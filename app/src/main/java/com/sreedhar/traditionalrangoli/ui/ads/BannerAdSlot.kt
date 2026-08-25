package com.sreedhar.traditionalrangoli.ui.ads

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.sreedhar.traditionalrangoli.ads.AdConfig
import com.sreedhar.traditionalrangoli.ads.AdsManager
import com.sreedhar.traditionalrangoli.ui.theme.Gold
import com.sreedhar.traditionalrangoli.ui.theme.Ivory

@Composable
fun AdBannerSlot(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var widthDp by remember { mutableStateOf(320) }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Ivory)
    ) {
        if (AdsManager.isReady) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { ctx ->
                    AdView(ctx).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        setAdSize(AdSize.BANNER)
                        adUnitId = AdConfig.bannerAdUnitId
                        loadAd(AdRequest.Builder().build())
                    }
                },
                update = { view ->
                    val next = with(density) { view.width.takeIf { it > 0 }?.toDp()?.value?.toInt() } ?: widthDp
                    if (next > 0) widthDp = next
                }
            )
        }
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Gold.copy(alpha = 0.28f)))
    }
    DisposableEffect(Unit) { onDispose { } }
}
