package com.singlepointsol.todo.domain.usecase

import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.repository.TodoRepository

class AddTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: TodoItem) {
        if (todo.taskName == "Error") throw Exception("Failed to add TODO")
        repository.addTodo(todo)
    }
}