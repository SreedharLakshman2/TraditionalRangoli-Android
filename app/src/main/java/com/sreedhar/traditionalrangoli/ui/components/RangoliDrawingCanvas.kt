package com.sreedhar.traditionalrangoli.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.sreedhar.traditionalrangoli.data.DrawingSession
import com.sreedhar.traditionalrangoli.ui.theme.Gold

@Composable
fun RangoliDrawingCanvas(session: DrawingSession, modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, Gold.copy(alpha = 0.45f), RoundedCornerShape(28.dp))
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                session.begin(offset.x, offset.y, size.width.toFloat(), size.height.toFloat())
                            },
                            onDrag = { change, _ ->
                                change.consume()
                                session.move(change.position.x, change.position.y, size.width.toFloat(), size.height.toFloat())
                            },
                            onDragEnd = { session.end() },
                            onDragCancel = { session.end() }
                        )
                    }
            ) {
                drawIntoCanvas { canvas ->
                    ArtworkPainter.paint(canvas.nativeCanvas, size.width, size.height, session)
                }
            }
        }
    }
}
