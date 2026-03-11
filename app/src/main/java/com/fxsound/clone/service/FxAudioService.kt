package com.fxsound.clone.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fxsound.clone.audio.AndroidAudioProcessor

class FxAudioService : Service() {
    private val CHANNEL_ID = "FxSoundServiceChannel"
    private var audioProcessor: AndroidAudioProcessor? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        audioProcessor = AndroidAudioProcessor(0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("FxSound")
            .setContentText("Audio enhancement active")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setOngoing(true)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sessionId = intent?.getIntExtra("audio_session_id", -1) ?: -1
        if (sessionId != -1) {
            audioProcessor?.addOrUpdateSession(sessionId)
            audioProcessor?.setEnabled(true)
        }
        return START_STICKY
    }

    override fun onDestroy() {
        audioProcessor?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "FxSound Audio Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
