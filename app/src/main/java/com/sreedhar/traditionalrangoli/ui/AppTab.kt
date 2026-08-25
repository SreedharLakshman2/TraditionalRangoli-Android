package com.sreedhar.traditionalrangoli.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppTab(
    val title: String,
    val icon: ImageVector
) {
    Home("Home", Icons.Filled.Home),
    Explore("Explore", Icons.Filled.Star),
    Create("Create", Icons.Filled.Add),
    Saved("Saved", Icons.Filled.Widgets),
    Profile("Profile", Icons.Filled.Person)
}
