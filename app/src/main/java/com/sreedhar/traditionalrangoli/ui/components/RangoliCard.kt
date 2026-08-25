package com.sreedhar.traditionalrangoli.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted

@Composable
fun RangoliCard(pattern: RangoliPattern, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .paperCard(18.dp)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        MotifPreview(
            motif = pattern.motif,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = pattern.title,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Ink,
            modifier = Modifier.padding(top = 10.dp)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 6.dp, bottom = 4.dp)) {
            MetaChip(pattern.difficulty.title)
            MetaChip("${pattern.gridSize} × ${pattern.gridSize}")
        }
        Text(text = pattern.family.title, color = Muted, fontSize = 12.sp)
    }
}

@Composable
fun CategoryCard(title: String, symbol: String, motif: com.sreedhar.traditionalrangoli.data.MotifKind, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .paperCard(18.dp)
            .clickable(onClick = onClick)
            .padding(10.dp)
    ) {
        MotifPreview(
            motif = motif,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
        )
        Text(
            text = "$symbol  $title",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Ink,
            modifier = Modifier.padding(top = 8.dp, start = 4.dp, bottom = 4.dp)
        )
    }
}
