package com.singlepointsol.todo.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_items")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
    val createdAt: String
)