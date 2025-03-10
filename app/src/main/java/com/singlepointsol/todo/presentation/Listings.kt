package com.singlepointsol.todo.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.singlepointsol.todo.domain.model.TodoItem


@Composable
fun TodoList(todos: List<TodoItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(todos) { todo ->
            Text(text = todo.taskName, modifier = Modifier.padding(16.dp))
        }
    }
}