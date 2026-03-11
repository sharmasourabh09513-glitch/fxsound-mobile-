package com.fxsound.clone.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxsound.clone.model.PresetLibrary
import com.fxsound.clone.ui.components.PresetCard
import com.fxsound.clone.ui.theme.FxColors
import com.fxsound.clone.viewmodel.FxSoundViewModel

@Composable
fun PresetsScreen(viewModel: FxSoundViewModel) {
    var showSaveDialog by remember { mutableStateOf(false) }
    var customName by remember { mutableStateOf("") }

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
                text = "PRESETS",
                color = FxColors.TextDim,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(
                onClick = { showSaveDialog = true },
                colors = ButtonDefaults.textButtonColors(contentColor = FxColors.AccentCyan)
            ) {
                Text("+ SAVE CUSTOM", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Built-in presets
            item {
                Text(
                    text = "BUILT-IN",
                    color = FxColors.TextDim,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            items(PresetLibrary.presets) { preset ->
                PresetCard(
                    preset = preset,
                    isSelected = viewModel.currentPresetName == preset.name,
                    onClick = { viewModel.selectPreset(preset) }
                )
            }

            // Custom presets
            if (viewModel.customPresets.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "CUSTOM",
                        color = FxColors.TextDim,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(viewModel.customPresets) { preset ->
                    PresetCard(
                        preset = preset,
                        isSelected = viewModel.currentPresetName == preset.name,
                        onClick = { viewModel.selectPreset(preset) },
                        onDelete = { viewModel.deleteCustomPreset(preset.name) }
                    )
                }
            }

            // Bottom padding
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }

    // Save dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Custom Preset", color = FxColors.TextPrimary) },
            text = {
                OutlinedTextField(
                    value = customName,
                    onValueChange = { customName = it },
                    label = { Text("Preset Name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = FxColors.AccentCyan,
                        cursorColor = FxColors.AccentCyan
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (customName.isNotBlank()) {
                            viewModel.saveCustomPreset(customName.trim())
                            customName = ""
                            showSaveDialog = false
                        }
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = FxColors.AccentCyan)
                ) {
                    Text("SAVE")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showSaveDialog = false },
                    colors = ButtonDefaults.textButtonColors(contentColor = FxColors.TextSecondary)
                ) {
                    Text("CANCEL")
                }
            },
            containerColor = FxColors.Surface,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
