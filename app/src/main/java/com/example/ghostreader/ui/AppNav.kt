package com.example.ghostreader.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.ghostreader.ui.screens.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*




@Composable
fun AppNav() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("scan", "Scan", Icons.Default.Camera),
        BottomNavItem("tools", "Tools", Icons.Default.Build),
        BottomNavItem("library", "Library", Icons.Default.Folder)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentDestination =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.route,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {

            composable("home") { DashboardScreen() }
            composable("scan") { CameraScreen(onGoToPages = {}) }
            composable("tools") { ToolsScreen() }
            composable("library") { LibraryScreen() }

        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
