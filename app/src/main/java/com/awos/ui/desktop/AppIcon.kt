package com.awos.ui.desktop

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.graphics.vector.ImageVector
import com.awos.navigation.AwosRoutes

/**
 * Represents a single desktop / start-menu shortcut.
 * Phase 2+ will make this dynamic (installed Linux apps, Wine apps, etc).
 */
data class AppIcon(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val phase1Apps = listOf(
    AppIcon("File Explorer", Icons.Filled.Folder, AwosRoutes.EXPLORER),
    AppIcon("Terminal", Icons.Filled.Terminal, AwosRoutes.TERMINAL),
    AppIcon("Downloads", Icons.Filled.Download, AwosRoutes.DOWNLOADS),
    AppIcon("Browser", Icons.Filled.Public, AwosRoutes.DESKTOP), // stub, wired in Phase 4
    AppIcon("Settings", Icons.Filled.Settings, AwosRoutes.SETTINGS)
)
