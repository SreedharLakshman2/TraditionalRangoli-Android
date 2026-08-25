package com.sreedhar.traditionalrangoli.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun CourtyardTabBar(
    selection: AppTab,
    onSelect: (AppTab) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp)
            .shadow(12.dp, RoundedCornerShape(40.dp))
            .clip(RoundedCornerShape(40.dp))
            .background(Paper)
            .border(1.dp, Gold.copy(alpha = 0.35f), RoundedCornerShape(40.dp))
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AppTab.entries.forEach { tab ->
            val selected = tab == selection
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSelect(tab) }
                    .padding(vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(if (tab == AppTab.Create) 46.dp else 38.dp, 28.dp)
                        .clip(CircleShape)
                        .background(if (selected) Primary else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                        tint = if (selected) OnAccent else Muted,
                        modifier = Modifier.size(if (tab == AppTab.Create) 18.dp else 16.dp)
                    )
                }
                Text(
                    text = tab.title,
                    color = if (selected) Primary else Muted,
                    fontSize = 10.sp
                )
            }
        }
    }
}
