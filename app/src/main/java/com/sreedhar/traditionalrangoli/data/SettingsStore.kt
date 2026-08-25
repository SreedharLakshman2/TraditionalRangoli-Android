package com.sreedhar.traditionalrangoli.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Calendar

class SettingsStore(context: Context) {
    private val prefs = context.applicationContext.getSharedPreferences("rangoli", Context.MODE_PRIVATE)

    var soundEnabled by mutableStateOf(prefs.getBoolean(Keys.SOUND, true))
        private set
    var hapticsEnabled by mutableStateOf(prefs.getBoolean(Keys.HAPTICS, true))
        private set
    var showGuides by mutableStateOf(prefs.getBoolean(Keys.GUIDES, true))
        private set
    var defaultGrid by mutableIntStateOf(prefs.getInt(Keys.GRID, 9))
        private set
    var xp by mutableIntStateOf(prefs.getInt(Keys.XP, 0))
        private set
    var patternsCompleted by mutableIntStateOf(prefs.getInt(Keys.COMPLETED, 0))
        private set
    var streak by mutableIntStateOf(prefs.getInt(Keys.STREAK, 0))
        private set
    var favoriteStyle by mutableStateOf(prefs.getString(Keys.STYLE, "Lotus") ?: "Lotus")
        private set
    var favoritePatternIds by mutableStateOf(prefs.getStringSet(Keys.FAVORITES, emptySet())?.toSet() ?: emptySet())
        private set
    var completedPatternIds by mutableStateOf(prefs.getStringSet(Keys.PATTERN_IDS, emptySet())?.toSet() ?: emptySet())
        private set

    val levelTitle: String
        get() = when {
            xp < 80 -> "Rangoli Beginner"
            xp < 220 -> "Courtyard Apprentice"
            xp < 500 -> "Kolam Artist"
            else -> "Master Creator"
        }

    val levelProgress: Float
        get() {
            val brackets = listOf(0 to 80, 80 to 220, 220 to 500, 500 to 900)
            val pair = brackets.firstOrNull { xp < it.second } ?: (500 to 900)
            return ((xp - pair.first).toFloat() / (pair.second - pair.first)).coerceIn(0f, 1f)
        }

    fun setSound(value: Boolean) {
        soundEnabled = value
        prefs.edit().putBoolean(Keys.SOUND, value).apply()
    }

    fun setHaptics(value: Boolean) {
        hapticsEnabled = value
        prefs.edit().putBoolean(Keys.HAPTICS, value).apply()
    }

    fun setGuides(value: Boolean) {
        showGuides = value
        prefs.edit().putBoolean(Keys.GUIDES, value).apply()
    }

    fun setGridSize(value: Int) {
        defaultGrid = value
        prefs.edit().putInt(Keys.GRID, value).apply()
    }

    fun toggleFavorite(patternId: String) {
        favoritePatternIds = if (favoritePatternIds.contains(patternId)) {
            favoritePatternIds - patternId
        } else {
            favoritePatternIds + patternId
        }
        prefs.edit().putStringSet(Keys.FAVORITES, favoritePatternIds).apply()
    }

    fun award(pattern: RangoliPattern?) {
        noteActivity()
        val reward = pattern?.xpReward ?: 40
        xp += reward
        patternsCompleted += 1
        prefs.edit()
            .putInt(Keys.XP, xp)
            .putInt(Keys.COMPLETED, patternsCompleted)
            .apply()
        pattern?.let {
            completedPatternIds = completedPatternIds + it.id
            favoriteStyle = it.theme.title
            prefs.edit()
                .putStringSet(Keys.PATTERN_IDS, completedPatternIds)
                .putString(Keys.STYLE, favoriteStyle)
                .apply()
        }
    }

    private fun noteActivity() {
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        val last = prefs.getLong(Keys.LAST_DAY, 0L)
        if (last == 0L) {
            streak = maxOf(streak, 1)
        } else {
            val gap = ((today - last) / 86_400_000L).toInt()
            streak = when {
                gap == 1 -> streak + 1
                gap > 1 -> 1
                else -> streak
            }
        }
        prefs.edit().putInt(Keys.STREAK, streak).putLong(Keys.LAST_DAY, today).apply()
    }

    private object Keys {
        const val SOUND = "rangoli.sound"
        const val HAPTICS = "rangoli.haptics"
        const val GUIDES = "rangoli.guides"
        const val GRID = "rangoli.grid"
        const val XP = "rangoli.xp"
        const val COMPLETED = "rangoli.completed"
        const val PATTERN_IDS = "rangoli.patternIds"
        const val STREAK = "rangoli.streak"
        const val LAST_DAY = "rangoli.lastDay"
        const val FAVORITES = "rangoli.favoritePatterns"
        const val STYLE = "rangoli.style"
    }
}
