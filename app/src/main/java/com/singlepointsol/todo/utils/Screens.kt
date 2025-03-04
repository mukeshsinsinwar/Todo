package com.singlepointsol.todo.utils


sealed class Screen {
    object MainListingScreen : Screen() {
        override fun toString(): String = "MainListingScreen"
    }
    object AddTaskScreen : Screen() {
        override fun toString(): String = "AddTaskScreen"
    }
}