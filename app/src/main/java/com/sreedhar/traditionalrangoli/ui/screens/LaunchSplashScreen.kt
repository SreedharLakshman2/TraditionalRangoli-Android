package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.ui.theme.Ivory
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun LaunchSplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = "Traditional Rangoli",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
            color = Primary
        )
        Text(
            text = "Learn kolam & courtyard art",
            color = Muted,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(Color.Cyan, Color.Magenta, Color(0xFFFF8AB4), Color(0xFFFF9800)).forEach { color ->
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(color)
                )
            }
        }
        Text(
            text = "Sreeo Studio",
            modifier = Modifier.padding(top = 10.dp),
            fontWeight = FontWeight.SemiBold,
            style = androidx.compose.ui.text.TextStyle(
                brush = Brush.horizontalGradient(
                    listOf(Color.Cyan, Color.Magenta, Color(0xFFFF8AB4), Color(0xFFFF9800))
                )
            )
        )
        Text(
            text = "© 2026 Sai Laksha Technologies",
            color = Muted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )
        Spacer(Modifier.height(12.dp))
    }
}
