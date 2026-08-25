package com.sreedhar.traditionalrangoli.data

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

object DrawingUtilities {
    fun gridPoints(size: Int, width: Float, height: Float, insetRatio: Float = 0.1f): List<Point2D> {
        if (size <= 1) return emptyList()
        val inset = minOf(width, height) * insetRatio
        val usable = minOf(width, height) - inset * 2f
        val originX = (width - usable) / 2f
        val originY = (height - usable) / 2f
        val cell = usable / (size - 1)
        val points = ArrayList<Point2D>(size * size)
        for (row in 0 until size) {
            for (col in 0 until size) {
                points += Point2D(originX + col * cell, originY + row * cell)
            }
        }
        return points
    }

    fun snap(point: Point2D, gridSize: Int, width: Float, height: Float, threshold: Float = 0.38f): Point2D {
        val dots = gridPoints(gridSize, width, height)
        val nearest = dots.minByOrNull { hypot(it.x - point.x, it.y - point.y) } ?: return point
        val cell = minOf(width, height) * 0.8f / maxOf(gridSize - 1, 1)
        return if (hypot(nearest.x - point.x, nearest.y - point.y) <= cell * threshold) nearest else point
    }

    fun interpolate(from: Point2D, to: Point2D, spacing: Float): List<Point2D> {
        val distance = hypot(to.x - from.x, to.y - from.y)
        if (distance <= spacing) return listOf(to)
        val steps = (distance / spacing).toInt().coerceAtLeast(1)
        return (1..steps).map { i ->
            val t = i / steps.toFloat()
            Point2D(from.x + (to.x - from.x) * t, from.y + (to.y - from.y) * t)
        }
    }

    fun mapped(point: Point2D, width: Float, height: Float) =
        Point2D(point.x * width, point.y * height)

    fun normalized(point: Point2D, width: Float, height: Float) =
        Point2D(
            if (width == 0f) 0f else point.x / width,
            if (height == 0f) 0f else point.y / height
        )
}

object SymmetryEngine {
    fun copies(points: List<Point2D>, mode: SymmetryMode, center: Point2D): List<List<Point2D>> {
        if (points.isEmpty()) return emptyList()
        return when (mode) {
            SymmetryMode.None -> listOf(points)
            SymmetryMode.Horizontal -> listOf(points, mirror(points, horizontal = true, center))
            SymmetryMode.Vertical -> listOf(points, mirror(points, horizontal = false, center))
            SymmetryMode.FourWay -> {
                val h = mirror(points, horizontal = true, center)
                val v = mirror(points, horizontal = false, center)
                val hv = mirror(h, horizontal = false, center)
                listOf(points, h, v, hv)
            }
            SymmetryMode.EightWay -> {
                val angles = listOf(0.0, PI / 4, PI / 2, 3 * PI / 4, PI, 5 * PI / 4, 3 * PI / 2, 7 * PI / 4)
                angles.map { rotate(points, it.toFloat(), center) }
            }
        }
    }

    private fun mirror(points: List<Point2D>, horizontal: Boolean, center: Point2D) =
        points.map { point ->
            if (horizontal) Point2D(point.x, center.y * 2f - point.y)
            else Point2D(center.x * 2f - point.x, point.y)
        }

    private fun rotate(points: List<Point2D>, angle: Float, center: Point2D): List<Point2D> {
        val cosine = cos(angle)
        val sine = sin(angle)
        return points.map { point ->
            val dx = point.x - center.x
            val dy = point.y - center.y
            Point2D(center.x + dx * cosine - dy * sine, center.y + dx * sine + dy * cosine)
        }
    }
}
