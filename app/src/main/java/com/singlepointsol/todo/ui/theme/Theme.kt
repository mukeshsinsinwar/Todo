package com.singlepointsol.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(

    primary = darkPrimary,
    onPrimary = Color.White,
    secondary = darkSecondary,
    background = Color.Black,
    onBackground = Color.White,

)

private val LightColorScheme = lightColorScheme(
    primary = lightSecondary,
    secondary = lightPrimary,
    tertiary = lightSecondary

)

@Composable
fun ToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


