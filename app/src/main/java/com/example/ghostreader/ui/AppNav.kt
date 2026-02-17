package com.example.ghostreader.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ghostreader.ui.screens.CameraScreen
import com.example.ghostreader.ui.screens.PagesScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "camera") {
        composable("camera") {
            CameraScreen(
                onGoToPages = { navController.navigate("pages") }
            )
        }

        composable("pages") {
            PagesScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
