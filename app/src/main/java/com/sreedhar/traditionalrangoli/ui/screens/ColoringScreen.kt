package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.DrawTool
import com.sreedhar.traditionalrangoli.data.DrawingSession
import com.sreedhar.traditionalrangoli.ui.components.ColorPaletteBar
import com.sreedhar.traditionalrangoli.ui.components.RangoliDrawingCanvas
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Ivory
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun ColoringScreen(session: DrawingSession, onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        session.tool = DrawTool.Fill
        session.snapToDots = false
        session.showGrid = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Skip", color = Muted, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.clickable(onClick = onDone))
            Spacer(Modifier.weight(1f))
            Text("Color & decorate", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
            Spacer(Modifier.weight(1f))
            Text("Done", color = Primary, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.clickable(onClick = onDone))
        }
        Text(
            "Tap enclosed spaces to fill. Add rice powder, flowers or a diya.",
            color = Muted,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 10.dp)
        )
        RangoliDrawingCanvas(session, Modifier.weight(1f).padding(horizontal = 16.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DecoTool(DrawTool.Fill, Icons.Filled.Palette, "Fill", session, Modifier.weight(1f))
            DecoTool(DrawTool.Rice, Icons.Filled.Circle, "Rice", session, Modifier.weight(1f))
            DecoTool(DrawTool.Flower, Icons.Filled.LocalFlorist, "Flower", session, Modifier.weight(1f))
            DecoTool(DrawTool.Diya, Icons.Filled.Whatshot, "Diya", session, Modifier.weight(1f))
            DecoTool(DrawTool.Dots, Icons.Filled.GridView, "Dots", session, Modifier.weight(1f))
        }
        ColorPaletteBar(selection = session.color, onSelect = { session.color = it })
        Spacer(Modifier.padding(bottom = 12.dp))
    }
}

@Composable
private fun DecoTool(tool: DrawTool, icon: ImageVector, title: String, session: DrawingSession, modifier: Modifier = Modifier) {
    val selected = session.tool == tool
    Column(
        modifier = modifier.clickable { session.tool = tool },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(if (selected) Primary else Paper)
                .border(1.dp, Gold.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = title, tint = if (selected) OnAccent else Primary, modifier = Modifier.size(16.dp))
        }
        Text(title, color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 6.dp))
    }
}
