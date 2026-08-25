package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.BrowseCollection
import com.sreedhar.traditionalrangoli.data.PatternCatalog
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.CategoryCard
import com.sreedhar.traditionalrangoli.ui.components.MetaChip
import com.sreedhar.traditionalrangoli.ui.components.MotifPreview
import com.sreedhar.traditionalrangoli.ui.components.RangoliCard
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.components.paperCard
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary
import java.util.Calendar

@Composable
fun HomeScreen(
    onOpenPattern: (RangoliPattern) -> Unit,
    onOpenCollection: (BrowseCollection) -> Unit
) {
    val daily = PatternCatalog.daily
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Column(Modifier.weight(1f)) {
                Text(greeting(), fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 30.sp, color = Ink)
                Text("Create something beautiful today.", color = Muted, fontSize = 16.sp, modifier = Modifier.padding(top = 6.dp))
            }
            Column(
                modifier = Modifier.paperCard(14.dp).padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🔥", fontSize = 18.sp)
                Text("${LocalSettings.current.streak}", fontWeight = FontWeight.SemiBold, color = Primary, fontSize = 14.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .background(Brush.verticalGradient(listOf(Paper, androidx.compose.ui.graphics.Color(0xFFF3E6D0))))
                .paperCard(30.dp)
                .clickable { onOpenPattern(daily) }
                .padding(18.dp)
        ) {
            Row {
                Text("DAILY RANGOLI", color = Gold, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.4.sp)
                Spacer(Modifier.weight(1f))
                MetaChip(daily.difficulty.title)
            }
            MotifPreview(daily.motif, Modifier.fillMaxWidth().padding(top = 14.dp).clip(RoundedCornerShape(22.dp)))
            Text(daily.title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, color = Ink, modifier = Modifier.padding(top = 12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(top = 10.dp)) {
                MetaChip("${daily.gridSize} × ${daily.gridSize} dots")
                MetaChip("${daily.stepCount} steps")
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 14.dp)) {
                Text(
                    "Start Drawing",
                    color = OnAccent,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(Primary)
                        .padding(horizontal = 22.dp, vertical = 12.dp)
                )
            }
        }

        SectionHeader("Explore Collections")
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BrowseCollection.entries.forEach { collection ->
                Box(Modifier.width(148.dp)) {
                    CategoryCard(
                        title = collection.title,
                        symbol = collection.symbol,
                        motif = PatternCatalog.matching(collection).firstOrNull()?.motif ?: daily.motif,
                        onClick = { onOpenCollection(collection) }
                    )
                }
            }
        }

        SectionHeader("Popular Patterns", "Large, slow-drawn courtyard pieces")
        PatternCatalog.popular.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { pattern ->
                    RangoliCard(pattern, onClick = { onOpenPattern(pattern) }, modifier = Modifier.weight(1f))
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}

private fun greeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when (hour) {
        in 5..11 -> "Good Morning 👋"
        in 12..16 -> "Good Afternoon 👋"
        else -> "Good Evening 👋"
    }
}
