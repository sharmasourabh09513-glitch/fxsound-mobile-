package com.fxsound.clone.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import com.fxsound.clone.data.PresetRepository
import com.fxsound.clone.model.FxPreset
import com.fxsound.clone.model.PresetLibrary

class FxSoundViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = PresetRepository(application)

    // Power state
    var isPowerOn by mutableStateOf(false)
        private set

    // Current effect values
    var clarity by mutableStateOf(5f)
        private set
    var ambience by mutableStateOf(4f)
        private set
    var surround by mutableStateOf(3f)
        private set
    var dynamicBoost by mutableStateOf(5f)
        private set
    var bassBoost by mutableStateOf(4f)
        private set
    var fidelity by mutableStateOf(5f)
        private set
    var volumeBoost by mutableStateOf(3f)
        private set

    // EQ bands (10 bands)
    var eqBands = mutableStateListOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
        private set

    // Preset management
    var currentPresetName by mutableStateOf("General")
        private set
    var customPresets = mutableStateListOf<FxPreset>()
        private set

    // Selected tab
    var selectedTab by mutableStateOf(0)
        private set

    // Callback for audio processor updates
    var onAudioSettingsChanged: ((FxSoundViewModel) -> Unit)? = null

    init {
        loadSavedSettings()
        loadCustomPresets()
    }

    private fun loadSavedSettings() {
        clarity = repository.loadSavedClarity()
        ambience = repository.loadSavedAmbience()
        surround = repository.loadSavedSurround()
        dynamicBoost = repository.loadSavedDynamicBoost()
        bassBoost = repository.loadSavedBassBoost()
        fidelity = repository.loadSavedFidelity()
        volumeBoost = repository.loadSavedVolumeBoost()
        currentPresetName = repository.loadSavedPresetName()
        isPowerOn = repository.loadSavedPowerOn()

        val savedBands = repository.loadSavedEqBands()
        for (i in savedBands.indices) {
            if (i < eqBands.size) eqBands[i] = savedBands[i]
        }
    }

    private fun loadCustomPresets() {
        customPresets.clear()
        customPresets.addAll(repository.loadCustomPresets())
    }

    fun togglePower() {
        isPowerOn = !isPowerOn
        saveAndNotify()
    }

    fun setClarity(value: Float) { clarity = value; saveAndNotify() }
    fun setAmbience(value: Float) { ambience = value; saveAndNotify() }
    fun setSurround(value: Float) { surround = value; saveAndNotify() }
    fun setDynamicBoost(value: Float) { dynamicBoost = value; saveAndNotify() }
    fun setBassBoost(value: Float) { bassBoost = value; saveAndNotify() }
    fun setFidelity(value: Float) { fidelity = value; saveAndNotify() }
    fun setVolumeBoost(value: Float) { volumeBoost = value; saveAndNotify() }

    fun setEqBand(index: Int, value: Float) {
        if (index in 0 until eqBands.size) {
            eqBands[index] = value
            saveAndNotify()
        }
    }

    fun resetEq() {
        for (i in eqBands.indices) {
            eqBands[i] = 0f
        }
        saveAndNotify()
    }

    fun selectPreset(preset: FxPreset) {
        clarity = preset.clarity
        ambience = preset.ambience
        surround = preset.surround
        dynamicBoost = preset.dynamicBoost
        bassBoost = preset.bassBoost
        fidelity = preset.fidelity
        volumeBoost = preset.volumeBoost
        currentPresetName = preset.name

        for (i in preset.eqBands.indices) {
            if (i < eqBands.size) eqBands[i] = preset.eqBands[i]
        }
        saveAndNotify()
    }

    fun saveCustomPreset(name: String) {
        val preset = FxPreset(
            name = name,
            description = "Custom preset",
            icon = "\uD83D\uDCAB",
            clarity = clarity,
            ambience = ambience,
            surround = surround,
            dynamicBoost = dynamicBoost,
            bassBoost = bassBoost,
            fidelity = fidelity,
            volumeBoost = volumeBoost,
            eqBands = eqBands.toList(),
            isBuiltIn = false
        )
        repository.saveCustomPreset(preset)
        loadCustomPresets()
    }

    fun deleteCustomPreset(name: String) {
        repository.deleteCustomPreset(name)
        loadCustomPresets()
    }

    fun selectTab(index: Int) {
        selectedTab = index
    }

    private fun saveAndNotify() {
        repository.saveCurrentSettings(
            clarity, ambience, surround, dynamicBoost, bassBoost,
            fidelity, volumeBoost, eqBands.toList(), currentPresetName, isPowerOn
        )
        onAudioSettingsChanged?.invoke(this)
    }
}
