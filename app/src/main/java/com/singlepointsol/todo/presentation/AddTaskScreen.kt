package com.singlepointsol.todo.presentation

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.singlepointsol.todo.R
import com.singlepointsol.todo.ui.theme.lightPrimary

@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val taskName by viewModel.taskName.collectAsState()
    val isTaskValid by viewModel.isTaskValid.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current


    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                navController.popBackStack()
            }
            is UiState.Error -> {
                Toast.makeText(context, (uiState as UiState.Error).message, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }


            else -> {}
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                OutlinedTextField(
                    value = taskName,
                    onValueChange = { viewModel.onTaskNameChange(it) },
                    label = { Text(stringResource(R.string.enter_task)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = !isTaskValid,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary ,  // Border when focused
                    unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary,
                    cursorColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary,
                    focusedLabelColor = if (isSystemInDarkTheme()) Color.White else  MaterialTheme.colorScheme.primary

                )
                )

                if (!isTaskValid) {
                    Text(
                        text = stringResource(R.string.task_cannot_be_empty),
                        color = Color.Red,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Button(
                onClick = { viewModel.addTodo() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isTaskValid
            ) {
                Text(text = "Add Task")
            }
        }

        // Loader overlay - this ensures it does NOT move other UI elements
        if (uiState == UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = if (isSystemInDarkTheme()) Color.White else lightPrimary)
            }
        }
    }


}

