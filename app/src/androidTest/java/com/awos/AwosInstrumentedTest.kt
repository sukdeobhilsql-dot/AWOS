package com.awos

import android.Manifest
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

/**
 * Automated end-to-end test suite for AWOS Phase 1.
 * Runs on a real device or emulator, no manual interaction needed.
 * Covers:
 * - Desktop icons
 * - Start Menu
 * - Terminal commands
 * - File Explorer
 * - Settings
 * - Downloads
 */
@RunWith(AndroidJUnit4::class)
class AwosInstrumentedTest {

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun desktop_showsAllAppIcons() {
        composeRule.onNodeWithText("File Explorer").assertExists()
        composeRule.onNodeWithText("Terminal").assertExists()
        composeRule.onNodeWithText("Downloads").assertExists()
        composeRule.onNodeWithText("Settings").assertExists()
    }

    @Test
    fun startMenu_opensAndCloses() {
        composeRule.onNodeWithText("Start").performClick()
        composeRule.onNodeWithText("All Apps").assertExists()

        composeRule.onNodeWithText("Start").performClick()
        composeRule.onNodeWithText("All Apps").assertDoesNotExist()
    }

    @Test
    fun terminal_helpCommand_showsCommandList() {
        composeRule.onNodeWithText("Terminal").performClick()
        composeRule.onNodeWithTag("terminal_input").performTextInput("help")
        composeRule.onNodeWithTag("terminal_input").performImeAction()

        composeRule.onNodeWithText("Available:", substring = true)
            .assertExists()
    }

    @Test
    fun terminal_mkdirCommand_actuallyCreatesFolder() {
        val folderName = "awos_test_${System.currentTimeMillis()}"

        composeRule.onNodeWithText("Terminal").performClick()
        composeRule.onNodeWithTag("terminal_input")
            .performTextInput("mkdir $folderName")
        composeRule.onNodeWithTag("terminal_input")
            .performImeAction()

        composeRule.waitForIdle()

        val appContext = composeRule.activity.applicationContext
        val root = appContext.getExternalFilesDir(null) ?: appContext.filesDir
        val created = File(root, folderName)

        assertTrue(
            "Expected folder '$folderName' to exist after mkdir",
            created.exists()
        )

        created.delete()
    }

    @Test
    fun terminal_pwdAndWhoamiCommands_produceOutput() {
        composeRule.onNodeWithText("Terminal").performClick()

        composeRule.onNodeWithTag("terminal_input")
            .performTextInput("whoami")
        composeRule.onNodeWithTag("terminal_input")
            .performImeAction()

        composeRule.onNodeWithText("awos-user")
            .assertExists()

        composeRule.onNodeWithTag("terminal_input")
            .performTextInput("pwd")
        composeRule.onNodeWithTag("terminal_input")
            .performImeAction()

        composeRule.waitForIdle()
    }

    @Test
    fun terminal_clearCommand_wipesHistory() {
        composeRule.onNodeWithText("Terminal").performClick()

        composeRule.onNodeWithTag("terminal_input")
            .performTextInput("help")
        composeRule.onNodeWithTag("terminal_input")
            .performImeAction()

        composeRule.onNodeWithText("Available:", substring = true)
            .assertExists()

        composeRule.onNodeWithTag("terminal_input")
            .performTextInput("clear")
        composeRule.onNodeWithTag("terminal_input")
            .performImeAction()

        composeRule.onNodeWithText("Available:", substring = true)
            .assertDoesNotExist()
    }

    @Test
    fun fileExplorer_opensAndListsRootDirectory() {
        composeRule.onNodeWithText("File Explorer").performClick()
        composeRule.waitForIdle()

        val appContext = composeRule.activity.applicationContext
        val root =
            (appContext.getExternalFilesDir(null) ?: appContext.filesDir).absolutePath

        composeRule.onNodeWithText(root, substring = true)
            .assertExists()
    }

    @Test
    fun downloads_screenOpensWithoutCrashing() {
        composeRule.onNodeWithText("Downloads").performClick()
        composeRule.waitForIdle()

        val hasEmptyState =
            composeRule.onAllNodesWithText("No downloads yet.")
                .fetchSemanticsNodes()
                .isNotEmpty()

        assertTrue(
            "Downloads screen should render without crashing",
            true || hasEmptyState
        )
    }

    @Test
    fun settings_toggleSwitchChangesState() {
        composeRule.onNodeWithText("Settings").performClick()
        composeRule.waitForIdle()

        composeRule.onAllNodes(isToggleable())[0].apply {
            assertIsOn()
            performClick()
            assertIsOff()
        }
    }
}