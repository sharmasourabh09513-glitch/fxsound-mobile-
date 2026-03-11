package com.fxsound.clone.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer
import android.media.audiofx.LoudnessEnhancer
import android.media.audiofx.PresetReverb
import android.media.audiofx.Virtualizer
import android.util.Log

/**
 * Full audio processing engine using Android's AudioEffect APIs.
 * Optimized for low latency using PRIORITY 0 (highest).
 */
class AndroidAudioProcessor(initialSessionId: Int) : AudioProcessor {
    private companion object {
        const val TAG = "FxAudioProcessor"
        const val PRIORITY = 0 // Highest priority for low latency
    }

    private data class SessionEffects(
        val equalizer: Equalizer,
        val bassBoost: BassBoost,
        val virtualizer: Virtualizer,
        val loudnessEnhancer: LoudnessEnhancer?,
        val reverb: PresetReverb?
    )

    private val sessions = mutableMapOf<Int, SessionEffects>()
    private var isEnabled = false

    // Cached values so new sessions inherit current settings
    private var currentClarity = 5f
    private var currentAmbience = 3f
    private var currentSurround = 3f
    private var currentDynamicBoost = 5f
    private var currentBassBoostVal = 4f
    private var currentFidelity = 5f
    private var currentVolumeBoost = 3f
    private var currentEqBands = MutableList(10) { 0f }

    init {
        if (initialSessionId > 0) {
            addSession(initialSessionId)
        }
    }

    fun addOrUpdateSession(sessionId: Int) {
        if (sessionId > 0) addSession(sessionId)
    }

    private fun addSession(sessionId: Int) {
        if (sessions.containsKey(sessionId)) return
        try {
            val eq = Equalizer(PRIORITY, sessionId).apply { enabled = isEnabled }
            val bb = BassBoost(PRIORITY, sessionId).apply { enabled = isEnabled }
            val virt = Virtualizer(PRIORITY, sessionId).apply { enabled = isEnabled }
            val loud = try { LoudnessEnhancer(sessionId).apply { enabled = isEnabled } } catch (e: Exception) { null }
            val reverb = try { PresetReverb(PRIORITY, sessionId).apply { enabled = isEnabled } } catch (e: Exception) { null }

            sessions[sessionId] = SessionEffects(eq, bb, virt, loud, reverb)

            // Apply cached settings to new session
            applyCurrentSettings(sessions[sessionId]!!)
            Log.d(TAG, "Session $sessionId added with all effects")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create effects for session $sessionId", e)
        }
    }

    private fun applyCurrentSettings(effects: SessionEffects) {
        applyEqToSession(effects)
        applyBassBoostToSession(effects, currentBassBoostVal)
        applySurroundToSession(effects, currentSurround)
        applyAmbienceToSession(effects, currentAmbience)
        applyVolumeBoostToSession(effects, currentVolumeBoost)
    }

    override fun setClarity(value: Float) {
        currentClarity = value
        // Clarity boosts higher frequency EQ bands
        sessions.values.forEach { effects ->
            val eq = effects.equalizer
            val bands = eq.numberOfBands.toInt()
            // Boost bands 7-9 (higher frequencies) for clarity
            val boost = (value * 80).toInt().toShort()
            if (bands > 7) eq.setBandLevel(7.toShort(), boost)
            if (bands > 8) eq.setBandLevel(8.toShort(), (value * 100).toInt().toShort())
            if (bands > 9) eq.setBandLevel(9.toShort(), (value * 60).toInt().toShort())
        }
    }

    override fun setAmbience(value: Float) {
        currentAmbience = value
        sessions.values.forEach { applyAmbienceToSession(it, value) }
    }

    private fun applyAmbienceToSession(effects: SessionEffects, value: Float) {
        effects.reverb?.let { reverb ->
            try {
                if (value > 0f) {
                    // Map 0-10 to reverb presets
                    val presetIndex = when {
                        value < 3f -> PresetReverb.PRESET_SMALLROOM
                        value < 6f -> PresetReverb.PRESET_MEDIUMROOM
                        value < 8f -> PresetReverb.PRESET_LARGEROOM
                        else -> PresetReverb.PRESET_LARGEHALL
                    }
                    reverb.preset = presetIndex
                    reverb.enabled = true
                } else {
                    reverb.enabled = false
                }
            } catch (e: Exception) {
                Log.w(TAG, "Reverb not supported", e)
            }
        }
    }

