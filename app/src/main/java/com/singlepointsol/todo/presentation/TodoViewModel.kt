package com.singlepointsol.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.usecase.AddTodoUseCase
import com.singlepointsol.todo.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel


import kotlinx.coroutines.flow.*
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

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage: SharedFlow<String?> = _errorMessage.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val filteredTodos = searchQuery
        .combine(todos) { query, todoList ->
            if (query.isBlank()) todoList
            else todoList.filter { it.taskName.contains(query, ignoreCase = true) }
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

    fun addTodo(onError: (String) -> Unit) {
        val task = _taskName.value.trim()

        if (task.isEmpty()) {
            _isTaskValid.value = false
            return
        }

        viewModelScope.launch {
            try {
                addTodoUseCase(TodoItem(taskName = task, createdAt = System.currentTimeMillis().toString()))
                _taskName.value = "" // Clear input field after adding
            } catch (e: Exception) {
                _errorMessage.emit("Failed to add TODO")
                onError("Failed to add TODO")
            }
        }
    }
}

