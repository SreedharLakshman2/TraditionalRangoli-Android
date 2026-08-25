package com.sreedhar.traditionalrangoli.data

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.UUID

class ArtworkStore(context: Context) {
    private val folder = File(context.applicationContext.filesDir, "RangoliArtworks").apply { mkdirs() }
    var artworks by mutableStateOf(loadAll())
        private set

    fun save(artwork: UserArtwork, thumbnail: Bitmap? = null): UserArtwork {
        val thumbFile = thumbnail?.let {
            val file = File(folder, "${artwork.id}.png")
            file.outputStream().use { out -> it.compress(Bitmap.CompressFormat.PNG, 100, out) }
            file.absolutePath
        } ?: artwork.thumbnailPath
        val stored = artwork.copy(thumbnailPath = thumbFile, updatedAt = System.currentTimeMillis())
        File(folder, "${stored.id}.json").writeText(toJson(stored).toString())
        artworks = listOf(stored) + artworks.filterNot { it.id == stored.id }
        return stored
    }

    fun delete(id: String) {
        File(folder, "$id.json").delete()
        File(folder, "$id.png").delete()
        artworks = artworks.filterNot { it.id == id }
    }

    private fun loadAll(): List<UserArtwork> {
        return folder.listFiles { _, name -> name.endsWith(".json") }
            ?.mapNotNull { file -> runCatching { fromJson(JSONObject(file.readText())) }.getOrNull() }
            ?.sortedByDescending { it.updatedAt }
            ?: emptyList()
    }

    private fun toJson(art: UserArtwork) = JSONObject().apply {
        put("id", art.id)
        put("title", art.title)
        put("createdAt", art.createdAt)
        put("updatedAt", art.updatedAt)
        put("patternId", art.patternId)
        put("studio", art.studio.name)
        put("gridSize", art.gridSize)
        put("thumbnailPath", art.thumbnailPath)
        put("isFavorite", art.isFavorite)
        put("colors", JSONArray(art.colors))
        put("strokes", JSONArray(art.strokes.map { stroke ->
            JSONObject().apply {
                put("id", stroke.id)
                put("colorHex", stroke.colorHex)
                put("width", stroke.width.toDouble())
                put("tool", stroke.tool.name)
                put("symmetry", stroke.symmetry.name)
                put("points", JSONArray(stroke.points.map { JSONObject().put("x", it.x.toDouble()).put("y", it.y.toDouble()) }))
            }
        }))
        put("fills", JSONArray(art.fills.map { fill ->
            JSONObject().apply {
                put("id", fill.id)
                put("colorHex", fill.colorHex)
                put("radius", fill.radius.toDouble())
                put("kind", fill.kind.name)
                put("x", fill.center.x.toDouble())
                put("y", fill.center.y.toDouble())
            }
        }))
    }

    private fun fromJson(json: JSONObject): UserArtwork {
        val strokes = json.optJSONArray("strokes") ?: JSONArray()
        val fills = json.optJSONArray("fills") ?: JSONArray()
        return UserArtwork(
            id = json.optString("id", UUID.randomUUID().toString()),
            title = json.optString("title"),
            createdAt = json.optLong("createdAt"),
            updatedAt = json.optLong("updatedAt"),
            patternId = json.optString("patternId").ifBlank { null },
            studio = runCatching { StudioKind.valueOf(json.optString("studio", "Dots")) }.getOrDefault(StudioKind.Dots),
            gridSize = json.optInt("gridSize", 9),
            strokes = (0 until strokes.length()).map { i ->
                val s = strokes.getJSONObject(i)
                val pts = s.optJSONArray("points") ?: JSONArray()
                DrawStroke(
                    id = s.optString("id", UUID.randomUUID().toString()),
                    points = (0 until pts.length()).map { p ->
                        val pt = pts.getJSONObject(p)
                        Point2D(pt.optDouble("x").toFloat(), pt.optDouble("y").toFloat())
                    },
                    colorHex = s.optLong("colorHex"),
                    width = s.optDouble("width", 4.2).toFloat(),
                    tool = runCatching { DrawTool.valueOf(s.optString("tool", "Brush")) }.getOrDefault(DrawTool.Brush),
                    symmetry = runCatching { SymmetryMode.valueOf(s.optString("symmetry", "None")) }.getOrDefault(SymmetryMode.None)
                )
            },
            fills = (0 until fills.length()).map { i ->
                val f = fills.getJSONObject(i)
                FillBlob(
                    id = f.optString("id", UUID.randomUUID().toString()),
                    center = Point2D(f.optDouble("x").toFloat(), f.optDouble("y").toFloat()),
                    colorHex = f.optLong("colorHex"),
                    radius = f.optDouble("radius", 0.05).toFloat(),
                    kind = runCatching { DrawTool.valueOf(f.optString("kind", "Fill")) }.getOrDefault(DrawTool.Fill)
                )
            },
            thumbnailPath = json.optString("thumbnailPath").ifBlank { null },
            isFavorite = json.optBoolean("isFavorite"),
            colors = json.optJSONArray("colors")?.let { arr -> (0 until arr.length()).map { arr.optLong(it) } } ?: emptyList()
        )
    }
}
