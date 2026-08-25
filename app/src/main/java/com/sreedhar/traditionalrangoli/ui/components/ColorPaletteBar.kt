package com.sreedhar.traditionalrangoli.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.sreedhar.traditionalrangoli.data.PowderSwatch
import com.sreedhar.traditionalrangoli.ui.theme.Ink

@Composable
fun ColorPaletteBar(selection: PowderSwatch, onSelect: (PowderSwatch) -> Unit) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 4.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        PowderSwatch.entries.forEach { swatch ->
            val selected = swatch == selection
            Box(
                modifier = Modifier
                    .size(if (selected) 30.dp else 24.dp)
                    .shadow(if (selected) 6.dp else 0.dp, CircleShape)
                    .clip(CircleShape)
                    .background(swatch.color)
                    .border(if (selected) 2.dp else 1.dp, Ink.copy(alpha = if (selected) 0.55f else 0.12f), CircleShape)
                    .clickable { onSelect(swatch) }
            )
        }
    }
}
