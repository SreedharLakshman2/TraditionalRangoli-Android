package com.sreedhar.traditionalrangoli.ads

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.os.Handler
import android.os.Looper
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdsManager {
    var isReady by mutableStateOf(false)
        private set

    private var interstitial: InterstitialAd? = null
    private var didBootstrap = false
    private var isLoading = false
    private var lastInterstitialAt = 0L
    private val cooldownMs = 45_000L
    private val main = Handler(Looper.getMainLooper())

    fun bootstrap(context: Context) {
        if (didBootstrap) return
        didBootstrap = true
        val app = context.applicationContext
        main.postDelayed({
            MobileAds.initialize(app) {
                main.post {
                    isReady = true
                    preload(app)
                }
            }
        }, 1_200)
    }

    fun preload(context: Context) {
        if (isLoading || interstitial != null) return
        isLoading = true
        InterstitialAd.load(
            context.applicationContext,
            AdConfig.interstitialAdUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    isLoading = false
                    interstitial = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    isLoading = false
                    interstitial = null
                }
            }
        )
    }

    fun showInterstitialAfterRangoli(context: Context, delayMs: Long = 1_150, then: (() -> Unit)? = null) {
        main.postDelayed({ showIfAvailable(context, then) }, delayMs)
    }

    private fun showIfAvailable(context: Context, then: (() -> Unit)?) {
        val now = System.currentTimeMillis()
        val ad = interstitial
        val activity = context.findActivity()
        if (ad == null || activity == null || now - lastInterstitialAt < cooldownMs) {
            preload(context)
            then?.invoke()
            return
        }
        lastInterstitialAt = now
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitial = null
                preload(context)
                then?.invoke()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitial = null
                preload(context)
                then?.invoke()
            }
        }
        interstitial = null
        ad.show(activity)
    }
}

private fun Context.findActivity(): Activity? {
    var ctx: Context = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}
