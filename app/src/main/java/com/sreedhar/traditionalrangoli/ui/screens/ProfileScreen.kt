package com.sreedhar.traditionalrangoli.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.R
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.components.paperCard
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
    var sound by remember { mutableStateOf(true) }
    var haptics by remember { mutableStateOf(true) }
    var guides by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SectionHeader("My Rangoli Journey")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatTile("0", "Patterns", Modifier.weight(1f))
            StatTile("Beginner", "Level", Modifier.weight(1f))
            StatTile("0", "Total XP", Modifier.weight(1f))
            StatTile("Lotus", "Style", Modifier.weight(1f))
        }
        Column(modifier = Modifier.fillMaxWidth().paperCard().padding(16.dp)) {
            Text("Rangoli Beginner", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
            LinearProgressIndicator(
                progress = { 0.08f },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(8.dp),
                color = Primary,
                trackColor = Gold.copy(alpha = 0.18f)
            )
            Text("Keep a quiet daily practice. XP arrives when a rangoli is completed.", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 10.dp))
        }
        Text("Achievements", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Badge("🪷", "First Rangoli", Modifier.weight(1f))
            Badge("🌸", "5 Patterns", Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            Badge("🔥", "7 Day Streak", Modifier.weight(1f))
            Badge("🏆", "Master Creator", Modifier.weight(1f))
        }
        Column(modifier = Modifier.fillMaxWidth().paperCard().padding(horizontal = 16.dp, vertical = 4.dp)) {
            SettingRow("Sound", sound) { sound = it }
            SettingRow("Haptics", haptics) { haptics = it }
            SettingRow("Show Guides", guides) { guides = it }
        }
        Column(modifier = Modifier.fillMaxWidth().paperCard().padding(18.dp)) {
            Text("About", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
            Text(
                "Traditional Rangoli is a courtyard companion for kolam and rangoli. Artwork stays on this device.",
                color = Muted,
                fontSize = 15.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text("Support", color = Primary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, modifier = Modifier.padding(top = 14.dp).clickable { open(context.getString(R.string.support_url)) })
            Text("Privacy Policy", color = Primary, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, modifier = Modifier.padding(top = 12.dp).clickable { open(context.getString(R.string.privacy_url)) })
            Text("© 2026 Sai Laksha Technologies", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 16.dp))
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun StatTile(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.paperCard(18.dp).padding(vertical = 14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 13.sp, color = Ink)
        Text(label, color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 6.dp))
    }
}

@Composable
private fun Badge(emoji: String, title: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.paperCard(18.dp).padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 28.sp, modifier = Modifier.padding(bottom = 8.dp))
        Text(title, color = Muted, fontSize = 12.sp)
    }
}

@Composable
private fun SettingRow(title: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(title, fontSize = 16.sp, color = Ink, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChange, colors = SwitchDefaults.colors(checkedTrackColor = Primary))
    }
}
