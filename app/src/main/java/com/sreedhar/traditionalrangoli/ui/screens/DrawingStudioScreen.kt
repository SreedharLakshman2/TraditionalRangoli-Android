package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.BrushSize
import com.sreedhar.traditionalrangoli.data.DrawingSession
import com.sreedhar.traditionalrangoli.data.StudioKind
import com.sreedhar.traditionalrangoli.data.SymmetryMode
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
fun DrawingStudioScreen(
    session: DrawingSession,
    onBack: () -> Unit,
    onDone: () -> Unit
) {
    var showMore by remember { mutableStateOf(false) }
    var showSymmetry by remember { mutableStateOf(false) }
    var showBrush by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIcon(Icons.AutoMirrored.Filled.ArrowBack, "Back") { onBack() }
            Spacer(Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(session.title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Ink)
                Text(
                    if (session.symmetry == SymmetryMode.None) "Free line" else session.symmetry.title,
                    fontSize = 11.sp,
                    color = Muted
                )
            }
            Spacer(Modifier.weight(1f))
            CircleIcon(Icons.Filled.MoreHoriz, "More") { showMore = true }
        }

        RangoliDrawingCanvas(
            session = session,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (session.studio == StudioKind.Dots || session.studio == StudioKind.Template) {
                GridPicker(selected = session.gridSize, onSelect = { session.gridSize = it })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CircleIcon(Icons.AutoMirrored.Filled.Undo, "Undo", enabled = session.canUndo) { session.undo() }
                CircleIcon(Icons.AutoMirrored.Filled.Redo, "Redo", enabled = session.canRedo) { session.redo() }
                CircleIcon(Icons.Filled.GridOn, "Toggle grid", active = session.showGrid) { session.showGrid = !session.showGrid }
                Box {
                    CircleIcon(Icons.Filled.Sync, "Symmetry", active = session.symmetry != SymmetryMode.None) {
                        showSymmetry = true
                    }
                    DropdownMenu(expanded = showSymmetry, onDismissRequest = { showSymmetry = false }) {
                        SymmetryMode.entries.forEach { mode ->
                            DropdownMenuItem(
                                text = { Text(mode.title) },
                                onClick = {
                                    session.symmetry = mode
                                    showSymmetry = false
                                }
                            )
                        }
                    }
                }
                Box {
                    CircleIcon(Icons.Filled.Brush, "Brush size", active = true) { showBrush = true }
                    DropdownMenu(expanded = showBrush, onDismissRequest = { showBrush = false }) {
                        BrushSize.entries.forEach { size ->
                            DropdownMenuItem(
                                text = { Text(size.title) },
                                onClick = {
                                    session.brush = size
                                    showBrush = false
                                }
                            )
                        }
                    }
                }
                CircleIcon(Icons.Filled.Delete, "Clear") { session.clear() }
            }
            ColorPaletteBar(selection = session.color, onSelect = { session.color = it })
            Text(
                text = "Done",
                color = OnAccent,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50))
                    .background(Primary)
                    .clickable(onClick = onDone)
                    .padding(vertical = 15.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }

    if (showMore) {
        AlertDialog(
            onDismissRequest = { showMore = false },
            title = { Text("Canvas") },
            text = {
                Column {
                    TextButton(onClick = { session.showGrid = !session.showGrid; showMore = false }) { Text("Toggle grid") }
                    TextButton(onClick = { session.snapToDots = !session.snapToDots; showMore = false }) { Text("Toggle snap") }
                    TextButton(onClick = { session.showGuides = !session.showGuides; showMore = false }) { Text("Toggle guides") }
                    TextButton(onClick = { session.clear(); showMore = false }) { Text("Clear canvas") }
                }
            },
            confirmButton = {
                TextButton(onClick = { showMore = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun GridPicker(selected: Int, onSelect: (Int) -> Unit) {
    val sizes = remember(selected) {
        val base = listOf(7, 9, 11, 15)
        if (selected in base) base else (base + selected).sorted()
    }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        sizes.forEach { size ->
            val active = size == selected
            Text(
                text = "${size}×${size}",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (active) OnAccent else Ink,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (active) Primary else Paper)
                    .clickable { onSelect(size) }
                    .padding(horizontal = 10.dp, vertical = 7.dp)
            )
        }
    }
}

@Composable
private fun CircleIcon(
    icon: ImageVector,
    label: String,
    enabled: Boolean = true,
    active: Boolean = enabled,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Paper)
            .border(1.dp, Gold.copy(alpha = 0.28f), CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (active) Primary else Muted,
            modifier = Modifier.size(16.dp)
        )
    }
}
