package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.MotifKind
import com.sreedhar.traditionalrangoli.ui.components.MotifPreview
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.components.paperCard
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Green
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Primary
import com.sreedhar.traditionalrangoli.ui.theme.Secondary

@Composable
fun CreateScreen(
    onStartDots: () -> Unit,
    onStartFreehand: () -> Unit,
    onChooseTemplate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader("Create", "Three ways to lay powder on the floor")
        StudioRow("DOT RANGOLI", "Create using a dot grid.", MotifKind.Pulli, Primary, onStartDots)
        StudioRow("FREEHAND", "Draw your own rangoli.", MotifKind.FestivalFlower, Secondary, onStartFreehand)
        StudioRow("TEMPLATE", "Start from a traditional pattern.", MotifKind.LotusDot, Green, onChooseTemplate)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun StudioRow(title: String, subtitle: String, motif: MotifKind, tint: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .paperCard(30.dp)
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MotifPreview(motif, Modifier.size(96.dp).clip(RoundedCornerShape(20.dp)))
        Column(Modifier.weight(1f)) {
            Text(title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
            Text(subtitle, color = Muted, fontSize = 15.sp, modifier = Modifier.padding(top = 4.dp))
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(36.dp, 4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(tint)
            )
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = Gold)
    }
}
