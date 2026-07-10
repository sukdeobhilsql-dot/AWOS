package com.awos.ui.explorer

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import java.io.File

data class FileEntry(
    val name: String,
    val isDirectory: Boolean,
    val sizeBytes: Long,
    val path: String
)

/**
 * File-browsing ViewModel for Phase 1.
 * Root is the app's own external-files sandbox (Android/data/com.awos/files) -
 * no runtime storage permission required, works consistently across all
 * Android versions. Visible to any file manager app under that path.
 */
class FileExplorerViewModel(application: Application) : AndroidViewModel(application) {

    private val rootPath: String =
        (application.getExternalFilesDir(null) ?: application.filesDir).apply { mkdirs() }.absolutePath

    val currentPath = mutableStateOf(rootPath)
    val entries = mutableStateOf<List<FileEntry>>(emptyList())

    init {
        loadDirectory(rootPath)
    }

    fun loadDirectory(path: String) {
        val dir = File(path)
        val listed = dir.listFiles()
            ?.sortedWith(compareByDescending<File> { it.isDirectory }.thenBy { it.name.lowercase() })
            ?.map {
                FileEntry(
                    name = it.name,
                    isDirectory = it.isDirectory,
                    sizeBytes = if (it.isFile) it.length() else 0L,
                    path = it.absolutePath
                )
            } ?: emptyList()

        currentPath.value = path
        entries.value = listed
    }

    fun navigateUp(): Boolean {
        val current = File(currentPath.value)
        val parent = current.parentFile
        return if (parent != null && current.absolutePath != rootPath) {
            loadDirectory(parent.absolutePath)
            true
        } else {
            false
        }
    }

    fun isAtRoot(): Boolean = currentPath.value == rootPath
}