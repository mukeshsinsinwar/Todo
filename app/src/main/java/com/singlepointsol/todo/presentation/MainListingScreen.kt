package com.singlepointsol.todo.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.singlepointsol.todo.R
import com.singlepointsol.todo.domain.model.TodoItem
import com.singlepointsol.todo.utils.Screen

@Composable
fun MainListingScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val todoList by viewModel.todos.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredTodos.collectAsStateWithLifecycle()


    Column(modifier = Modifier.fillMaxSize()) {
        if (todoList.isNotEmpty()) {
            SearchBar(searchQuery, viewModel::updateSearchQuery)
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                todoList.isEmpty() -> {

                    EmptyStateMessage( stringResource(R.string.press_the_button_to_add_a_todo_item),{
                        navController.navigate(Screen.AddTaskScreen.toString())

                    },true)
                }

                filteredList.isEmpty() -> {

                    EmptyStateMessage(stringResource(R.string.no_results_found),{

                    },false)
                }

                else -> {
                    TodoList(filteredList)
                }
            }
        }

        AddTaskButton {
            navController.navigate(Screen.AddTaskScreen.toString())
        }
    }
}
@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary ,  // Border when focused
            unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary,
            cursorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary,
            focusedLabelColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary

        ),
        label = { Text(stringResource(R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }
        }
    )
}


@Composable
fun EmptyStateMessage(message: String, onClick: () -> Unit, isClickable: Boolean) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message,
            color = Color.Gray,
            modifier = if (isClickable) Modifier.clickable { onClick() } else Modifier
        )
    }
}

@Composable
fun TodoList(todos: List<TodoItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(todos) { todo ->
            Text(text = todo.taskName, modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun AddTaskButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task", tint = Color.White)
        }
    }
}
