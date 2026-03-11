package com.fxsound.clone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// FxSound color palette
object FxColors {
    val Background = Color(0xFF0A0A0F)
    val Surface = Color(0xFF14141F)
    val SurfaceVariant = Color(0xFF1A1A2E)
    val AccentCyan = Color(0xFF00D5FF)
    val AccentCyanDim = Color(0xFF007A94)
    val AccentCyanGlow = Color(0x4D00D5FF)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB0B0B0)
    val TextDim = Color(0xFF666680)
    val Divider = Color(0xFF2A2A3A)
    val SliderTrack = Color(0xFF2A2A3A)
    val CardBorder = Color(0xFF2A2A3A)
    val Success = Color(0xFF00E676)
    val Warning = Color(0xFFFFAB00)
    val Error = Color(0xFFFF5252)
    val PowerOn = Color(0xFF00D5FF)
    val PowerOff = Color(0xFF444466)
}

private val FxDarkColorScheme = darkColorScheme(
    primary = FxColors.AccentCyan,
    onPrimary = Color.Black,
    secondary = FxColors.AccentCyanDim,
    background = FxColors.Background,
    surface = FxColors.Surface,
    surfaceVariant = FxColors.SurfaceVariant,
    onBackground = FxColors.TextPrimary,
    onSurface = FxColors.TextPrimary,
    onSurfaceVariant = FxColors.TextSecondary,
    outline = FxColors.Divider
)

@Composable
fun FxSoundTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FxDarkColorScheme,
        typography = Typography(
            headlineLarge = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = FxColors.TextPrimary
            ),
            headlineMedium = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = FxColors.AccentCyan
            ),
            titleMedium = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = FxColors.TextPrimary
            ),
            bodyLarge = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = FxColors.TextPrimary
            ),
            bodyMedium = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = FxColors.TextSecondary
            ),
            labelSmall = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = FxColors.TextDim
            )
        ),
        content = content
    )
}
