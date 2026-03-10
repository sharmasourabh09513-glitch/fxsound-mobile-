package com.fxsound.clone.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fxsound.clone.ui.components.FxPowerButton
import com.fxsound.clone.ui.components.FxSoundSlider

@Composable
fun MainScreen() {
    var isPowerOn by remember { mutableStateOf(false) }
    var clarity by remember { mutableStateOf(5f) }
    var ambience by remember { mutableStateOf(3f) }
    var surround by remember { mutableStateOf(2f) }
    var dynamicBoost by remember { mutableStateOf(4f) }
    var bassBoost by remember { mutableStateOf(6f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with Power Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "fxsound",
                color = Color(0xFF00D5FF),
                style = MaterialTheme.typography.headlineMedium
            )
            FxPowerButton(isOn = isPowerOn, onClick = { isPowerOn = !isPowerOn })
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Visualizer Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF1E1E1E), shape = MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Text("Visualizer Placeholder", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sensations Sliders
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item { FxSoundSlider("Clarity", clarity, { clarity = it }) }
            item { FxSoundSlider("Ambience", ambience, { ambience = it }) }
            item { FxSoundSlider("Surround Sound", surround, { surround = it }) }
            item { FxSoundSlider("Dynamic Boost", dynamicBoost, { dynamicBoost = it }) }
            item { FxSoundSlider("Bass Boost", bassBoost, { bassBoost = it }) }
        }
    }
}
