package com.fxsound.clone.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.fxsound.clone.R
import com.fxsound.clone.audio.AndroidAudioProcessor

class FxAudioService : Service() {
    private val CHANNEL_ID = "FxSoundServiceChannel"
    private lateinit var audioProcessor: AndroidAudioProcessor

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        // Provide a default or placeholder session ID for initialization
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
            // In a full implementation, we would add this session to a list
            // For now, we update the existing processor
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
