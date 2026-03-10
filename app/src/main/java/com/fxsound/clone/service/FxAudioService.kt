package com.fxsound.clone.service

import android.app.Notification
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
    private lateinit var audioProcessor: AndroidAudioProcessor

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        // Initialize without a session; sessions are attached when we receive them.
        audioProcessor = AndroidAudioProcessor(0)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("fxsound")
            .setContentText("Processing audio for elite sound...")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .build()
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val sessionId = intent?.getIntExtra("audio_session_id", -1) ?: -1
        if (sessionId != -1) {
            audioProcessor.addOrUpdateSession(sessionId)
            audioProcessor.setEnabled(true)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "FxSound Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
}
