package com.sreedhar.traditionalrangoli.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Maroon
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Paper

fun Modifier.paperCard(radius: Dp = 24.dp): Modifier = this
    .shadow(10.dp, RoundedCornerShape(radius), ambientColor = Color.Black.copy(alpha = 0.08f))
    .clip(RoundedCornerShape(radius))
    .background(Paper)
    .border(1.dp, Gold.copy(alpha = 0.4f), RoundedCornerShape(radius))

@Composable
fun SectionHeader(title: String, subtitle: String? = null) {
    Column {
        Text(
            text = title,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = Ink
        )
        if (subtitle != null) {
            Text(text = subtitle, color = Muted, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
        }
    }
}

@Composable
fun MetaChip(text: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Gold.copy(alpha = 0.18f))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(text = text, color = Maroon, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}
