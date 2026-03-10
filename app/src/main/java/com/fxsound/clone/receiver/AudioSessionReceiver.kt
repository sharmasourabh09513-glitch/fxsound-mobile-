package com.fxsound.clone.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.audiofx.AudioEffect
import com.fxsound.clone.service.FxAudioService

class AudioSessionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        val sessionId = intent.getIntExtra(AudioEffect.EXTRA_AUDIO_SESSION, -1)
        
        if (sessionId != -1) {
            val serviceIntent = Intent(context, FxAudioService::class.java).apply {
                putExtra("audio_session_id", sessionId)
            }
            if (action == AudioEffect.ACTION_OPEN_AUDIO_EFFECT_CONTROL_SESSION) {
                context.startService(serviceIntent)
            } else if (action == AudioEffect.ACTION_CLOSE_AUDIO_EFFECT_CONTROL_SESSION) {
                // Handle session closure if needed
            }
        }
    }
}
