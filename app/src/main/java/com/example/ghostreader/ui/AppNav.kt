package com.example.ghostreader.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.ghostreader.ui.screens.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding


data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun AppNav() {

    val navController = rememberNavController()

    val items = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("scan", "Scan", Icons.Default.CameraAlt),
        BottomNavItem("tools", "Tools", Icons.Default.Build),
        BottomNavItem("library", "Library", Icons.Default.Folder)
    )

    Scaffold(
        bottomBar = {

            NavigationBar {

                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { item ->

                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(item.icon, contentDescription = item.label)
                        },
                        label = {
                            Text(item.label)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("home") {
                DashboardScreen(
                    onScanClick = {
                        navController.navigate("scan")
                    }
                )
            }

            composable(route = "scan") {
                CameraScreen(
                    onGoToPages = {
                        navController.navigate("pages")
                    }
                )
            }

            composable("tools") {
                ToolsScreen()
            }

            composable("library") {
                LibraryScreen()
            }
            composable(route = "pages") {
                PagesScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
