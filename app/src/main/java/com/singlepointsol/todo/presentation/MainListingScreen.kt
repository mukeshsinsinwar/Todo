package com.singlepointsol.todo.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.singlepointsol.todo.utils.Screen


@Composable
fun MainListingScreen(navController: NavController) {
    val todoList = remember { mutableStateListOf<String>() } // List stored as state

    if (todoList.isEmpty()) {
        // Show placeholder text when the list is empty
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Press the + button to add a TODO item", modifier = Modifier.clickable {
                navController.navigate(Screen.AddTaskScreen.toString())
            })
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {

            items(todoList) { todo ->
                Text(
                    text = todo,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
