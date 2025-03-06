package com.singlepointsol.todo.domain.model

data class TodoItem(
    val id: Int = 0,
    val taskName: String,
    val createdAt : String
)