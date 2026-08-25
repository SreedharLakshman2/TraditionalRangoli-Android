package com.sreedhar.traditionalrangoli.ui.screens

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.sreedhar.traditionalrangoli.data.DrawingSession
import com.sreedhar.traditionalrangoli.data.StudioKind
import com.sreedhar.traditionalrangoli.data.StudioRoute
import com.sreedhar.traditionalrangoli.data.UserArtwork
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.ArtworkPainter
import java.io.File

private enum class StudioStep { Draw, Color, Complete }

@Composable
fun StudioHost(route: StudioRoute, onClose: () -> Unit, onCreateAnother: () -> Unit) {
    val settings = LocalSettings.current
    val context = LocalContext.current
    val session = remember(route) { sessionFor(route, settings.showGuides, settings.defaultGrid) }
    var step by remember { mutableStateOf(StudioStep.Draw) }
    var completed by remember { mutableStateOf<UserArtwork?>(null) }

    when (step) {
        StudioStep.Draw -> DrawingStudioScreen(
            session = session,
            onBack = onClose,
            onDone = { step = StudioStep.Color }
        )
        StudioStep.Color -> ColoringScreen(session) {
            val bitmap = ArtworkPainter.snapshot(session)
            val art = session.artwork(session.title, null)
            completed = art.copy(thumbnailPath = writeCachePng(context.cacheDir, art.id, bitmap))
            step = StudioStep.Complete
        }
        StudioStep.Complete -> completed?.let { art ->
            CompletionScreen(
                artwork = art,
                pattern = session.pattern,
                onClose = onClose,
                onCreateAnother = onCreateAnother
            )
        }
    }
}

private fun writeCachePng(cacheDir: File, id: String, bitmap: Bitmap): String {
    val file = File(cacheDir, "rangoli-$id.png")
    file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }
    return file.absolutePath
}

private fun sessionFor(route: StudioRoute, showGuides: Boolean, defaultGrid: Int): DrawingSession {
    return when (route) {
        is StudioRoute.Dots -> DrawingSession(
            studio = StudioKind.Dots,
            pattern = route.pattern,
            gridSize = route.pattern?.gridSize ?: defaultGrid,
            showGuides = showGuides
        )
        StudioRoute.Freehand -> DrawingSession(studio = StudioKind.Freehand, showGuides = false)
        is StudioRoute.Template -> DrawingSession(
            studio = StudioKind.Template,
            pattern = route.pattern,
            gridSize = route.pattern.gridSize,
            showGuides = showGuides
        )
        is StudioRoute.Continue -> DrawingSession(
            studio = route.artwork.studio,
            artwork = route.artwork,
            showGuides = showGuides
        )
    }
}
