package com.singlepointsol.todo.domain.repository

import com.singlepointsol.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun addTodo(todo: TodoItem)
}