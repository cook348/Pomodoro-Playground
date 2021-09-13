package com.example.pomodoroplayground.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Text(text = "Profile")
}