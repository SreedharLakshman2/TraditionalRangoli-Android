package com.sreedhar.traditionalrangoli.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Shader
import com.sreedhar.traditionalrangoli.data.DrawStroke
import com.sreedhar.traditionalrangoli.data.DrawTool
import com.sreedhar.traditionalrangoli.data.DrawingSession
import com.sreedhar.traditionalrangoli.data.DrawingUtilities
import com.sreedhar.traditionalrangoli.data.FillBlob
import com.sreedhar.traditionalrangoli.data.Point2D
import com.sreedhar.traditionalrangoli.data.PowderSwatch
import com.sreedhar.traditionalrangoli.data.SymmetryEngine
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

object ArtworkPainter {
    fun snapshot(session: DrawingSession, size: Int = 720): Bitmap {
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        paint(
            canvas = canvas,
            width = size.toFloat(),
            height = size.toFloat(),
            strokes = session.strokes,
            fills = session.fills,
            gridSize = session.gridSize,
            showGrid = false,
            livePoints = emptyList(),
            liveColor = session.color,
            liveWidth = session.brush.width,
            liveSymmetry = session.symmetry,
            liveTool = session.tool,
            showFloor = true
        )
        return bitmap
    }

    fun paint(canvas: Canvas, width: Float, height: Float, session: DrawingSession, showFloor: Boolean = true) {
        paint(
            canvas, width, height,
            session.strokes, session.fills, session.gridSize, session.showGrid,
            session.livePoints, session.color, session.brush.width, session.symmetry, session.tool,
            showFloor
        )
    }

    fun paint(
        canvas: Canvas,
        width: Float,
        height: Float,
        strokes: List<DrawStroke>,
        fills: List<FillBlob>,
        gridSize: Int,
        showGrid: Boolean,
        livePoints: List<Point2D>,
        liveColor: PowderSwatch,
        liveWidth: Float,
        liveSymmetry: com.sreedhar.traditionalrangoli.data.SymmetryMode,
        liveTool: DrawTool,
        showFloor: Boolean
    ) {
        if (showFloor) drawFloor(canvas, width, height)
        if (showGrid) drawGrid(canvas, width, height, gridSize)
        fills.forEach { drawFill(canvas, it, width, height) }
        val center = Point2D(width / 2f, height / 2f)
        strokes.forEach { drawStroke(canvas, it, width, height, center) }
        if (livePoints.size > 1) {
            val mapped = livePoints.map { DrawingUtilities.mapped(it, width, height) }
            val copies = SymmetryEngine.copies(mapped, liveSymmetry, center)
            val widthPx = scaledWidth(liveWidth, width, height)
            copies.forEach { points ->
                strokePath(canvas, points, liveColor.colorInt, widthPx, liveTool == DrawTool.Eraser)
            }
        }
    }

    private fun drawFloor(canvas: Canvas, width: Float, height: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = RadialGradient(
            width / 2f, height / 2f, min(width, height) * 0.78f,
            intArrayOf(0xFFB07A4C.toInt(), 0xFF8A5333.toInt(), 0xFF6A3A22.toInt()),
            floatArrayOf(0f, 0.55f, 1f),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, width, height, paint)
    }

    private fun drawGrid(canvas: Canvas, width: Float, height: Float, gridSize: Int) {
        val dots = DrawingUtilities.gridPoints(gridSize, width, height)
        val r = maxOf(2.2f, min(width, height) * 0.007f)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x8CF7F1E3.toInt()
            style = Paint.Style.FILL
        }
        dots.forEach { canvas.drawCircle(it.x, it.y, r, paint) }
    }

    private fun drawStroke(canvas: Canvas, stroke: DrawStroke, width: Float, height: Float, center: Point2D) {
        val points = stroke.points.map { DrawingUtilities.mapped(it, width, height) }
        val copies = SymmetryEngine.copies(points, stroke.symmetry, center)
        val widthPx = scaledWidth(stroke.width, width, height)
        val color = (0xFF000000L or stroke.colorHex).toInt()
        copies.forEach { copy ->
            strokePath(canvas, copy, color, widthPx, stroke.tool == DrawTool.Eraser)
        }
    }

    private fun strokePath(canvas: Canvas, points: List<Point2D>, color: Int, width: Float, eraser: Boolean) {
        if (points.isEmpty()) return
        val path = smoothPath(points)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
            style = Paint.Style.STROKE
            strokeWidth = width
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
            if (eraser) xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        canvas.drawPath(path, paint)
    }

    private fun smoothPath(points: List<Point2D>): Path {
        val path = Path()
        val first = points.first()
        path.moveTo(first.x, first.y)
        if (points.size == 1) return path
        if (points.size == 2) {
            path.lineTo(points[1].x, points[1].y)
            return path
        }
        for (index in 1 until points.size) {
            val previous = points[index - 1]
            val current = points[index]
            path.quadTo(previous.x, previous.y, (previous.x + current.x) / 2f, (previous.y + current.y) / 2f)
        }
        path.lineTo(points.last().x, points.last().y)
        return path
    }

    private fun scaledWidth(width: Float, canvasW: Float, canvasH: Float) =
        width * min(canvasW, canvasH) / 320f

    private fun drawFill(canvas: Canvas, fill: FillBlob, width: Float, height: Float) {
        val center = DrawingUtilities.mapped(fill.center, width, height)
        val radius = fill.radius * min(width, height)
        val color = (0xFF000000L or fill.colorHex).toInt()
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color }
        when (fill.kind) {
            DrawTool.Flower -> {
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 2f
                paint.strokeCap = Paint.Cap.ROUND
                for (i in 0 until 6) {
                    val a = i / 6f * PI.toFloat() * 2f
                    val tipX = center.x + cos(a) * radius
                    val tipY = center.y + sin(a) * radius
                    val path = Path()
                    path.moveTo(center.x, center.y)
                    path.quadTo(
                        center.x + cos(a + 0.4f) * radius * 0.6f,
                        center.y + sin(a + 0.4f) * radius * 0.6f,
                        tipX,
                        tipY
                    )
                    canvas.drawPath(path, paint)
                }
            }
            DrawTool.Diya -> {
                paint.color = 0xE6E3B23C.toInt()
                val flame = Path()
                flame.moveTo(center.x, center.y - radius)
                flame.quadTo(center.x - radius * 0.7f, center.y - radius * 0.2f, center.x - radius * 0.5f, center.y + radius * 0.2f)
                flame.quadTo(center.x + radius * 0.7f, center.y - radius * 0.2f, center.x, center.y - radius)
                canvas.drawPath(flame, paint)
                paint.color = color
                canvas.drawOval(center.x - radius * 0.7f, center.y, center.x + radius * 0.7f, center.y + radius * 0.7f, paint)
            }
            DrawTool.Dots, DrawTool.Rice -> {
                paint.alpha = 235
                canvas.drawCircle(center.x, center.y, radius, paint)
            }
            else -> {
                paint.alpha = 140
                canvas.drawCircle(center.x, center.y, radius, paint)
            }
        }
    }

    fun dashedGuidePaint(): Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = 0x47FFFFFF
        style = Paint.Style.STROKE
        strokeWidth = 1.6f
        pathEffect = DashPathEffect(floatArrayOf(5f, 6f), 0f)
    }
}
