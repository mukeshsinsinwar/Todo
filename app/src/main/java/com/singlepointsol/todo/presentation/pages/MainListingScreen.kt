package com.singlepointsol.todo.presentation.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.singlepointsol.todo.R
import com.singlepointsol.todo.presentation.AddTaskFloatingActionButton
import com.singlepointsol.todo.presentation.EmptyStateMessage
import com.singlepointsol.todo.presentation.SearchBar
import com.singlepointsol.todo.presentation.ShowErrorDialog
import com.singlepointsol.todo.presentation.TodoList
import com.singlepointsol.todo.presentation.viewmodel.TodoViewModel
import com.singlepointsol.todo.presentation.viewmodel.UiState

@Composable
fun MainListingScreen(
    onNavigateToAddTask: () -> Unit,
    viewModel: TodoViewModel
) {
    val todoList by viewModel.todos.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()
    val filteredList by viewModel.filteredTodos.collectAsStateWithLifecycle()
    val showErrorDialog by viewModel.showErrorDialog.collectAsState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Log.d("mukesh",showErrorDialog.toString())

    if (showErrorDialog){
        ShowErrorDialog( (uiState as UiState.Error).message,{
            viewModel.updateErrorDialog(false)
            viewModel.clearUiStates()
        })
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (todoList.isNotEmpty()) {
            SearchBar(searchQuery, viewModel::updateSearchQuery,isSearching)
        }
        Box(modifier = Modifier.weight(1f)) {
            when {
                todoList.isEmpty() -> {

                    EmptyStateMessage( stringResource(R.string.press_the_button_to_add_a_todo_item),{
                        onNavigateToAddTask()
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

        AddTaskFloatingActionButton {
            onNavigateToAddTask()
        }
    }
}