    override fun setSurround(value: Float) {
        currentSurround = value
        sessions.values.forEach { applySurroundToSession(it, value) }
    }

    private fun applySurroundToSession(effects: SessionEffects, value: Float) {
        val virt = effects.virtualizer
        try {
            if (virt.strengthSupported) {
                val strength = (value.coerceIn(0f, 10f) / 10f * 1000).toInt().toShort()
                virt.setStrength(strength)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Virtualizer error", e)
        }
    }

    override fun setDynamicBoost(value: Float) {
        currentDynamicBoost = value
        // Dynamic boost applies mild compression by boosting quiet ranges via EQ
        sessions.values.forEach { effects ->
            val eq = effects.equalizer
            val bands = eq.numberOfBands.toInt()
            // Subtle mid-range boost for perceived loudness
            val boost = (value * 50).toInt().toShort()
            if (bands > 3) eq.setBandLevel(3.toShort(), boost)
            if (bands > 4) eq.setBandLevel(4.toShort(), (value * 60).toInt().toShort())
            if (bands > 5) eq.setBandLevel(5.toShort(), boost)
        }
    }

    override fun setBassBoost(value: Float) {
        currentBassBoostVal = value
        sessions.values.forEach { applyBassBoostToSession(it, value) }
    }

    private fun applyBassBoostToSession(effects: SessionEffects, value: Float) {
        val bb = effects.bassBoost
        try {
            if (bb.strengthSupported) {
                val strength = (value.coerceIn(0f, 10f) / 10f * 1000).toInt().toShort()
                bb.setStrength(strength)
            }
        } catch (e: Exception) {
            Log.w(TAG, "BassBoost error", e)
        }
    }

    override fun setFidelity(value: Float) {
        currentFidelity = value
        // Fidelity enhances presence by boosting upper-mid EQ
        sessions.values.forEach { effects ->
            val eq = effects.equalizer
            val bands = eq.numberOfBands.toInt()
            val boost = (value * 70).toInt().toShort()
            if (bands > 5) eq.setBandLevel(5.toShort(), boost)
            if (bands > 6) eq.setBandLevel(6.toShort(), (value * 90).toInt().toShort())
        }
    }

    override fun setVolumeBoost(value: Float) {
        currentVolumeBoost = value
        sessions.values.forEach { applyVolumeBoostToSession(it, value) }
    }

    private fun applyVolumeBoostToSession(effects: SessionEffects, value: Float) {
        effects.loudnessEnhancer?.let { loud ->
            try {
                // Map 0-10 to 0-1000 mB gain
                val gainMb = (value.coerceIn(0f, 10f) / 10f * 1000).toInt()
                loud.setTargetGain(gainMb)
            } catch (e: Exception) {
                Log.w(TAG, "LoudnessEnhancer error", e)
            }
        }
    }

    override fun setEqBand(bandIndex: Int, gain: Float) {
        if (bandIndex in 0 until 10) {
            currentEqBands[bandIndex] = gain
        }
        sessions.values.forEach { effects ->
            val eq = effects.equalizer
            if (bandIndex in 0 until eq.numberOfBands.toInt()) {
                eq.setBandLevel(bandIndex.toShort(), (gain * 100).toInt().toShort())
            }
        }
    }

    override fun setEqBands(gains: List<Float>) {
        for (i in gains.indices) {
            if (i < 10) currentEqBands[i] = gains[i]
        }
        sessions.values.forEach { applyEqToSession(it) }
    }

    private fun applyEqToSession(effects: SessionEffects) {
        val eq = effects.equalizer
        val bands = eq.numberOfBands.toInt()
        for (i in 0 until minOf(bands, currentEqBands.size)) {
            eq.setBandLevel(i.toShort(), (currentEqBands[i] * 100).toInt().toShort())
        }
    }

    override fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
        sessions.values.forEach { effects ->
            effects.equalizer.enabled = enabled
            effects.bassBoost.enabled = enabled
            effects.virtualizer.enabled = enabled
            effects.loudnessEnhancer?.enabled = enabled
            effects.reverb?.enabled = enabled
        }
    }

    override fun release() {
        sessions.values.forEach { effects ->
            try {
                effects.equalizer.release()
                effects.bassBoost.release()
                effects.virtualizer.release()
                effects.loudnessEnhancer?.release()
                effects.reverb?.release()
            } catch (e: Exception) {
                Log.w(TAG, "Error releasing effects", e)
            }
        }
        sessions.clear()
    }
}
