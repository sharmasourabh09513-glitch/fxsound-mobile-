package com.fxsound.clone.audio

/**
 * Contract for FxSound audio processing with full control over all effects and EQ.
 */
interface AudioProcessor {
    fun setClarity(value: Float)
    fun setAmbience(value: Float)
    fun setSurround(value: Float)
    fun setDynamicBoost(value: Float)
    fun setBassBoost(value: Float)
    fun setFidelity(value: Float)
    fun setVolumeBoost(value: Float)
    fun setEqBand(bandIndex: Int, gain: Float)
    fun setEqBands(gains: List<Float>)
    fun setEnabled(enabled: Boolean)
    fun release()
}
