package com.sreedhar.traditionalrangoli.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.sreedhar.traditionalrangoli.data.MotifKind
import com.sreedhar.traditionalrangoli.ui.theme.FloorDeep
import com.sreedhar.traditionalrangoli.ui.theme.FloorLight
import com.sreedhar.traditionalrangoli.ui.theme.FloorMid
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MotifPreview(motif: MotifKind, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.aspectRatio(1f).fillMaxSize()) {
        val c = Offset(size.width / 2f, size.height / 2f)
        val r = size.minDimension / 2f
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(FloorLight, FloorMid, FloorDeep),
                center = c,
                radius = r * 1.15f
            )
        )
        drawMotif(motif, c, r * 0.78f)
    }
}

private fun DrawScope.drawMotif(kind: MotifKind, c: Offset, r: Float) {
    val rice = Color(0xFFF7F1E3)
    val maroon = Color(0xFF9B2C2C)
    val gold = Color(0xFFC89B3C)
    val terracotta = Color(0xFFC45C2A)
    val green = Color(0xFF3F6B4F)
    val yellow = Color(0xFFE3B23C)
    when (kind) {
        MotifKind.LotusDot, MotifKind.EightPetal -> petals(c, r, if (kind == MotifKind.EightPetal) 8 else 8, maroon, gold)
        MotifKind.SimpleFlower -> petals(c, r, 4, terracotta, gold)
        MotifKind.FestivalFlower -> {
            petals(c, r * 0.95f, 12, maroon, yellow)
            petals(c, r * 0.62f, 8, terracotta, gold)
        }
        MotifKind.Peacock -> peacock(c, r, green, gold, maroon)
        MotifKind.Diya -> diya(c, r, maroon, gold, yellow)
        MotifKind.Pulli -> pulli(c, r, rice, gold)
        MotifKind.Spiral -> spiral(c, r, rice)
        MotifKind.GeometricStar -> star(c, r, gold, maroon)
        MotifKind.Mandala -> {
            petals(c, r, 16, maroon, gold)
            ring(c, r * 0.42f, gold, 4f)
            ring(c, r * 0.22f, rice, 3f)
        }
        MotifKind.Butterfly -> butterfly(c, r, maroon, gold)
        MotifKind.PongalPot -> pot(c, r, terracotta, green, gold)
        MotifKind.SikkuKnot -> sikku(c, r, rice)
        MotifKind.OnamPookalam -> {
            petals(c, r, 16, yellow, maroon)
            petals(c, r * 0.7f, 12, terracotta, gold)
            petals(c, r * 0.42f, 8, green, rice)
        }
        MotifKind.SunBurst -> sun(c, r, gold, yellow)
        MotifKind.MangoLeaf -> mango(c, r, green, gold)
    }
    drawCircle(gold, r * 0.055f, c)
}

private fun polar(c: Offset, r: Float, a: Float) =
    Offset(c.x + r * cos(a), c.y + r * sin(a))

private fun DrawScope.ring(c: Offset, r: Float, color: Color, width: Float) {
    drawCircle(color, r, c, style = Stroke(width = width))
}

private fun DrawScope.petals(c: Offset, r: Float, count: Int, fill: Color, rim: Color) {
    val step = (2 * PI / count).toFloat()
    for (i in 0 until count) {
        val a = -PI.toFloat() / 2f + i * step
        val tip = polar(c, r, a)
        val left = polar(c, r * 0.42f, a - step * 0.32f)
        val right = polar(c, r * 0.42f, a + step * 0.32f)
        val path = Path().apply {
            moveTo(c.x, c.y)
            cubicTo(left.x, left.y, tip.x, tip.y, tip.x, tip.y)
            cubicTo(tip.x, tip.y, right.x, right.y, c.x, c.y)
            close()
        }
        drawPath(path, fill.copy(alpha = 0.92f))
        drawPath(path, rim, style = Stroke(width = 2.4f, cap = StrokeCap.Round, join = StrokeJoin.Round))
    }
    ring(c, r * 0.18f, rim, 2.2f)
}

private fun DrawScope.peacock(c: Offset, r: Float, green: Color, gold: Color, maroon: Color) {
    for (i in -5..5) {
        val a = -PI.toFloat() / 2f + i * 0.18f
        val tip = polar(c, r, a)
        drawLine(green, polar(c, r * 0.12f, a), tip, strokeWidth = 5f, cap = StrokeCap.Round)
        drawCircle(gold, r * 0.055f, tip)
        drawCircle(maroon, r * 0.028f, tip)
    }
    drawCircle(maroon, r * 0.16f, Offset(c.x, c.y + r * 0.18f))
    drawCircle(gold, r * 0.06f, Offset(c.x, c.y + r * 0.12f))
}

private fun DrawScope.diya(c: Offset, r: Float, maroon: Color, gold: Color, yellow: Color) {
    val bowl = Path().apply {
        moveTo(c.x - r * 0.55f, c.y + r * 0.08f)
        quadraticTo(c.x, c.y + r * 0.62f, c.x + r * 0.55f, c.y + r * 0.08f)
        quadraticTo(c.x, c.y - r * 0.05f, c.x - r * 0.55f, c.y + r * 0.08f)
        close()
    }
    drawPath(bowl, maroon)
    drawPath(bowl, gold, style = Stroke(3f))
    val flame = Path().apply {
        moveTo(c.x, c.y - r * 0.55f)
        quadraticTo(c.x + r * 0.18f, c.y - r * 0.18f, c.x, c.y + r * 0.02f)
        quadraticTo(c.x - r * 0.18f, c.y - r * 0.18f, c.x, c.y - r * 0.55f)
        close()
    }
    drawPath(flame, yellow)
    for (i in 0 until 8) {
        val a = i * (2 * PI / 8).toFloat()
        drawCircle(gold, r * 0.04f, polar(c, r * 0.78f, a))
    }
}

