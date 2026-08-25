package com.sreedhar.traditionalrangoli.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.ui.AppTab
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun SavedScreen(onGoToTab: (AppTab) -> Unit) {
    var segment by remember { mutableIntStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SectionHeader("My Rangolis")
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            listOf("My Creations", "Favorites").forEachIndexed { index, label ->
                SegmentedButton(
                    selected = segment == index,
                    onClick = { segment = index },
                    shape = SegmentedButtonDefaults.itemShape(index, 2)
                ) { Text(label) }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (segment == 0) "Your rangoli gallery is waiting." else "Save patterns you love.",
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Ink,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (segment == 0) "Create your first traditional rangoli." else "Favorite a courtyard piece to keep it close.",
                color = Muted,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = if (segment == 0) "Create Rangoli" else "Explore Patterns",
                color = OnAccent,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Primary)
                    .clickable { onGoToTab(if (segment == 0) AppTab.Create else AppTab.Explore) }
                    .padding(horizontal = 24.dp, vertical = 14.dp)
            )
        }
    }
}
