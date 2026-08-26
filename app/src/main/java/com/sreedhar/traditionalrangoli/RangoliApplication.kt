package com.sreedhar.traditionalrangoli

import android.app.Application
import com.sreedhar.traditionalrangoli.ads.AdsManager
import com.sreedhar.traditionalrangoli.data.ArtworkStore
import com.sreedhar.traditionalrangoli.data.SettingsStore

class RangoliApplication : Application() {
    lateinit var settings: SettingsStore
        private set
    lateinit var artworks: ArtworkStore
        private set

    override fun onCreate() {
        super.onCreate()
        settings = SettingsStore(this)
        artworks = ArtworkStore(this)
        AdsManager.bootstrap(this)
    }
}
