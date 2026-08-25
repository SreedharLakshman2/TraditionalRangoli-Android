package com.sreedhar.traditionalrangoli.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.data.PatternCatalog
import com.sreedhar.traditionalrangoli.data.RangoliPattern
import com.sreedhar.traditionalrangoli.data.UserArtwork
import com.sreedhar.traditionalrangoli.ui.AppTab
import com.sreedhar.traditionalrangoli.ui.LocalArtworks
import com.sreedhar.traditionalrangoli.ui.LocalSettings
import com.sreedhar.traditionalrangoli.ui.components.RangoliCard
import com.sreedhar.traditionalrangoli.ui.components.SectionHeader
import com.sreedhar.traditionalrangoli.ui.components.paperCard
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.OnAccent
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun SavedScreen(
    onGoToTab: (AppTab) -> Unit,
    onContinue: (UserArtwork) -> Unit,
    onOpenPattern: (RangoliPattern) -> Unit
) {
    var segment by remember { mutableIntStateOf(0) }
    val artworks = LocalArtworks.current.artworks
    val favorites = LocalSettings.current.favoritePatternIds
    val creations = artworks
    val favoriteArt = artworks.filter { it.isFavorite }
    val favoritePatterns = PatternCatalog.all.filter { favorites.contains(it.id) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SectionHeader("My Rangolis")
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 16.dp)) {
            listOf("My Creations", "Favorites").forEachIndexed { index, label ->
                SegmentedButton(
                    selected = segment == index,
                    onClick = { segment = index },
                    shape = SegmentedButtonDefaults.itemShape(index, 2)
                ) { Text(label) }
            }
        }
        if (segment == 0) {
            if (creations.isEmpty()) {
                EmptyGallery(
                    title = "Your rangoli gallery is waiting.",
                    subtitle = "Create your first traditional rangoli.",
                    button = "Create Rangoli"
                ) { onGoToTab(AppTab.Create) }
            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    creations.chunked(2).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                            row.forEach { art ->
                                ArtworkThumb(art, Modifier.weight(1f)) { onContinue(art) }
                            }
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        } else if (favoriteArt.isEmpty() && favoritePatterns.isEmpty()) {
            EmptyGallery(
                title = "Save patterns you love.",
                subtitle = "Favorite a courtyard piece to keep it close.",
                button = "Explore Patterns"
            ) { onGoToTab(AppTab.Explore) }
        } else {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                favoriteArt.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        row.forEach { art ->
                            ArtworkThumb(art, Modifier.weight(1f)) { onContinue(art) }
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                favoritePatterns.chunked(2).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                        row.forEach { pattern ->
                            RangoliCard(pattern, onClick = { onOpenPattern(pattern) }, modifier = Modifier.weight(1f))
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ArtworkThumb(art: UserArtwork, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val bitmap = remember(art.thumbnailPath) { art.thumbnailPath?.let { BitmapFactory.decodeFile(it) } }
    Column(
        modifier = modifier.paperCard(18.dp).clickable(onClick = onClick).padding(10.dp)
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = art.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(16.dp))
            )
        }
        Text(art.title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Ink, modifier = Modifier.padding(top = 10.dp))
        Text("${art.gridSize} × ${art.gridSize}", color = Muted, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
    }
}

@Composable
private fun EmptyGallery(title: String, subtitle: String, button: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, fontFamily = FontFamily.Serif, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, color = Ink, textAlign = TextAlign.Center)
        Text(subtitle, color = Muted, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
        Spacer(Modifier.height(20.dp))
        Text(
            button,
            color = OnAccent,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Primary)
                .clickable(onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 14.dp)
        )
    }
}
