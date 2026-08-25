package com.sreedhar.traditionalrangoli.data

import androidx.compose.ui.graphics.Color
import java.util.UUID

enum class StudioKind { Dots, Freehand, Template }

enum class SymmetryMode(val title: String) {
    None("None"),
    Horizontal("Horizontal"),
    Vertical("Vertical"),
    FourWay("4-way"),
    EightWay("8-way")
}

enum class DrawTool { Brush, Eraser, Fill, Rice, Flower, Diya, Dots }

enum class BrushSize(val title: String, val width: Float) {
    Small("Small", 2.4f),
    Medium("Medium", 4.2f),
    Large("Large", 7.2f)
}

enum class PowderSwatch(val title: String, val hex: Long) {
    Rice("Rice", 0xF4E6C3),
    TraditionalRed("Red", 0x9B2C2C),
    Terracotta("Terracotta", 0xC45C2A),
    Yellow("Yellow", 0xE3B23C),
    Green("Green", 0x3F6B4F),
    White("White", 0xFFF8E7),
    Orange("Orange", 0xD97706),
    Pink("Pink", 0xC45D7A),
    Purple("Purple", 0x6B3F6B),
    Gold("Gold", 0xC89B3C);

    val color: Color get() = Color(0xFF000000L or hex)
    val colorInt: Int get() = (0xFF000000L or hex).toInt()
}

data class Point2D(val x: Float, val y: Float)

data class DrawStroke(
    val id: String = UUID.randomUUID().toString(),
    val points: List<Point2D>,
    val colorHex: Long,
    val width: Float,
    val tool: DrawTool,
    val symmetry: SymmetryMode
)

data class FillBlob(
    val id: String = UUID.randomUUID().toString(),
    val center: Point2D,
    val colorHex: Long,
    val radius: Float,
    val kind: DrawTool
)

data class UserArtwork(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val patternId: String?,
    val studio: StudioKind,
    val gridSize: Int,
    val strokes: List<DrawStroke>,
    val fills: List<FillBlob>,
    val thumbnailPath: String?,
    val isFavorite: Boolean,
    val colors: List<Long>
)

sealed class StudioRoute {
    data class Dots(val pattern: RangoliPattern?) : StudioRoute()
    data object Freehand : StudioRoute()
    data class Template(val pattern: RangoliPattern) : StudioRoute()
    data class Continue(val artwork: UserArtwork) : StudioRoute()
}
