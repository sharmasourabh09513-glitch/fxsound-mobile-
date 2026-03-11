package com.fxsound.clone.model

data class FxPreset(
    val name: String,
    val description: String,
    val icon: String,
    val clarity: Float,
    val ambience: Float,
    val surround: Float,
    val dynamicBoost: Float,
    val bassBoost: Float,
    val fidelity: Float,
    val volumeBoost: Float,
    val eqBands: List<Float>, // 10 bands: 32Hz,64Hz,125Hz,250Hz,500Hz,1kHz,2kHz,4kHz,8kHz,16kHz
    val isBuiltIn: Boolean = true
)

object PresetLibrary {
    val defaultEq = listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)

    val presets = listOf(
        FxPreset(
            name = "General",
            description = "Balanced enhancement for all content",
            icon = "\uD83C\uDFB6",
            clarity = 5f, ambience = 4f, surround = 3f,
            dynamicBoost = 5f, bassBoost = 4f, fidelity = 5f, volumeBoost = 3f,
            eqBands = listOf(2f, 1f, 0f, 0f, 1f, 2f, 2f, 1f, 2f, 3f)
        ),
        FxPreset(
            name = "Music",
            description = "Rich, wide sound for music listening",
            icon = "\uD83C\uDFB5",
            clarity = 7f, ambience = 3f, surround = 2f,
            dynamicBoost = 4f, bassBoost = 6f, fidelity = 7f, volumeBoost = 2f,
            eqBands = listOf(3f, 4f, 2f, 0f, 0f, 1f, 3f, 5f, 6f, 5f)
        ),
        FxPreset(
            name = "Voice",
            description = "Clear speech and dialogue emphasis",
            icon = "\uD83C\uDF99",
            clarity = 9f, ambience = 1f, surround = 0f,
            dynamicBoost = 6f, bassBoost = 0f, fidelity = 8f, volumeBoost = 4f,
            eqBands = listOf(-4f, -2f, 0f, 3f, 6f, 8f, 6f, 3f, 0f, -2f)
        ),
        FxPreset(
            name = "Movies",
            description = "Cinematic audio with deep bass and surround",
            icon = "\uD83C\uDFAC",
            clarity = 5f, ambience = 6f, surround = 8f,
            dynamicBoost = 5f, bassBoost = 4f, fidelity = 6f, volumeBoost = 3f,
            eqBands = listOf(5f, 3f, 1f, 0f, 0f, 1f, 2f, 3f, 4f, 3f)
        ),
        FxPreset(
            name = "Gaming",
            description = "Fast, immersive audio for gameplay",
            icon = "\uD83C\uDFAE",
            clarity = 8f, ambience = 2f, surround = 7f,
            dynamicBoost = 7f, bassBoost = 5f, fidelity = 8f, volumeBoost = 5f,
            eqBands = listOf(1f, 2f, 1f, 2f, 3f, 5f, 6f, 7f, 8f, 7f)
        ),
        FxPreset(
            name = "Streaming",
            description = "Enhanced streaming video audio",
            icon = "\uD83D\uDCFA",
            clarity = 6f, ambience = 4f, surround = 5f,
            dynamicBoost = 6f, bassBoost = 3f, fidelity = 6f, volumeBoost = 4f,
            eqBands = listOf(2f, 1f, 0f, 1f, 3f, 4f, 3f, 2f, 3f, 2f)
        ),
        FxPreset(
            name = "Bass Boost",
            description = "Heavy, deep low-end enhancement",
            icon = "\uD83D\uDD0A",
            clarity = 3f, ambience = 2f, surround = 3f,
            dynamicBoost = 5f, bassBoost = 10f, fidelity = 4f, volumeBoost = 4f,
            eqBands = listOf(8f, 7f, 5f, 3f, 1f, 0f, 0f, 0f, -1f, -2f)
        ),
        FxPreset(
            name = "Pop",
            description = "Bright and punchy for pop music",
            icon = "\u2B50",
            clarity = 7f, ambience = 3f, surround = 2f,
            dynamicBoost = 5f, bassBoost = 5f, fidelity = 7f, volumeBoost = 3f,
            eqBands = listOf(1f, 3f, 4f, 2f, 0f, 1f, 3f, 5f, 4f, 3f)
        ),
        FxPreset(
            name = "Rock",
            description = "Powerful mids and crisp highs",
            icon = "\uD83E\uDD18",
            clarity = 8f, ambience = 2f, surround = 3f,
            dynamicBoost = 6f, bassBoost = 6f, fidelity = 7f, volumeBoost = 4f,
            eqBands = listOf(4f, 3f, 1f, 0f, 2f, 4f, 5f, 4f, 3f, 4f)
        ),
        FxPreset(
            name = "Hip-Hop",
            description = "Deep bass with crisp vocals",
            icon = "\uD83C\uDFA4",
            clarity = 6f, ambience = 3f, surround = 4f,
            dynamicBoost = 5f, bassBoost = 8f, fidelity = 6f, volumeBoost = 5f,
            eqBands = listOf(6f, 5f, 4f, 2f, 0f, 1f, 2f, 4f, 3f, 2f)
        ),
        FxPreset(
            name = "Classical",
            description = "Warm, spacious orchestral sound",
            icon = "\uD83C\uDFBB",
            clarity = 6f, ambience = 7f, surround = 5f,
            dynamicBoost = 3f, bassBoost = 2f, fidelity = 8f, volumeBoost = 1f,
            eqBands = listOf(0f, 1f, 2f, 2f, 1f, 0f, 1f, 2f, 3f, 2f)
        ),
        FxPreset(
            name = "Flat",
            description = "No processing, pure audio output",
            icon = "\u2796",
            clarity = 0f, ambience = 0f, surround = 0f,
            dynamicBoost = 0f, bassBoost = 0f, fidelity = 0f, volumeBoost = 0f,
            eqBands = listOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        )
    )
}
