package com.fxsound.clone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FxSoundSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color(0xFFB0B0B0),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..10f,
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFF00D5FF),
                activeTrackColor = Color(0xFF00D5FF),
                inactiveTrackColor = Color(0xFF333333)
            )
        )
        Text(
            text = value.toInt().toString(),
            color = Color.White,
            fontSize = 14.sp
        )
    }
}

@Composable
fun FxPowerButton(
    isOn: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(64.dp)
            .background(
                color = if (isOn) Color(0xFF00D5FF).copy(alpha = 0.2f) else Color(0xFF1E1E1E),
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        Text(
            text = "\u23FB",
            color = if (isOn) Color(0xFF00D5FF) else Color(0xFF333333),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
