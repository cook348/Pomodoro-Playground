package com.example.pomodoroplayground.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Timer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.pomodoroplayground.R

const val timerName = "timer"
const val statsName = "stats"
const val profileName = "profile"

sealed class PomodoroScreen(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {

    object Timer : PomodoroScreen(timerName, R.string.timer, Icons.Filled.Timer)
    object Stats : PomodoroScreen(statsName, R.string.stats, Icons.Filled.QueryStats)
    object Profile : PomodoroScreen(profileName, R.string.profile, Icons.Filled.Person)

    companion object {
        fun fromRoute(route: String?): PomodoroScreen =
            when (route?.substringBefore("/")) {
                timerName -> Timer
                statsName -> Stats
                profileName -> Profile
                null -> Timer
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}