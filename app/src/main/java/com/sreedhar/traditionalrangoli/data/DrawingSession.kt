package com.sreedhar.traditionalrangoli.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.hypot

class DrawingSession(
    studio: StudioKind,
    pattern: RangoliPattern? = null,
    gridSize: Int? = null,
    showGuides: Boolean = true,
    artwork: UserArtwork? = null
) {
    var studio by mutableStateOf(artwork?.studio ?: studio)
    var pattern by mutableStateOf(pattern ?: artwork?.patternId?.let(PatternCatalog::pattern))
    var gridSize by mutableIntStateOf(artwork?.gridSize ?: gridSize ?: pattern?.gridSize ?: 9)
    var showGrid by mutableStateOf((artwork?.studio ?: studio) != StudioKind.Freehand)
    var snapToDots by mutableStateOf((artwork?.studio ?: studio) != StudioKind.Freehand)
    var symmetry by mutableStateOf(if ((artwork?.studio ?: studio) == StudioKind.Freehand) SymmetryMode.FourWay else SymmetryMode.None)
    var color by mutableStateOf(if ((artwork?.studio ?: studio) == StudioKind.Freehand) PowderSwatch.TraditionalRed else PowderSwatch.Rice)
    var brush by mutableStateOf(BrushSize.Medium)
    var tool by mutableStateOf(DrawTool.Brush)
    var showGuides by mutableStateOf(showGuides)
    var strokes by mutableStateOf(artwork?.strokes ?: emptyList())
    var fills by mutableStateOf(artwork?.fills ?: emptyList())
    var undoneStrokes by mutableStateOf(emptyList<DrawStroke>())
    var undoneFills by mutableStateOf(emptyList<FillBlob>())
    var livePoints by mutableStateOf(emptyList<Point2D>())
    var canvasWidth by mutableFloatStateOf(320f)
    var canvasHeight by mutableFloatStateOf(320f)

    val canUndo: Boolean get() = strokes.isNotEmpty() || fills.isNotEmpty()
    val canRedo: Boolean get() = undoneStrokes.isNotEmpty() || undoneFills.isNotEmpty()
    val title: String
        get() = pattern?.title ?: if (studio == StudioKind.Freehand) "Freehand Rangoli" else "Dot Rangoli"

    fun begin(x: Float, y: Float, width: Float, height: Float) {
        canvasWidth = width
        canvasHeight = height
        if (tool != DrawTool.Brush && tool != DrawTool.Eraser) {
            addFill(x, y, width, height)
            return
        }
        val snapped = prepared(Point2D(x, y), width, height)
        livePoints = listOf(DrawingUtilities.normalized(snapped, width, height))
    }

    fun move(x: Float, y: Float, width: Float, height: Float) {
        if (tool != DrawTool.Brush && tool != DrawTool.Eraser) return
        canvasWidth = width
        canvasHeight = height
        val snapped = prepared(Point2D(x, y), width, height)
        val lastNorm = livePoints.lastOrNull()
        if (lastNorm == null) {
            livePoints = listOf(DrawingUtilities.normalized(snapped, width, height))
            return
        }
        val last = DrawingUtilities.mapped(lastNorm, width, height)
        val cell = minOf(width, height) * 0.8f / maxOf(gridSize - 1, 1)
        if (hypot(snapped.x - last.x, snapped.y - last.y) > cell * 2.6f) return
        val extras = DrawingUtilities.interpolate(last, snapped, 2.2f)
        livePoints = livePoints + extras.map { DrawingUtilities.normalized(it, width, height) }
    }

    fun end() {
        if ((tool != DrawTool.Brush && tool != DrawTool.Eraser) || livePoints.size <= 1) {
            livePoints = emptyList()
            return
        }
        strokes = strokes + DrawStroke(
            points = livePoints,
            colorHex = if (tool == DrawTool.Eraser) 0L else color.hex,
            width = if (tool == DrawTool.Eraser) brush.width * 1.8f else brush.width,
            tool = tool,
            symmetry = symmetry
        )
        undoneStrokes = emptyList()
        livePoints = emptyList()
    }

    fun undo() {
        val lastFill = fills.lastOrNull()
        if (lastFill != null) {
            fills = fills.dropLast(1)
            undoneFills = undoneFills + lastFill
            return
        }
        val last = strokes.lastOrNull() ?: return
        strokes = strokes.dropLast(1)
        undoneStrokes = undoneStrokes + last
    }

    fun redo() {
        val fill = undoneFills.lastOrNull()
        if (fill != null) {
            undoneFills = undoneFills.dropLast(1)
            fills = fills + fill
            return
        }
        val stroke = undoneStrokes.lastOrNull() ?: return
        undoneStrokes = undoneStrokes.dropLast(1)
        strokes = strokes + stroke
    }

    fun clear() {
        strokes = emptyList()
        fills = emptyList()
        undoneStrokes = emptyList()
        undoneFills = emptyList()
        livePoints = emptyList()
    }

    fun artwork(title: String, thumbnailPath: String?): UserArtwork {
        return UserArtwork(
            title = title,
            patternId = pattern?.id,
            studio = studio,
            gridSize = gridSize,
            strokes = strokes,
            fills = fills,
            thumbnailPath = thumbnailPath,
            isFavorite = false,
            colors = (strokes.map { it.colorHex } + fills.map { it.colorHex }).distinct()
        )
    }

    private fun addFill(x: Float, y: Float, width: Float, height: Float) {
        val radius = when (tool) {
            DrawTool.Fill -> 0.08f
            DrawTool.Rice -> 0.035f
            DrawTool.Flower -> 0.05f
            DrawTool.Diya -> 0.045f
            DrawTool.Dots -> 0.018f
            else -> 0.06f
        }
        fills = fills + FillBlob(
            center = DrawingUtilities.normalized(Point2D(x, y), width, height),
            colorHex = color.hex,
            radius = radius,
            kind = tool
        )
        undoneFills = emptyList()
    }

    private fun prepared(point: Point2D, width: Float, height: Float): Point2D {
        return if (snapToDots && showGrid) DrawingUtilities.snap(point, gridSize, width, height) else point
    }
}
