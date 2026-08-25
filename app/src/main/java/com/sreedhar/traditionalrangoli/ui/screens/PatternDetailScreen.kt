package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.MetaChip
import com.sreedhar.traditionalrangoli.ui.components.MotifPreview
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun PatternDetailScreen(
    pattern: RangoliPattern,
    onBack: () -> Unit,
    onStartDrawing: () -> Unit,
    onLearn: () -> Unit
) {
    val settings = LocalSettings.current
    val favorited = settings.favoritePatternIds.contains(pattern.id)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = onBack)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
            Text("  Back", color = Primary, fontWeight = FontWeight.SemiBold)
        }
        MotifPreview(pattern.motif, Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)))
        Text(pattern.title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, color = Ink)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetaChip(pattern.difficulty.title)
            MetaChip("${pattern.gridSize} × ${pattern.gridSize} dots")
            MetaChip("${pattern.stepCount} steps")
            MetaChip("${pattern.estimatedMinutes} min")
        }
        Text(pattern.description, color = Muted, fontSize = 16.sp)
        Text(
            "Learn Step-by-Step",
            color = OnAccent,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Primary)
                .clickable(onClick = onLearn)
                .padding(vertical = 15.dp),
            textAlign = TextAlign.Center
        )
        Text(
            "Start Drawing",
            color = Primary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Paper)
                .clickable(onClick = onStartDrawing)
                .padding(vertical = 15.dp),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth().clickable { settings.toggleFavorite(pattern.id) }.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (favorited) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                contentDescription = null,
                tint = Primary
            )
            Text(if (favorited) "  Favorited" else "  Favorite", color = Primary, fontWeight = FontWeight.SemiBold)
        }
        Text("What's inside", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
        listOf("Dot placement", "Symmetry", "Guided strokes", "Coloring").forEach { item ->
            Text("•  $item", color = Muted, fontSize = 15.sp)
        }
    }
}

@Composable
fun PatternGridScreen(title: String, patterns: List<RangoliPattern>, onBack: () -> Unit, onOpenPattern: (RangoliPattern) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable(onClick = onBack).padding(bottom = 16.dp)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Ink)
            Text("  $title", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = Ink)
        }
        patterns.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                row.forEach { pattern ->
                    com.sreedhar.traditionalrangoli.ui.components.RangoliCard(
                        pattern = pattern,
                        onClick = { onOpenPattern(pattern) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}
