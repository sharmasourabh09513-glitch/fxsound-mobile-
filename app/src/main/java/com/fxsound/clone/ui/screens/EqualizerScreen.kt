package com.fxsound.clone.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxsound.clone.ui.components.FrequencyCurve
import com.fxsound.clone.ui.theme.FxColors
import com.fxsound.clone.viewmodel.FxSoundViewModel

private val EQ_LABELS = listOf("32", "64", "125", "250", "500", "1K", "2K", "4K", "8K", "16K")

@Composable
fun EqualizerScreen(viewModel: FxSoundViewModel) {
    val isOn = viewModel.isPowerOn

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "10-BAND EQUALIZER",
                color = FxColors.TextDim,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "RESET",
                color = FxColors.AccentCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(FxColors.SurfaceVariant)
                    .clickable { viewModel.resetEq() }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Frequency curve
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(FxColors.SurfaceVariant)
                .padding(12.dp)
        ) {
            FrequencyCurve(
                eqBands = viewModel.eqBands.toList(),
                isActive = isOn
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // dB labels
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("+12", color = FxColors.TextDim, fontSize = 9.sp)
            Text("0 dB", color = FxColors.TextDim, fontSize = 9.sp)
            Text("-12", color = FxColors.TextDim, fontSize = 9.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // EQ Band sliders
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (i in 0 until 10) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    // Value label
                    Text(
                        text = String.format("%.0f", viewModel.eqBands[i]),
                        color = if (viewModel.eqBands[i] != 0f) FxColors.AccentCyan else FxColors.TextDim,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    // Vertical slider
                    VerticalEqSlider(
                        value = viewModel.eqBands[i],
                        onValueChange = { viewModel.setEqBand(i, it) },
                        enabled = isOn,
                        modifier = Modifier.height(180.dp)
                    )

                    // Frequency label
                    Text(
                        text = EQ_LABELS[i],
                        color = FxColors.TextDim,
                        fontSize = 9.sp,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (i < 5) "Hz" else "",
                        color = FxColors.TextDim,
                        fontSize = 7.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalEqSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    // Using a regular Slider rotated via layout tricks
    // We invert the range so up = positive
    Column(
        modifier = modifier.width(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = -12f..12f,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            colors = SliderDefaults.colors(
                thumbColor = if (enabled) FxColors.AccentCyan else FxColors.TextDim,
                activeTrackColor = if (enabled) FxColors.AccentCyan else FxColors.TextDim,
                inactiveTrackColor = FxColors.SliderTrack
            )
        )
    }
}