private fun DrawScope.pulli(c: Offset, r: Float, rice: Color, gold: Color) {
    val n = 5
    for (row in 0 until n) {
        for (col in 0 until n) {
            val x = c.x + (col - 2) * r * 0.32f
            val y = c.y + (row - 2) * r * 0.32f
            drawCircle(rice, r * 0.045f, Offset(x, y))
        }
    }
    val loop = Path()
    var started = false
    for (i in 0..32) {
        val t = i / 32f * 2f * PI.toFloat()
        val rr = r * (0.22f + 0.18f * sin(t * 2f))
        val p = polar(c, rr, t)
        if (!started) { loop.moveTo(p.x, p.y); started = true } else loop.lineTo(p.x, p.y)
    }
    loop.close()
    drawPath(loop, gold, style = Stroke(width = 3.2f, cap = StrokeCap.Round))
}

private fun DrawScope.spiral(c: Offset, r: Float, rice: Color) {
    val path = Path()
    var started = false
    for (i in 0..80) {
        val t = i / 80f * 4.2f * PI.toFloat()
        val rr = r * (0.08f + 0.9f * i / 80f)
        val p = polar(c, rr, t)
        if (!started) { path.moveTo(p.x, p.y); started = true } else path.lineTo(p.x, p.y)
    }
    drawPath(path, rice, style = Stroke(width = 3.4f, cap = StrokeCap.Round))
}

private fun DrawScope.star(c: Offset, r: Float, gold: Color, maroon: Color) {
    val path = Path()
    val points = 8
    for (i in 0 until points * 2) {
        val a = -PI.toFloat() / 2f + i * (PI.toFloat() / points)
        val rr = if (i % 2 == 0) r else r * 0.42f
        val p = polar(c, rr, a)
        if (i == 0) path.moveTo(p.x, p.y) else path.lineTo(p.x, p.y)
    }
    path.close()
    drawPath(path, maroon.copy(alpha = 0.85f))
    drawPath(path, gold, style = Stroke(3f, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

private fun DrawScope.butterfly(c: Offset, r: Float, maroon: Color, gold: Color) {
    fun wing(sign: Float) {
        val path = Path().apply {
            moveTo(c.x, c.y)
            cubicTo(c.x + sign * r * 0.15f, c.y - r * 0.7f, c.x + sign * r * 0.85f, c.y - r * 0.35f, c.x + sign * r * 0.55f, c.y)
            cubicTo(c.x + sign * r * 0.9f, c.y + r * 0.4f, c.x + sign * r * 0.2f, c.y + r * 0.55f, c.x, c.y)
            close()
        }
        drawPath(path, maroon.copy(alpha = 0.9f))
        drawPath(path, gold, style = Stroke(2.4f))
    }
    wing(-1f); wing(1f)
    drawLine(gold, Offset(c.x, c.y - r * 0.35f), Offset(c.x, c.y + r * 0.4f), 5f, StrokeCap.Round)
}

private fun DrawScope.pot(c: Offset, r: Float, terracotta: Color, green: Color, gold: Color) {
    val body = Path().apply {
        moveTo(c.x - r * 0.28f, c.y - r * 0.08f)
        quadraticTo(c.x - r * 0.55f, c.y + r * 0.15f, c.x - r * 0.32f, c.y + r * 0.5f)
        lineTo(c.x + r * 0.32f, c.y + r * 0.5f)
        quadraticTo(c.x + r * 0.55f, c.y + r * 0.15f, c.x + r * 0.28f, c.y - r * 0.08f)
        close()
    }
    drawPath(body, terracotta)
    drawCircle(gold, r * 0.16f, Offset(c.x, c.y - r * 0.22f))
    for (i in -2..2) {
        val leaf = polar(Offset(c.x, c.y - r * 0.38f), r * 0.28f, -PI.toFloat() / 2f + i * 0.35f)
        drawCircle(green, r * 0.08f, leaf)
    }
}

private fun DrawScope.sikku(c: Offset, r: Float, rice: Color) {
    for (k in 0..2) {
        val path = Path()
        for (i in 0..48) {
            val t = i / 48f * 2f * PI.toFloat()
            val rr = r * (0.28f + k * 0.18f)
            val p = Offset(c.x + rr * cos(t) * 1.05f, c.y + rr * sin(t + k * 0.6f))
            if (i == 0) path.moveTo(p.x, p.y) else path.lineTo(p.x, p.y)
        }
        path.close()
        drawPath(path, rice, style = Stroke(3.1f, cap = StrokeCap.Round))
    }
}

private fun DrawScope.sun(c: Offset, r: Float, gold: Color, yellow: Color) {
    for (i in 0 until 16) {
        val a = i * (2 * PI / 16).toFloat()
        drawLine(gold, polar(c, r * 0.22f, a), polar(c, r, a), 4.2f, StrokeCap.Round)
    }
    drawCircle(yellow, r * 0.22f, c)
}

private fun DrawScope.mango(c: Offset, r: Float, green: Color, gold: Color) {
    for (i in 0 until 10) {
        val a = i * (2 * PI / 10).toFloat()
        val tip = polar(c, r, a)
        val path = Path().apply {
            moveTo(c.x, c.y)
            quadraticTo(polar(c, r * 0.55f, a - 0.18f).x, polar(c, r * 0.55f, a - 0.18f).y, tip.x, tip.y)
            quadraticTo(polar(c, r * 0.55f, a + 0.18f).x, polar(c, r * 0.55f, a + 0.18f).y, c.x, c.y)
            close()
        }
        drawPath(path, green.copy(alpha = 0.9f))
        drawPath(path, gold, style = Stroke(1.8f))
    }
}
