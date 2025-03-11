package com.singlepointsol.todo.presentation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ShowErrorDialog( errorMessage :String ,onDismiss: () -> Unit){
    AlertDialog(
        onDismissRequest = {onDismiss()},
        title = { Text("Error") },
        text = { Text(errorMessage) },
        confirmButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text("OK")
            }
        }
    )
}