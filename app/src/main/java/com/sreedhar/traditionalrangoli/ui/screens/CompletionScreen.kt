package com.sreedhar.traditionalrangoli.ui.screens

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.sreedhar.traditionalrangoli.ads.AdsManager
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.data.UserArtwork
import com.sreedhar.traditionalrangoli.ui.LocalArtworks
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.MetaChip
import com.sreedhar.traditionalrangoli.ui.components.MotifPreview
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Ivory
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Paper
import com.sreedhar.traditionalrangoli.ui.theme.Primary
import java.io.File

@Composable
fun CompletionScreen(
    artwork: UserArtwork,
    pattern: RangoliPattern?,
    onClose: () -> Unit,
    onCreateAnother: () -> Unit
) {
    val settings = LocalSettings.current
    val artworks = LocalArtworks.current
    val context = LocalContext.current
    val reward = pattern?.xpReward ?: 50
    var shownXp by remember { mutableIntStateOf(0) }
    var saved by remember { mutableStateOf(false) }
    var favorited by remember { mutableStateOf(artwork.isFavorite) }
    val thumb = remember(artwork.thumbnailPath) {
        artwork.thumbnailPath?.let { BitmapFactory.decodeFile(it) }
    }

    LaunchedEffect(artwork.id) {
        settings.award(pattern)
        shownXp = reward
        AdsManager.showInterstitialAfterRangoli(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text("Beautiful Rangoli! 🪷", fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 30.sp, color = Ink, textAlign = TextAlign.Center)
        if (thumb != null) {
            Image(
                bitmap = thumb.asImageBitmap(),
                contentDescription = artwork.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
            )
        } else {
            MotifPreview(pattern?.motif ?: com.sreedhar.traditionalrangoli.data.MotifKind.LotusDot, Modifier.fillMaxWidth().clip(RoundedCornerShape(28.dp)))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetaChip("+$shownXp XP")
            MetaChip("Pattern Completed")
            pattern?.let { MetaChip("${it.stepCount} Steps") }
        }
        pattern?.let { MetaChip("Difficulty: ${it.difficulty.title}") }
        ActionButton(if (saved) "Saved" else "Save Rangoli") {
            artworks.save(artwork)
            saved = true
        }
        ActionButton("Share", filled = false) {
            val path = artwork.thumbnailPath ?: return@ActionButton
            val uri = FileProvider.getUriForFile(context, "${context.packageName}.files", File(path))
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, "Share rangoli"))
        }
        ActionButton("Create Another", filled = false, onClick = onCreateAnother)
        Text(
            if (favorited) "In Favorites" else "Add to Favorites",
            color = Primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.clickable {
                artworks.save(artwork.copy(isFavorite = true))
                favorited = true
            }
        )
        Text("Done", color = Muted, modifier = Modifier.clickable(onClick = onClose).padding(bottom = 12.dp))
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun ActionButton(title: String, filled: Boolean = true, onClick: () -> Unit) {
    Text(
        text = title,
        color = if (filled) OnAccent else Primary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .background(if (filled) Primary else Paper)
            .clickable(onClick = onClick)
            .padding(vertical = 15.dp),
        textAlign = TextAlign.Center
    )
}
