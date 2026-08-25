package com.sreedhar.traditionalrangoli.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sreedhar.traditionalrangoli.R
import com.sreedhar.traditionalrangoli.ui.theme.Ink
import com.sreedhar.traditionalrangoli.ui.theme.Muted
import com.sreedhar.traditionalrangoli.ui.theme.Primary

@Composable
fun ProfileScreen() {
    val context = LocalContext.current
    fun open(url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "My Rangoli Journey",
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = Ink
        )
        Text(
            text = "Traditional Rangoli is a courtyard companion for kolam and rangoli. Artwork stays on this device.",
            color = Muted,
            fontSize = 16.sp
        )
        Text(
            text = "Support",
            color = Primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { open(context.getString(R.string.support_url)) }
        )
        Text(
            text = "Privacy Policy",
            color = Primary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.clickable { open(context.getString(R.string.privacy_url)) }
        )
        Text(
            text = "© 2026 Sai Laksha Technologies",
            color = Muted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
