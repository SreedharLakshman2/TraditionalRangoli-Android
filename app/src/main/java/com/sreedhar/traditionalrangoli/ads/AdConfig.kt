package com.sreedhar.traditionalrangoli.ads

import com.sreedhar.traditionalrangoli.BuildConfig

/**
 * Android Traditional Rangoli in AdMob (package `com.sreedhar.traditionalrangoli`).
 * Debug/emulator uses Google sample units so the account is not flagged.
 */
object AdConfig {
    const val PRODUCTION_APP_ID = "ca-app-pub-9471606055191983~5469714405"
    const val PRODUCTION_BANNER = "ca-app-pub-9471606055191983/5707341957"
    const val PRODUCTION_INTERSTITIAL = "ca-app-pub-9471606055191983/2843551069"

    const val TEST_BANNER = "ca-app-pub-3940256099942544/6300978111"
    const val TEST_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"

    val bannerAdUnitId: String
        get() = if (BuildConfig.DEBUG) TEST_BANNER else PRODUCTION_BANNER

    val interstitialAdUnitId: String
        get() = if (BuildConfig.DEBUG) TEST_INTERSTITIAL else PRODUCTION_INTERSTITIAL
}
