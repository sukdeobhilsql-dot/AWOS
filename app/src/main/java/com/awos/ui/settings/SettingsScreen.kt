package com.awos.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class SettingToggle(val label: String, val description: String)

private val phase1Settings = listOf(
    SettingToggle("Dark Desktop Theme", "Windows-style dark theme (default)"),
    SettingToggle("Show Clock in Taskbar", "Display live time in bottom-right tray"),
    SettingToggle("Enable Terminal", "Turn the basic shell simulator on/off"),
    SettingToggle("Auto-open Downloads folder", "Jump to Downloads after a file finishes")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val toggleStates = remember { mutableStateMapOf<String, Boolean>().apply {
        phase1Settings.forEach { put(it.label, true) }
    } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("AWOS — Version 0.1.0 (MVP)", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))

            phase1Settings.forEach { setting ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(setting.label, style = MaterialTheme.typography.bodyLarge)
                        Text(setting.description, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(
                        checked = toggleStates[setting.label] ?: true,
                        onCheckedChange = { toggleStates[setting.label] = it }
                    )
                }
                Divider()
            }
        }
    }
}
