package com.awos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.awos.ui.desktop.DesktopScreen
import com.awos.ui.downloads.DownloadManagerScreen
import com.awos.ui.explorer.FileExplorerScreen
import com.awos.ui.settings.SettingsScreen
import com.awos.ui.terminal.TerminalScreen

object AwosRoutes {
    const val DESKTOP = "desktop"
    const val EXPLORER = "explorer"
    const val TERMINAL = "terminal"
    const val SETTINGS = "settings"
    const val DOWNLOADS = "downloads"
}

@Composable
fun AwosNavHost() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = AwosRoutes.DESKTOP) {
        composable(AwosRoutes.DESKTOP) {
            DesktopScreen(navController = navController)
        }
        composable(AwosRoutes.EXPLORER) {
            FileExplorerScreen(onBack = { navController.popBackStack() })
        }
        composable(AwosRoutes.TERMINAL) {
            TerminalScreen(onBack = { navController.popBackStack() })
        }
        composable(AwosRoutes.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable(AwosRoutes.DOWNLOADS) {
            DownloadManagerScreen(onBack = { navController.popBackStack() })
        }
    }
}
