package com.example.pomodoroplayground

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun TimerScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Text(text = "Timer")
}