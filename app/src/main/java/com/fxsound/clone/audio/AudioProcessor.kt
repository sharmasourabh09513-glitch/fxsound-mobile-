package com.fxsound.clone.audio

interface AudioProcessor {
    fun setClarity(value: Float)
    fun setAmbience(value: Float)
    fun setSurround(value: Float)
    fun setDynamicBoost(value: Float)
    fun setBassBoost(value: Float)
    fun setEqBand(bandIndex: Int, gain: Float)
    fun setEnabled(enabled: Boolean)
}

package com.fxsound.clone.audio

import android.media.audiofx.BassBoost
import android.media.audiofx.Equalizer

class AndroidAudioProcessor(initialSessionId: Int) : AudioProcessor {
    private val processingUnits = mutableMapOf<Int, Pair<Equalizer, BassBoost>>()

    init {
        addSession(initialSessionId)
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
            eq.setBandLevel(7.toShort(), (value * 100).toInt().toShort()) // 4kHz
            eq.setBandLevel(8.toShort(), (value * 150).toInt().toShort()) // 8kHz
        }
    }

    override fun setAmbience(value: Float) { /* Placeholder */ }

    override fun setSurround(value: Float) { /* Placeholder */ }

    override fun setDynamicBoost(value: Float) { /* Placeholder */ }

    override fun setBassBoost(value: Float) {
        processingUnits.values.forEach { (_, bb) ->
            if (bb.strengthSupported) {
                bb.setStrength((value * 100).toInt().toShort())
            }
        }
    }

    override fun setEqBand(bandIndex: Int, gain: Float) {
        processingUnits.values.forEach { (eq, _) ->
            if (bandIndex < eq.numberOfBands) {
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
