package com.sreedhar.traditionalrangoli.data

import java.util.Calendar

object PatternCatalog {
    val all: List<RangoliPattern> = listOf(
        p("lotus-dot", "Lotus Dot Rangoli", PatternFamily.Floral, MotifTheme.Lotus, listOf(Festival.Diwali, Festival.Navratri), Difficulty.Beginner, 9, 10, "Create this traditional symmetrical lotus-inspired rangoli using a guided dot pattern.", listOf("lotus", "dots", "beginner"), MotifKind.LotusDot, 50, 6),
        p("simple-flower", "Simple Flower Kolam", PatternFamily.Floral, MotifTheme.Flowers, listOf(Festival.Ugadi), Difficulty.Beginner, 7, 8, "A gentle four-petal flower kolam — perfect first powder on the courtyard floor.", listOf("flower", "kolam"), MotifKind.SimpleFlower, 40, 5),
        p("peacock", "Peacock Rangoli", PatternFamily.FreehandRangoli, MotifTheme.Peacock, listOf(Festival.Diwali, Festival.Navratri), Difficulty.Intermediate, 11, 16, "Fan feathers, a quiet crest, and the proud posture of a courtyard peacock.", listOf("peacock", "festival"), MotifKind.Peacock, 80, 8),
        p("diya", "Diya Rangoli", PatternFamily.FreehandRangoli, MotifTheme.Diya, listOf(Festival.Diwali), Difficulty.Beginner, 9, 9, "A lamp at the heart, petals for light — the classic Deepavali threshold.", listOf("diya", "diwali"), MotifKind.Diya, 45, 5),
        p("pulli", "Traditional Pulli Kolam", PatternFamily.PulliKolam, MotifTheme.Traditional, listOf(Festival.Pongal), Difficulty.Intermediate, 9, 14, "Loop the rice powder around a lattice of pulli without breaking the line.", listOf("pulli", "tamil"), MotifKind.Pulli, 75, 7),
        p("spiral", "Spiral Kolam", PatternFamily.SikkuKolam, MotifTheme.Traditional, listOf(Festival.Pongal), Difficulty.Intermediate, 11, 12, "A continuous sikku spiral that grows from a single bindu into a full kolam.", listOf("spiral", "sikku"), MotifKind.Spiral, 70, 6),
        p("eight-petal", "Eight Petal Lotus", PatternFamily.Floral, MotifTheme.Lotus, listOf(Festival.Navratri, Festival.Diwali), Difficulty.Intermediate, 11, 15, "Ashtadala padma — eight petals in layered terracotta, gold, and leaf green.", listOf("lotus", "ashtadala"), MotifKind.EightPetal, 80, 8),
        p("geo-star", "Geometric Star", PatternFamily.Geometric, MotifTheme.Mandala, listOf(Festival.NewYear), Difficulty.Beginner, 9, 10, "An eight-point star nested in an octagon, drawn with temple-geometry calm.", listOf("star", "geometry"), MotifKind.GeometricStar, 55, 5),
        p("festival-flower", "Festival Flower Rangoli", PatternFamily.Floral, MotifTheme.Flowers, listOf(Festival.Diwali, Festival.Navratri, Festival.Onam), Difficulty.Intermediate, 11, 14, "Concentric festival petals in red, gold, and marigold for any threshold.", listOf("festival", "flower"), MotifKind.FestivalFlower, 70, 7),
        p("mandala", "Mandala Rangoli", PatternFamily.Geometric, MotifTheme.Mandala, listOf(Festival.Navratri, Festival.NewYear), Difficulty.Advanced, 15, 22, "Layered rings of petals, stars, and bindu — a meditative mandala for skilled hands.", listOf("mandala", "advanced"), MotifKind.Mandala, 120, 8),
        p("butterfly", "Butterfly Rangoli", PatternFamily.FreehandRangoli, MotifTheme.Flowers, listOf(Festival.Onam, Festival.Ugadi), Difficulty.Intermediate, 11, 13, "Twin wings, a slender body, and garden color for a spring courtyard.", listOf("butterfly", "freehand"), MotifKind.Butterfly, 65, 6),
        p("pongal-pot", "Pongal Pot Rangoli", PatternFamily.FreehandRangoli, MotifTheme.Traditional, listOf(Festival.Pongal), Difficulty.Intermediate, 11, 15, "The overflowing pongal pot with mango leaves — harvest joy at the doorway.", listOf("pongal", "pot"), MotifKind.PongalPot, 75, 7),
        p("sikku-knot", "Sikku Knot Kolam", PatternFamily.SikkuKolam, MotifTheme.Traditional, listOf(Festival.Pongal), Difficulty.Advanced, 11, 18, "Interlocking sikku knots that never lift from the floor — a Tamil classic.", listOf("sikku", "knot"), MotifKind.SikkuKnot, 100, 8),
        p("onam-pookalam", "Onam Pookalam", PatternFamily.Floral, MotifTheme.Flowers, listOf(Festival.Onam), Difficulty.Intermediate, 13, 16, "Rings of flower color inspired by Kerala’s Onam pookalam carpets.", listOf("onam", "pookalam"), MotifKind.OnamPookalam, 85, 7),
        p("sun-burst", "New Year Sunburst", PatternFamily.Geometric, MotifTheme.Mandala, listOf(Festival.NewYear, Festival.Ugadi), Difficulty.Beginner, 9, 9, "A rising sun of gold rays — simple geometry for a new year’s morning.", listOf("sun", "new year"), MotifKind.SunBurst, 50, 5),
        p("mango-leaf", "Mango Leaf Toran", PatternFamily.Floral, MotifTheme.Traditional, listOf(Festival.Ugadi, Festival.Diwali), Difficulty.Beginner, 9, 8, "A circular toran of mango leaves around a gold bindu.", listOf("mango", "toran"), MotifKind.MangoLeaf, 45, 5)
    )

