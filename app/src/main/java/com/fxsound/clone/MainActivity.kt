package com.fxsound.clone

import android.os.Bundle
import android.os.Build
import android.content.Intent
import android.content.pm.PackageManager
import android.Manifest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fxsound.clone.service.FxAudioService
import com.fxsound.clone.ui.MainScreen
import com.fxsound.clone.viewmodel.FxSoundViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FxSoundViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        // Start the audio service safely
        try {
            val serviceIntent = Intent(this, FxAudioService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContent {
            MainScreen(viewModel = viewModel)
        }
    }
}
