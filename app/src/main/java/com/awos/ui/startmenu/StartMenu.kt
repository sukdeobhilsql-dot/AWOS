package com.awos.ui.startmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awos.ui.desktop.AppIcon
import com.awos.ui.theme.AwosSurface
import com.awos.ui.theme.AwosText

/**
 * Start Menu: opens above the taskbar, lists all installed/available apps.
 * Tapping outside dismisses it (scrim). Grid adapts to screen width and
 * the panel height is capped so it never overflows on large/TV screens.
 */
@Composable
fun StartMenu(
    apps: List<AppIcon>,
    onAppClick: (String) -> Unit,
    onDismiss: () -> Unit
) {
    // Full-screen scrim to catch outside taps
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.4f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 64.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .background(AwosSurface, RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Text("All Apps", color = AwosText, style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 96.dp),
                modifier = Modifier.heightIn(max = 320.dp)
            ) {
                items(apps) { app ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onAppClick(app.route) },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = app.icon, contentDescription = app.label, tint = AwosText)
                        Spacer(Modifier.height(4.dp))
                        Text(app.label, color = AwosText, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}