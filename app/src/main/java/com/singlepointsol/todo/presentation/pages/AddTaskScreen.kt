package com.singlepointsol.todo.presentation.pages

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.singlepointsol.todo.presentation.AddTaskButton
import com.singlepointsol.todo.presentation.LoadingIndicator
import com.singlepointsol.todo.presentation.TaskInputField
import com.singlepointsol.todo.presentation.viewmodel.TodoViewModel
import com.singlepointsol.todo.presentation.viewmodel.UiState

@Composable
fun AddTaskScreen(
    onNavigateBack: () -> Unit,
    viewModel: TodoViewModel = hiltViewModel()
) {
    val taskName by viewModel.taskName.collectAsStateWithLifecycle()
    val isTaskValid by viewModel.isTaskValid.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current


    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Success -> {
                onNavigateBack()
            }
            is UiState.Error -> {
                Toast.makeText(context, (uiState as UiState.Error).message, Toast.LENGTH_SHORT)
                    .show()
                onNavigateBack()
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
            TaskInputField(
                taskName = taskName,
                isTaskValid = isTaskValid,
                onTaskChange = viewModel::onTaskNameChange
            )
            AddTaskButton({ viewModel.addTodo() }, isTaskValid)
        }

        if (uiState == UiState.Loading) {
            LoadingIndicator()
        }
    }
}







