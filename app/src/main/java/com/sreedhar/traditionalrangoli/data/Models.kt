package com.sreedhar.traditionalrangoli.data

enum class Difficulty(val title: String) {
    Beginner("Beginner"), Intermediate("Intermediate"), Advanced("Advanced")
}

enum class PatternFamily(val title: String) {
    PulliKolam("Pulli Kolam"),
    SikkuKolam("Sikku Kolam"),
    FreehandRangoli("Freehand Rangoli"),
    Geometric("Geometric"),
    Floral("Floral")
}

enum class MotifTheme(val title: String, val symbol: String) {
    Lotus("Lotus", "🪷"),
    Peacock("Peacock", "🦚"),
    Diya("Diya", "🪔"),
    Flowers("Flowers", "🌸"),
    Mandala("Mandala", "✦"),
    Traditional("Traditional Motifs", "卍")
}

enum class Festival(val title: String) {
    Pongal("Pongal"),
    Diwali("Diwali"),
    Navratri("Navratri"),
    Onam("Onam"),
    Ugadi("Ugadi"),
    NewYear("New Year")
}

enum class BrowseCollection(val title: String, val symbol: String) {
    Festival("Festival", "🪔"),
    DotKolam("Dot Kolam", "⚬"),
    Floral("Floral", "❀"),
    Peacock("Peacock", "🦚"),
    Mandala("Mandala", "✦"),
    Geometric("Geometric", "◇")
}

enum class MotifKind {
    LotusDot, SimpleFlower, Peacock, Diya, Pulli, Spiral, EightPetal,
    GeometricStar, FestivalFlower, Mandala, Butterfly, PongalPot,
    SikkuKnot, OnamPookalam, SunBurst, MangoLeaf
}

data class RangoliPattern(
    val id: String,
    val title: String,
    val family: PatternFamily,
    val theme: MotifTheme,
    val festivals: List<Festival>,
    val difficulty: Difficulty,
    val gridSize: Int,
    val estimatedMinutes: Int,
    val description: String,
    val tags: List<String>,
    val motif: MotifKind,
    val xpReward: Int,
    val stepCount: Int
)
