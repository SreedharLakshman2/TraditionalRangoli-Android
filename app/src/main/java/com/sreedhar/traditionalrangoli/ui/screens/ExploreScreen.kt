package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.Festival
import com.sreedhar.traditionalrangoli.data.MotifKind
import com.sreedhar.traditionalrangoli.data.MotifTheme
import com.sreedhar.traditionalrangoli.data.PatternCatalog
import com.sreedhar.traditionalrangoli.data.PatternFamily
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.ui.components.CategoryCard
import com.sreedhar.traditionalrangoli.ui.components.RangoliCard
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Paper

@Composable
fun ExploreScreen(
    onOpenPattern: (RangoliPattern) -> Unit,
    onOpenGrid: (title: String, patterns: List<RangoliPattern>) -> Unit
) {
    var query by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SectionHeader("Explore Rangoli", "Traditional families, motifs and festivals")
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search patterns...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Muted) },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Paper,
                unfocusedContainerColor = Paper,
                focusedBorderColor = Gold,
                unfocusedBorderColor = Gold.copy(alpha = 0.4f),
                focusedTextColor = Ink,
                unfocusedTextColor = Ink
            )
        )
        if (query.isBlank()) {
            ExploreRow(
                title = "TRADITIONAL",
                items = PatternFamily.entries.mapNotNull { family ->
                    val items = PatternCatalog.all.filter { it.family == family }
                    items.firstOrNull()?.let { ExploreBucket(family.title, "⚬", it.motif, items) }
                },
                onOpenGrid = onOpenGrid
            )
            ExploreRow(
                title = "THEMES",
                items = MotifTheme.entries.mapNotNull { theme ->
                    val items = PatternCatalog.all.filter { it.theme == theme }
                    items.firstOrNull()?.let { ExploreBucket(theme.title, theme.symbol, it.motif, items) }
                },
                onOpenGrid = onOpenGrid
            )
            ExploreRow(
                title = "FESTIVALS",
                items = Festival.entries.mapNotNull { festival ->
                    val items = PatternCatalog.all.filter { it.festivals.contains(festival) }
                    items.firstOrNull()?.let { ExploreBucket(festival.title, "🪔", it.motif, items) }
                },
                onOpenGrid = onOpenGrid
            )
        } else {
            PatternCatalog.search(query).chunked(2).forEach { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                    row.forEach { pattern ->
                        RangoliCard(pattern, onClick = { onOpenPattern(pattern) }, modifier = Modifier.weight(1f))
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}

private data class ExploreBucket(
    val title: String,
    val symbol: String,
    val motif: MotifKind,
    val patterns: List<RangoliPattern>
)

@Composable
private fun ExploreRow(
    title: String,
    items: List<ExploreBucket>,
    onOpenGrid: (String, List<RangoliPattern>) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(title, color = Gold, fontSize = 12.sp, letterSpacing = 1.6.sp)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items.forEach { item ->
                Box(Modifier.width(148.dp)) {
                    CategoryCard(item.title, item.symbol, item.motif) {
                        onOpenGrid(item.title, item.patterns)
                    }
                }
            }
        }
    }
}
