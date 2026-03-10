package com.fxsound.clone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fxsound.clone.ui.MainScreen

import android.content.Intent
import com.fxsound.clone.service.FxAudioService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState: Bundle?)
        
        // Start the audio service
        val serviceIntent = Intent(this, FxAudioService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        setContent {
            MainScreen()
        }
    }
}
