package com.singlepointsol.todo.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.usecase.AddTodoUseCase
import com.singlepointsol.todo.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _taskName = MutableStateFlow("")
    val taskName: StateFlow<String> = _taskName.asStateFlow()

    private val _isTaskValid = MutableStateFlow(true)
    val isTaskValid: StateFlow<Boolean> = _isTaskValid.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> = _showErrorDialog.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val filteredTodos = searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) {
                _isSearching.value = false
                todos
            } else {
                _isSearching.value = true
                searchQuery.debounce(1000).onEach { }
                    .combine(todos) { debouncedQuery, todoList ->
                        todoList.filter { it.taskName.contains(debouncedQuery, ignoreCase = true) }
                    }
                    .onEach { _isSearching.value = false }
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())



    init {
        viewModelScope.launch {
            getTodosUseCase().collect { _todos.value = it }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun onTaskNameChange(newValue: String) {
        _taskName.value = newValue
        _isTaskValid.value = newValue.isNotBlank()
    }

    fun updateErrorDialog(isShown: Boolean){
        _showErrorDialog.value = isShown

    }

    fun clearUiStates(){
        _uiState.value = UiState.Idle
        _taskName.value = ""
    }

    fun addTodo() {
        val task = _taskName.value.trim()

        if (task.isEmpty()) {
            _isTaskValid.value = false
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                addTodoUseCase(TodoItem(taskName = task, createdAt = System.currentTimeMillis().toString()))
                delay(3000)
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                delay(3000)
                updateErrorDialog(true)
                _uiState.value = UiState.Error("Failed to add TODO")


            }
        }
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}


