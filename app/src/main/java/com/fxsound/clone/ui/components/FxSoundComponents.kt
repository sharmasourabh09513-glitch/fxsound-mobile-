package com.fxsound.clone.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxsound.clone.model.FxPreset
import com.fxsound.clone.ui.theme.FxColors
import kotlin.math.sin
import kotlin.random.Random

// ═══════════════════════════════════════════
// EFFECT SLIDER — Horizontal with glow track
// ═══════════════════════════════════════════
@Composable
fun FxEffectSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = if (enabled) FxColors.TextPrimary else FxColors.TextDim,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = String.format("%.1f", value),
                color = if (enabled) FxColors.AccentCyan else FxColors.TextDim,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..10f,
            enabled = enabled,
            colors = SliderDefaults.colors(
                thumbColor = if (enabled) FxColors.AccentCyan else FxColors.TextDim,
                activeTrackColor = if (enabled) FxColors.AccentCyan else FxColors.TextDim,
                inactiveTrackColor = FxColors.SliderTrack
            )
        )
    }
}

// ═══════════════════════════════════════════
// EQ BAND SLIDER — Vertical slider for EQ
// ═══════════════════════════════════════════
@Composable
fun EqBandSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.width(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%.0f", value),
            color = if (value != 0f) FxColors.AccentCyan else FxColors.TextDim,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        // Vertical slider using a rotated horizontal slider layout
        Box(
            modifier = Modifier
                .height(140.dp)
                .width(36.dp),
            contentAlignment = Alignment.Center
        ) {
            // Track background
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(2.dp))
                    .background(FxColors.SliderTrack)
            )
            // Active fill
            val fillHeight = ((value + 12f) / 24f).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .fillMaxHeight(fillHeight)
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        if (enabled) Brush.verticalGradient(
                            colors = listOf(FxColors.AccentCyan, FxColors.AccentCyanDim)
                        )
                        else Brush.verticalGradient(
                            colors = listOf(FxColors.TextDim, FxColors.TextDim)
                        )
                    )
            )
            // Thumb
            val thumbPosition = 1f - fillHeight
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = (thumbPosition * 130).dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(if (enabled) FxColors.AccentCyan else FxColors.TextDim)
                    .border(2.dp, FxColors.Background, CircleShape)
            )
        }
        // Use a regular slider behind the scenes for interaction
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = -12f..12f,
            enabled = enabled,
            modifier = Modifier
                .height(0.dp)
                .width(0.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.Transparent,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = FxColors.TextDim,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

// ═══════════════════════════════════════════
// POWER BUTTON — Glowing toggle
// ═══════════════════════════════════════════
@Composable
fun FxPowerButton(
    isOn: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = if (isOn) 1f else 0.3f,
        animationSpec = tween(300),
        label = "power_alpha"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(
                if (isOn) FxColors.AccentCyanGlow else FxColors.SurfaceVariant
            )
            .border(
                width = 2.dp,
                color = if (isOn) FxColors.AccentCyan else FxColors.Divider,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "\u23FB",
            color = if (isOn) FxColors.AccentCyan else FxColors.PowerOff,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ═══════════════════════════════════════════
// SPECTRUM VISUALIZER — Animated bars
// ═══════════════════════════════════════════
@Composable
fun SpectrumVisualizer(
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val barCount = 32
    val heights = remember { mutableStateListOf<Float>().apply { repeat(barCount) { add(Random.nextFloat() * 0.3f) } } }
    val infiniteTransition = rememberInfiniteTransition(label = "viz")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "viz_phase"
    )

    // Update bar heights
    LaunchedEffect(phase) {
        if (isActive) {
            for (i in 0 until barCount) {
                val wave = (sin(Math.toRadians((phase + i * 20).toDouble())) * 0.3f + 0.5f).toFloat()
                heights[i] = (wave * (0.4f + Random.nextFloat() * 0.6f)).coerceIn(0.05f, 1f)
            }
        } else {
            for (i in 0 until barCount) {
                heights[i] = 0.03f
            }
        }
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        val barWidth = size.width / barCount * 0.7f
        val gap = size.width / barCount * 0.3f

        for (i in 0 until barCount) {
            val barHeight = heights[i] * size.height
            val x = i * (barWidth + gap)
            val gradient = Brush.verticalGradient(
                colors = if (isActive) listOf(
                    FxColors.AccentCyan,
                    FxColors.AccentCyanDim.copy(alpha = 0.3f)
                ) else listOf(
                    FxColors.TextDim.copy(alpha = 0.3f),
                    FxColors.TextDim.copy(alpha = 0.1f)
                ),
                startY = size.height - barHeight,
                endY = size.height
            )
            drawRect(
                brush = gradient,
                topLeft = Offset(x, size.height - barHeight),
                size = Size(barWidth, barHeight)
            )
        }
    }
}

// ═══════════════════════════════════════════
// PRESET CARD — Selectable preset item
// ═══════════════════════════════════════════
@Composable
fun PresetCard(
    preset: FxPreset,
    isSelected: Boolean,
    onClick: () -> Unit,
    onDelete: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .then(
                if (isSelected) Modifier.border(
                    1.dp, FxColors.AccentCyan, RoundedCornerShape(12.dp)
                ) else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) FxColors.AccentCyanGlow else FxColors.SurfaceVariant
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Text(
                text = preset.icon,
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = preset.name,
                    color = if (isSelected) FxColors.AccentCyan else FxColors.TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = preset.description,
                    color = FxColors.TextSecondary,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // Selected indicator
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(FxColors.AccentCyan)
                )
            }
            // Delete for custom presets
            if (onDelete != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "\u2715",
                    color = FxColors.Error,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        }
    }
}

// ═══════════════════════════════════════════
// FREQUENCY CURVE — EQ visualization
// ═══════════════════════════════════════════
@Composable
fun FrequencyCurve(
    eqBands: List<Float>,
    modifier: Modifier = Modifier,
    isActive: Boolean = true
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        if (eqBands.isEmpty()) return@Canvas

        val path = Path()
        val midY = size.height / 2
        val segmentWidth = size.width / (eqBands.size - 1).coerceAtLeast(1)

        // Draw center line
        drawLine(
            color = FxColors.Divider,
            start = Offset(0f, midY),
            end = Offset(size.width, midY),
            strokeWidth = 1f
        )

        // Draw curve
        val points = eqBands.mapIndexed { index, value ->
            val x = index * segmentWidth
            val y = midY - (value / 12f * midY * 0.8f)
            Offset(x, y)
        }

        if (points.isNotEmpty()) {
            path.moveTo(points[0].x, points[0].y)
            for (i in 1 until points.size) {
                val cpX = (points[i - 1].x + points[i].x) / 2
                path.cubicTo(cpX, points[i - 1].y, cpX, points[i].y, points[i].x, points[i].y)
            }

            drawPath(
                path = path,
                color = if (isActive) FxColors.AccentCyan else FxColors.TextDim,
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw dots at each band
            points.forEach { point ->
                drawCircle(
                    color = if (isActive) FxColors.AccentCyan else FxColors.TextDim,
                    radius = 3.dp.toPx(),
                    center = point
                )
            }
        }
    }
}
