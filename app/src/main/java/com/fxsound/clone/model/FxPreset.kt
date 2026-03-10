package com.fxsound.clone.model

data class FxPreset(
    val name: String,
    val clarity: Float,
    val ambience: Float,
    val surround: Float,
    val dynamicBoost: Float,
    val bassBoost: Float,
    val eqGains: List<Float>
)

object PresetLibrary {
    val presets = listOf(
        FxPreset(
            name = "Music",
            clarity = 7f,
            ambience = 3f,
            surround = 2f,
            dynamicBoost = 4f,
            bassBoost = 6f,
            eqGains = listOf(2f, 3f, 1f, 0f, 0f, 1f, 2f, 4f, 6f)
        ),
        FxPreset(
            name = "Movie",
            clarity = 5f,
            ambience = 6f,
            surround = 8f,
            dynamicBoost = 5f,
            bassBoost = 4f,
            eqGains = listOf(4f, 2f, 0f, 0f, 0f, 1f, 2f, 3f, 4f)
        ),
        FxPreset(
            name = "Gaming",
            clarity = 8f,
            ambience = 2f,
            surround = 6f,
            dynamicBoost = 7f,
            bassBoost = 5f,
            eqGains = listOf(0f, 1f, 0f, 2f, 3f, 4f, 5f, 6f, 7f)
        ),
        FxPreset(
            name = "Voice",
            clarity = 9f,
            ambience = 1f,
            surround = 0f,
            dynamicBoost = 6f,
            bassBoost = 0f,
            eqGains = listOf(-10f, -5f, 0f, 5f, 8f, 10f, 8f, 5f, 0f)
        )
    )
}
