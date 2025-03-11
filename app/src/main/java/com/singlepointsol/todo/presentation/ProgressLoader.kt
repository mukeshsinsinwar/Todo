package com.singlepointsol.todo.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.singlepointsol.todo.ui.theme.lightPrimary

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = if (isSystemInDarkTheme()) Color.White else lightPrimary)
    }
}

@Composable
fun SearchLoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        strokeWidth = 2.dp,
        color = if (isSystemInDarkTheme()) Color.White else lightPrimary
    )
}