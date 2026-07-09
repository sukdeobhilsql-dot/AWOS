package com.awos.ui.desktop

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.awos.ui.startmenu.StartMenu
import com.awos.ui.taskbar.Taskbar
import com.awos.ui.theme.AwosDarkBg
import com.awos.ui.theme.AwosText

/**
 * The AWOS "home screen" - equivalent to a Windows Desktop.
 * Icon grid + Taskbar (bottom) + Start Menu overlay.
 */
@Composable
fun DesktopScreen(navController: NavHostController) {
    var startMenuOpen by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(AwosDarkBg, androidx.compose.ui.graphics.Color(0xFF0F1115)))
            )
    ) {
        // Desktop icon grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp)
        ) {
            items(phase1Apps) { app ->
                DesktopIcon(app = app, onClick = { navController.navigate(app.route) })
            }
        }

        // Start Menu overlay
        if (startMenuOpen) {
            StartMenu(
                apps = phase1Apps,
                onAppClick = { route ->
                    startMenuOpen = false
                    navController.navigate(route)
                },
                onDismiss = { startMenuOpen = false }
            )
        }

        // Taskbar pinned to bottom
        Taskbar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onStartClick = { startMenuOpen = !startMenuOpen }
        )
    }
}

@Composable
private fun DesktopIcon(app: AppIcon, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = app.icon, contentDescription = app.label, tint = AwosText)
        Spacer(Modifier.height(4.dp))
        Text(text = app.label, color = AwosText, fontSize = 12.sp)
    }
}