    val daily: RangoliPattern
        get() {
            val day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
            return all[day % all.size]
        }

    val popular: List<RangoliPattern> get() = all.take(6)

    fun pattern(id: String): RangoliPattern? = all.find { it.id == id }

    fun matching(collection: BrowseCollection): List<RangoliPattern> = all.filter { pattern ->
        when (collection) {
            BrowseCollection.Festival -> pattern.festivals.isNotEmpty()
            BrowseCollection.DotKolam -> pattern.family == PatternFamily.PulliKolam || pattern.family == PatternFamily.SikkuKolam
            BrowseCollection.Floral -> pattern.family == PatternFamily.Floral || pattern.theme == MotifTheme.Flowers || pattern.theme == MotifTheme.Lotus
            BrowseCollection.Peacock -> pattern.theme == MotifTheme.Peacock
            BrowseCollection.Mandala -> pattern.theme == MotifTheme.Mandala
            BrowseCollection.Geometric -> pattern.family == PatternFamily.Geometric
        }
    }

    fun search(query: String): List<RangoliPattern> {
        val trimmed = query.trim().lowercase()
        if (trimmed.isEmpty()) return all
        return all.filter {
            it.title.lowercase().contains(trimmed) ||
                it.tags.any { tag -> tag.contains(trimmed) } ||
                it.family.title.lowercase().contains(trimmed) ||
                it.theme.title.lowercase().contains(trimmed) ||
                it.festivals.any { f -> f.title.lowercase().contains(trimmed) }
        }
    }

    private fun p(
        id: String, title: String, family: PatternFamily, theme: MotifTheme,
        festivals: List<Festival>, difficulty: Difficulty, grid: Int, minutes: Int,
        description: String, tags: List<String>, motif: MotifKind, xp: Int, steps: Int
    ) = RangoliPattern(id, title, family, theme, festivals, difficulty, grid, minutes, description, tags, motif, xp, steps)
}
