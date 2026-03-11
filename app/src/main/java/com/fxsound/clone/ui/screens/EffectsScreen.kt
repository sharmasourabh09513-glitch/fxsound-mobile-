package com.fxsound.clone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxsound.clone.ui.components.FxEffectSlider
import com.fxsound.clone.ui.components.SpectrumVisualizer
import com.fxsound.clone.ui.theme.FxColors
import com.fxsound.clone.viewmodel.FxSoundViewModel

@Composable
fun EffectsScreen(viewModel: FxSoundViewModel) {
    val isOn = viewModel.isPowerOn

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp)
    ) {
        // Visualizer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(FxColors.SurfaceVariant)
                .padding(12.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "SPECTRUM",
                        color = FxColors.TextDim,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isOn) "ACTIVE" else "STANDBY",
                        color = if (isOn) FxColors.Success else FxColors.TextDim,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                SpectrumVisualizer(isActive = isOn)
            }
        }

        // Current preset indicator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PRESET: ${viewModel.currentPresetName.uppercase()}",
                color = FxColors.AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Section header
        Text(
            text = "SENSATIONS",
            color = FxColors.TextDim,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, bottom = 4.dp)
        )

        // Effect sliders
        FxEffectSlider("Clarity", viewModel.clarity, { viewModel.setClarity(it) }, enabled = isOn)
        FxEffectSlider("Ambience", viewModel.ambience, { viewModel.setAmbience(it) }, enabled = isOn)
        FxEffectSlider("3D Surround", viewModel.surround, { viewModel.setSurround(it) }, enabled = isOn)
        FxEffectSlider("Dynamic Boost", viewModel.dynamicBoost, { viewModel.setDynamicBoost(it) }, enabled = isOn)
        FxEffectSlider("Bass Boost", viewModel.bassBoost, { viewModel.setBassBoost(it) }, enabled = isOn)
        FxEffectSlider("Fidelity", viewModel.fidelity, { viewModel.setFidelity(it) }, enabled = isOn)
        FxEffectSlider("Volume Boost", viewModel.volumeBoost, { viewModel.setVolumeBoost(it) }, enabled = isOn)
    }
}
