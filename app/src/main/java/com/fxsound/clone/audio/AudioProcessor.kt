package com.fxsound.clone.audio

/**
 * Contract for applying FxSound-style processing controls to one or more Android audio sessions.
 */
interface AudioProcessor {
    fun setClarity(value: Float)
    fun setAmbience(value: Float)
    fun setSurround(value: Float)
    fun setDynamicBoost(value: Float)
    fun setBassBoost(value: Float)
    fun setEqBand(bandIndex: Int, gain: Float)
    fun setEnabled(enabled: Boolean)
}
