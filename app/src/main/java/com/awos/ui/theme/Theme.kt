package com.awos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AwosColorScheme = darkColorScheme(
    primary = AwosBlue,
    secondary = AwosAccent,
    background = AwosDarkBg,
    surface = AwosSurface,
    onPrimary = AwosText,
    onBackground = AwosText,
    onSurface = AwosText
)

@Composable
fun AwosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AwosColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}
