package com.fxsound.clone.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxsound.clone.ui.components.FxPowerButton
import com.fxsound.clone.ui.screens.EffectsScreen
import com.fxsound.clone.ui.screens.EqualizerScreen
import com.fxsound.clone.ui.screens.PresetsScreen
import com.fxsound.clone.ui.theme.FxColors
import com.fxsound.clone.ui.theme.FxSoundTheme
import com.fxsound.clone.viewmodel.FxSoundViewModel

@Composable
fun MainScreen(viewModel: FxSoundViewModel) {
    FxSoundTheme {
        Scaffold(
            topBar = { FxTopBar(viewModel) },
            bottomBar = { FxBottomBar(viewModel) },
            containerColor = FxColors.Background
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(FxColors.Background)
            ) {
                when (viewModel.selectedTab) {
                    0 -> EffectsScreen(viewModel)
                    1 -> EqualizerScreen(viewModel)
                    2 -> PresetsScreen(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FxTopBar(viewModel: FxSoundViewModel) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "fx",
                    color = FxColors.AccentCyan,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "sound",
                    color = FxColors.TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Light
                )
            }
        },
        actions = {
            // Status text
            Text(
                text = if (viewModel.isPowerOn) "ON" else "OFF",
                color = if (viewModel.isPowerOn) FxColors.Success else FxColors.TextDim,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )
            FxPowerButton(
                isOn = viewModel.isPowerOn,
                onClick = { viewModel.togglePower() },
                modifier = Modifier.padding(end = 8.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = FxColors.Background
        )
    )
}

@Composable
private fun FxBottomBar(viewModel: FxSoundViewModel) {
    NavigationBar(
        containerColor = FxColors.Surface,
        contentColor = FxColors.TextPrimary,
        tonalElevation = 0.dp
    ) {
        val tabs = listOf(
            TabItem("\uD83C\uDFA7", "Effects"),
            TabItem("\uD83C\uDFDA", "Equalizer"),
            TabItem("\uD83D\uDCCB", "Presets")
        )

        tabs.forEachIndexed { index, tab ->
            NavigationBarItem(
                selected = viewModel.selectedTab == index,
                onClick = { viewModel.selectTab(index) },
                icon = {
                    Text(
                        text = tab.icon,
                        fontSize = 20.sp
                    )
                },
                label = {
                    Text(
                        text = tab.label,
                        fontSize = 11.sp,
                        fontWeight = if (viewModel.selectedTab == index) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FxColors.AccentCyan,
                    selectedTextColor = FxColors.AccentCyan,
                    unselectedIconColor = FxColors.TextDim,
                    unselectedTextColor = FxColors.TextDim,
                    indicatorColor = FxColors.AccentCyanGlow
                )
            )
        }
    }
}

private data class TabItem(val icon: String, val label: String)
