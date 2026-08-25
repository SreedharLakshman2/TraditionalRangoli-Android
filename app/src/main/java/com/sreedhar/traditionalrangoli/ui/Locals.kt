package com.sreedhar.traditionalrangoli.ui

import androidx.compose.runtime.compositionLocalOf
import com.sreedhar.traditionalrangoli.data.ArtworkStore
import com.sreedhar.traditionalrangoli.data.SettingsStore

val LocalSettings = compositionLocalOf<SettingsStore> {
    error("SettingsStore not provided")
}

val LocalArtworks = compositionLocalOf<ArtworkStore> {
    error("ArtworkStore not provided")
}
