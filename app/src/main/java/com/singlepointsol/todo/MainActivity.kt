package com.singlepointsol.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.singlepointsol.todo.ui.theme.ToDoTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.singlepointsol.todo.common.CustomTopBar
import com.singlepointsol.todo.screens.AddTaskScreen
import com.singlepointsol.todo.screens.MainListingScreen
import com.singlepointsol.todo.utils.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
              ToDoApp()
            }
        }
    }
}

@Composable
fun ToDoApp() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { CustomTopBar(navController) } // Top bar applied to all screens
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.MainListingScreen.toString(),
            modifier = Modifier.padding(paddingValues) // Apply padding from Scaffold
        ) {
            composable(Screen.MainListingScreen.toString()) {
                MainListingScreen(navController)
            }
            composable(Screen.AddTaskScreen.toString()) {
                AddTaskScreen(navController)
            }
        }
    }
}




