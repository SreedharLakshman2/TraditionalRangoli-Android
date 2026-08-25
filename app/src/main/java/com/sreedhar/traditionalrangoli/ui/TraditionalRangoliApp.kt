package com.sreedhar.traditionalrangoli.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sreedhar.traditionalrangoli.ui.screens.CreateScreen
import com.sreedhar.traditionalrangoli.ui.screens.ExploreScreen
import com.sreedhar.traditionalrangoli.ui.screens.HomeScreen
import com.sreedhar.traditionalrangoli.ui.screens.LaunchSplashScreen
import com.sreedhar.traditionalrangoli.ui.screens.ProfileScreen
import com.sreedhar.traditionalrangoli.ui.screens.SavedScreen
import com.sreedhar.traditionalrangoli.ui.theme.Ivory
import kotlinx.coroutines.delay

@Composable
fun TraditionalRangoliApp() {
    var showSplash by remember { mutableStateOf(true) }
    var tab by remember { mutableStateOf(AppTab.Home) }

    LaunchedEffect(Unit) {
        delay(2_100)
        showSplash = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
    ) {
        Scaffold(
            containerColor = Ivory,
            bottomBar = {
                if (!showSplash) {
                    CourtyardTabBar(selection = tab, onSelect = { tab = it })
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                when (tab) {
                    AppTab.Home -> HomeScreen()
                    AppTab.Explore -> ExploreScreen()
                    AppTab.Create -> CreateScreen()
                    AppTab.Saved -> SavedScreen()
                    AppTab.Profile -> ProfileScreen()
                }
            }
        }
        AnimatedVisibility(
            visible = showSplash,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            LaunchSplashScreen()
        }
    }
}
