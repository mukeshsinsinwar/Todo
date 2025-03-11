package com.singlepointsol.todo.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.singlepointsol.todo.R


@Composable
fun AddTaskInputField(taskName: String, isTaskValid: Boolean, onTaskChange: (String) -> Unit) {
    Column {
        OutlinedTextField(
            value = taskName,
            onValueChange = onTaskChange,
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
}


@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, isSearching: Boolean) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
            cursorColor = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary,
            focusedLabelColor = if (isSystemInDarkTheme()) Color.White else MaterialTheme.colorScheme.primary
        ),
        label = { Text(stringResource(R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        trailingIcon = {
            if (isSearching) {
                SearchLoadingIndicator()
            } else if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
                }
            }
        }
    )
}
