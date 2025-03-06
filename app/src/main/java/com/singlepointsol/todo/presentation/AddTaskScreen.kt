package com.singlepointsol.todo.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val taskName by viewModel.taskName.collectAsState()
    val isTaskValid by viewModel.isTaskValid.collectAsState()
    val context = LocalContext.current

    // Collect error messages and show a Toast
    LaunchedEffect(Unit) {
        viewModel.errorMessage.collect { message ->
            message?.let {
                delay(3000)
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            OutlinedTextField(
                value = taskName,
                onValueChange = { viewModel.onTaskNameChange(it) },
                label = { Text("Enter Task") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = !isTaskValid
            )

            if (!isTaskValid) {
                Text(
                    text = "Task cannot be empty",
                    color = Color.Red,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Button(
            onClick = { viewModel.addTodo { navController.popBackStack() } },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = isTaskValid
        ) {
            Text(text = "Add Task")
        }
    }
}

