package com.singlepointsol.todo.domain.usecase

import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<TodoItem>> = repository.getTodos()
}