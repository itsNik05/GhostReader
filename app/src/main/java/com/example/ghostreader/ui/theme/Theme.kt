package com.example.ghostreader.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    background = DeepBackground,
    surface = CardBackground,
    primary = AccentBlue,
    secondary = AccentPurple,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun GhostReaderTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        content = content
    )
}
