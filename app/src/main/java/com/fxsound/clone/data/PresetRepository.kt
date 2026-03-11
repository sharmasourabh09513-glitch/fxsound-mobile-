package com.fxsound.clone.data

import android.content.Context
import android.content.SharedPreferences
import com.fxsound.clone.model.FxPreset
import com.fxsound.clone.model.PresetLibrary

class PresetRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("fxsound_prefs", Context.MODE_PRIVATE)

    fun saveCurrentSettings(
        clarity: Float, ambience: Float, surround: Float,
        dynamicBoost: Float, bassBoost: Float, fidelity: Float,
        volumeBoost: Float, eqBands: List<Float>, presetName: String,
        isPowerOn: Boolean
    ) {
        prefs.edit().apply {
            putFloat("clarity", clarity)
            putFloat("ambience", ambience)
            putFloat("surround", surround)
            putFloat("dynamicBoost", dynamicBoost)
            putFloat("bassBoost", bassBoost)
            putFloat("fidelity", fidelity)
            putFloat("volumeBoost", volumeBoost)
            putString("presetName", presetName)
            putBoolean("isPowerOn", isPowerOn)
            for (i in eqBands.indices) {
                putFloat("eq_band_$i", eqBands[i])
            }
            apply()
        }
    }

    fun loadSavedClarity(): Float = prefs.getFloat("clarity", 5f)
    fun loadSavedAmbience(): Float = prefs.getFloat("ambience", 4f)
    fun loadSavedSurround(): Float = prefs.getFloat("surround", 3f)
    fun loadSavedDynamicBoost(): Float = prefs.getFloat("dynamicBoost", 5f)
    fun loadSavedBassBoost(): Float = prefs.getFloat("bassBoost", 4f)
    fun loadSavedFidelity(): Float = prefs.getFloat("fidelity", 5f)
    fun loadSavedVolumeBoost(): Float = prefs.getFloat("volumeBoost", 3f)
    fun loadSavedPresetName(): String = prefs.getString("presetName", "General") ?: "General"
    fun loadSavedPowerOn(): Boolean = prefs.getBoolean("isPowerOn", false)

    fun loadSavedEqBands(): List<Float> {
        return (0 until 10).map { i ->
            prefs.getFloat("eq_band_$i", 0f)
        }
    }

    fun saveCustomPreset(preset: FxPreset) {
        val customPresets = loadCustomPresets().toMutableList()
        customPresets.removeAll { it.name == preset.name }
        customPresets.add(preset)

        val names = customPresets.map { it.name }.toSet()
        prefs.edit().putStringSet("custom_preset_names", names).apply()
        for (p in customPresets) {
            savePresetData(p)
        }
    }

    fun deleteCustomPreset(name: String) {
        val names = prefs.getStringSet("custom_preset_names", emptySet())?.toMutableSet() ?: mutableSetOf()
        names.remove(name)
        prefs.edit().putStringSet("custom_preset_names", names).apply()
    }

    fun loadCustomPresets(): List<FxPreset> {
        val names = prefs.getStringSet("custom_preset_names", emptySet()) ?: emptySet()
        return names.mapNotNull { loadPresetData(it) }
    }

    private fun savePresetData(preset: FxPreset) {
        val prefix = "preset_${preset.name}_"
        prefs.edit().apply {
            putFloat("${prefix}clarity", preset.clarity)
            putFloat("${prefix}ambience", preset.ambience)
            putFloat("${prefix}surround", preset.surround)
            putFloat("${prefix}dynamicBoost", preset.dynamicBoost)
            putFloat("${prefix}bassBoost", preset.bassBoost)
            putFloat("${prefix}fidelity", preset.fidelity)
            putFloat("${prefix}volumeBoost", preset.volumeBoost)
            for (i in preset.eqBands.indices) {
                putFloat("${prefix}eq_$i", preset.eqBands[i])
            }
            apply()
        }
    }

    private fun loadPresetData(name: String): FxPreset? {
        val prefix = "preset_${name}_"
        if (!prefs.contains("${prefix}clarity")) return null
        return FxPreset(
            name = name,
            description = "Custom preset",
            icon = "\uD83D\uDCAB",
            clarity = prefs.getFloat("${prefix}clarity", 5f),
            ambience = prefs.getFloat("${prefix}ambience", 3f),
            surround = prefs.getFloat("${prefix}surround", 2f),
            dynamicBoost = prefs.getFloat("${prefix}dynamicBoost", 5f),
            bassBoost = prefs.getFloat("${prefix}bassBoost", 4f),
            fidelity = prefs.getFloat("${prefix}fidelity", 5f),
            volumeBoost = prefs.getFloat("${prefix}volumeBoost", 3f),
            eqBands = (0 until 10).map { prefs.getFloat("${prefix}eq_$it", 0f) },
            isBuiltIn = false
        )
    }
}
