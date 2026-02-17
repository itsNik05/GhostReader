package com.example.ghostreader

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ghostreader.ui.AppNav
import com.example.ghostreader.ui.theme.GhostReaderTheme

class MainActivity : ComponentActivity() {

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)

        setContent {
            GhostReaderTheme {
                AppNav()
            }
        }
    }
}
