package com.sreedhar.traditionalrangoli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import com.sreedhar.traditionalrangoli.ui.LocalArtworks
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.TraditionalRangoliApp
import com.sreedhar.traditionalrangoli.ui.theme.TraditionalRangoliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true
        val app = application as RangoliApplication
        setContent {
            TraditionalRangoliTheme {
                val dark = isSystemInDarkTheme()
                SideEffect {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = !dark
                }
                CompositionLocalProvider(
                    LocalSettings provides app.settings,
                    LocalArtworks provides app.artworks
                ) {
                    TraditionalRangoliApp()
                }
            }
        }
    }
}
