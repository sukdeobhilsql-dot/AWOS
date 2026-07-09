package com.awos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.awos.navigation.AwosNavHost
import com.awos.ui.theme.AwosTheme

/**
 * AWOS Phase 1 - Single entry point.
 * Boots straight into the Desktop shell (Splash kept minimal for MVP).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AwosTheme {
                AwosNavHost()
            }
        }
    }
}
