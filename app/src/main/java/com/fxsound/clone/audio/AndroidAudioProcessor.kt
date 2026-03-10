package com.fxsound.clone.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer

/**
 * Minimal implementation backed by platform audio effects.
 *
 * Note: audio effect band indices / ranges vary by device. This implementation intentionally
 * stays simple and should be treated as a prototype.
 */
class AndroidAudioProcessor(initialSessionId: Int) : AudioProcessor {
    private val processingUnits = mutableMapOf<Int, Pair<Equalizer, BassBoost>>()

    init {
        // Only create effects for a valid session id.
        if (initialSessionId > 0) {
            addSession(initialSessionId)
        }
    }

    fun addOrUpdateSession(sessionId: Int) {
        if (sessionId > 0) addSession(sessionId)
    }

    private fun addSession(sessionId: Int) {
        if (processingUnits.containsKey(sessionId)) return
        val eq = Equalizer(0, sessionId)
        val bb = BassBoost(0, sessionId)
        eq.enabled = true
        bb.enabled = true
        processingUnits[sessionId] = Pair(eq, bb)
    }

    override fun setClarity(value: Float) {
        processingUnits.values.forEach { (eq, _) ->
            // Guard against devices with fewer bands.
            val bands = eq.numberOfBands.toInt()
            if (bands > 7) eq.setBandLevel(7.toShort(), (value * 100).toInt().toShort())
            if (bands > 8) eq.setBandLevel(8.toShort(), (value * 150).toInt().toShort())
        }
    }

    override fun setAmbience(value: Float) {
        // Placeholder
    }

    override fun setSurround(value: Float) {
        // Placeholder
    }

    override fun setDynamicBoost(value: Float) {
        // Placeholder
    }

    override fun setBassBoost(value: Float) {
        processingUnits.values.forEach { (_, bb) ->
            if (bb.strengthSupported) {
                // BassBoost strength range is 0..1000; we map the 0..10 UI to 0..1000.
                val strength = (value.coerceIn(0f, 10f) / 10f * 1000).toInt().toShort()
                bb.setStrength(strength)
            }
        }
    }

    override fun setEqBand(bandIndex: Int, gain: Float) {
        processingUnits.values.forEach { (eq, _) ->
            if (bandIndex in 0 until eq.numberOfBands.toInt()) {
                eq.setBandLevel(bandIndex.toShort(), gain.toInt().toShort())
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        processingUnits.values.forEach { (eq, bb) ->
            eq.enabled = enabled
            bb.enabled = enabled
        }
    }
}
