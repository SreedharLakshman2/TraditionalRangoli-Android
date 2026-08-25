package com.sreedhar.traditionalrangoli.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.R
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.components.paperCard
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    val settings = LocalSettings.current
    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        SectionHeader("My Rangoli Journey")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            StatTile("${settings.patternsCompleted}", "Patterns", Modifier.weight(1f))
            StatTile(settings.levelTitle.substringAfterLast(' ').ifBlank { "Beginner" }, "Level", Modifier.weight(1f))
            StatTile("${settings.xp}", "Total XP", Modifier.weight(1f))
            StatTile(compactStyle(settings.favoriteStyle), "Style", Modifier.weight(1f))
        }
        Column(modifier = Modifier.fillMaxWidth().paperCard().padding(16.dp)) {
            Text(settings.levelTitle, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Ink)
            LinearProgressIndicator(
                progress = { settings.levelProgress },
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
            SettingRow("Sound", settings.soundEnabled) { settings.setSound(it) }
            SettingRow("Haptics", settings.hapticsEnabled) { settings.setHaptics(it) }
            SettingRow("Show Guides", settings.showGuides) { settings.setGuides(it) }
            Text("Default Grid", fontSize = 16.sp, color = Ink, modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
                listOf(7, 9, 11, 15).forEach { size ->
                    val active = settings.defaultGrid == size
                    Text(
                        text = "$size × $size",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (active) OnAccent else Ink,
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(if (active) Primary else Paper)
                            .clickable { settings.setGridSize(size) }
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("Theme", fontSize = 16.sp, color = Ink, modifier = Modifier.weight(1f))
                Text("Ivory courtyard", color = Muted, fontSize = 15.sp)
            }
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
    Column(
        modifier = modifier
            .paperCard(18.dp)
            .padding(horizontal = 6.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = Ink,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            label,
            color = Muted,
            fontSize = 10.sp,
            maxLines = 1,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

private fun compactStyle(value: String): String = when {
    value.equals("Traditional Motifs", ignoreCase = true) -> "Traditional"
    else -> value
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
