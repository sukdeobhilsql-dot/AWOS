package com.awos.ui.terminal

import android.os.Environment
import java.io.File

/**
 * Phase 1 "Basic Terminal": a lightweight simulated shell that understands
 * a handful of navigation/utility commands directly against the filesystem.
 *
 * NOTE: This is NOT a real Linux shell. Phase 2 replaces/extends this with
 * an actual Termux + proot-distro (Ubuntu/Debian) bash session.
 */
class TerminalEngine {
    private var currentDir: File = Environment.getExternalStorageDirectory()

    fun prompt(): String = "awos:${currentDir.name.ifEmpty { "/" }} $ "

    fun run(rawInput: String): String {
        val input = rawInput.trim()
        if (input.isEmpty()) return ""

        val parts = input.split(Regex("\\s+"))
        val cmd = parts[0]
        val args = parts.drop(1)

        return try {
            when (cmd) {
                "help" -> "Available: ls, cd, pwd, mkdir, echo, clear, whoami, date, help"
                "pwd" -> currentDir.absolutePath
                "whoami" -> "awos-user"
                "date" -> java.util.Date().toString()
                "ls" -> currentDir.listFiles()?.joinToString("  ") { it.name } ?: "(empty)"
                "cd" -> changeDirectory(args.getOrNull(0))
                "mkdir" -> makeDirectory(args.getOrNull(0))
                "echo" -> args.joinToString(" ")
                "clear" -> "__CLEAR__"
                else -> "command not found: $cmd (Linux runtime arrives in Phase 2)"
            }
        } catch (e: Exception) {
            "error: ${e.message}"
        }
    }

    private fun changeDirectory(target: String?): String {
        if (target == null) return "cd: missing operand"
        val next = when (target) {
            ".." -> currentDir.parentFile ?: currentDir
            "~" -> Environment.getExternalStorageDirectory()
            else -> File(currentDir, target)
        }
        return if (next.exists() && next.isDirectory) {
            currentDir = next
            ""
        } else {
            "cd: no such directory: $target"
        }
    }

    private fun makeDirectory(name: String?): String {
        if (name == null) return "mkdir: missing operand"
        val newDir = File(currentDir, name)
        return if (newDir.mkdirs()) "" else "mkdir: failed to create '$name'"
    }
}
