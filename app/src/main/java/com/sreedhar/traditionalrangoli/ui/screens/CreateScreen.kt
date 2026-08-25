package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted

@Composable
fun CreateScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Create",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = Ink
        )
        Text(
            text = "Dot-grid, freehand, and template studios.",
            color = Muted,
            fontSize = 16.sp
        )
    }
}
