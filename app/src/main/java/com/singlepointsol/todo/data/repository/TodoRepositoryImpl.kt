package com.singlepointsol.todo.data.repository


import com.singlepointsol.todo.data.local.TodoDao
import com.singlepointsol.todo.data.local.TodoEntity
import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(private val dao: TodoDao) : TodoRepository {
    override fun getTodos(): Flow<List<TodoItem>> {
        return dao.getAllTodos().map { entities -> entities.map { TodoItem(it.id, it.taskName,it.createdAt) } }
    }

    override suspend fun addTodo(todo: TodoItem) {
        dao.insertTodo(TodoEntity(taskName = todo.taskName, createdAt = todo.createdAt))
    }
}