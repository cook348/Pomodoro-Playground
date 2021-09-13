package com.example.pomodoroplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pomodoroplayground.screens.PomodoroScreen
import com.example.pomodoroplayground.screens.ProfileScreen
import com.example.pomodoroplayground.screens.StatsScreen
import com.example.pomodoroplayground.ui.theme.PomodoroPlaygroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PomodoroPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PomodoroApp()
                }
            }
        }
    }
}

@Composable
fun PomodoroApp() {

    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    var currentScreen = PomodoroScreen.fromRoute(backStackEntry.value?.destination?.route)

    val items = listOf(PomodoroScreen.Timer, PomodoroScreen.Stats, PomodoroScreen.Profile)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        bottomBar = {
            PomodoroBottomNavBar(
                navController = navController,
                items = items
            )
        }
    ) { innerPadding ->
        PomodoroNavHost(navController = navController, Modifier.padding(innerPadding))
    }
}

@Composable
fun PomodoroNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = PomodoroScreen.Timer.route,
        modifier = modifier
    ) {
        composable(PomodoroScreen.Timer.route) { TimerScreen(navController) }
        composable(PomodoroScreen.Stats.route) { StatsScreen(navController) }
        composable(PomodoroScreen.Profile.route) { ProfileScreen(navController) }
    }
}

@Composable
fun PomodoroBottomNavBar(
    navController: NavHostController,
    items: List<PomodoroScreen>,
    modifier: Modifier = Modifier
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(screen.resourceId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PomodoroPlaygroundTheme {
        PomodoroApp()
    }
}