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
import com.sreedhar.traditionalrangoli.data.BrowseCollection
import com.sreedhar.traditionalrangoli.data.Festival
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
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun ExploreScreen(
    onOpenPattern: (RangoliPattern) -> Unit,
    onOpenCollection: (BrowseCollection) -> Unit
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
            ExploreRow("TRADITIONAL", PatternFamily.entries.mapNotNull { family ->
                val items = PatternCatalog.all.filter { it.family == family }
                items.firstOrNull()?.let { Triple(family.title, "⚬", it) }
            }, onOpenPattern)
            ExploreRow("THEMES", MotifTheme.entries.mapNotNull { theme ->
                val items = PatternCatalog.all.filter { it.theme == theme }
                items.firstOrNull()?.let { Triple(theme.title, theme.symbol, it) }
            }, onOpenPattern)
            ExploreRow("FESTIVALS", Festival.entries.mapNotNull { festival ->
                val items = PatternCatalog.all.filter { it.festivals.contains(festival) }
                items.firstOrNull()?.let { Triple(festival.title, "🪔", it) }
            }, onOpenPattern)
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

@Composable
private fun ExploreRow(
    title: String,
    items: List<Triple<String, String, RangoliPattern>>,
    onOpenPattern: (RangoliPattern) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(title, color = Gold, fontSize = 12.sp, letterSpacing = 1.6.sp)
        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items.forEach { (label, symbol, pattern) ->
                Box(Modifier.width(148.dp)) {
                    CategoryCard(label, symbol, pattern.motif) { onOpenPattern(pattern) }
                }
            }
        }
    }
}
