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
class TodoViewModel @Inject constructor(  // ✅ Add @Inject here!
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    init {
        viewModelScope.launch {
            getTodosUseCase().collect { _todos.value = it }
        }
    }

    fun addTodo(taskName: String, createdAt: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                addTodoUseCase(TodoItem(taskName = taskName, createdAt = createdAt))
            } catch (e: Exception) {
                onError("Failed to add TODO")
            }
        }
    }
}
