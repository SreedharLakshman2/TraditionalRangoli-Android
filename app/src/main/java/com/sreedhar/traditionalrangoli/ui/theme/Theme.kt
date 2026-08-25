package com.sreedhar.traditionalrangoli.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CourtyardScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnAccent,
    secondary = Gold,
    onSecondary = Ink,
    background = Ivory,
    onBackground = Ink,
    surface = Paper,
    onSurface = Ink,
    surfaceVariant = Ivory,
    onSurfaceVariant = Muted
)

@Composable
fun TraditionalRangoliTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CourtyardScheme,
        typography = RangoliTypography,
        content = content
    )
}
