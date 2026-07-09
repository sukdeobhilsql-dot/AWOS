package com.awos.ui.taskbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.awos.ui.theme.AwosAccent
import com.awos.ui.theme.AwosTaskbar
import com.awos.ui.theme.AwosText
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * Bottom taskbar: Start button, running-app tray placeholder, clock/status.
 */
@Composable
fun Taskbar(modifier: Modifier = Modifier, onStartClick: () -> Unit) {
    var currentTime by remember { mutableStateOf(timeNow()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = timeNow()
            delay(1000L)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(AwosTaskbar)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Start button
        Row(
            modifier = Modifier
                .background(AwosAccent.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                .clickable { onStartClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Apps, contentDescription = "Start", tint = AwosAccent)
            Spacer(Modifier.width(6.dp))
            Text("Start", color = AwosText)
        }

        Spacer(Modifier.weight(1f))

        // Status tray (placeholder icons for Phase 1)
        Icon(Icons.Filled.Wifi, contentDescription = "Wifi", tint = AwosText, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Icon(Icons.Filled.BatteryFull, contentDescription = "Battery", tint = AwosText, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(10.dp))
        Text(text = currentTime, color = AwosText)
    }
}

private fun timeNow(): String =
    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
