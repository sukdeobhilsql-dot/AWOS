package com.awos.ui.explorer

import android.os.Environment
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.io.File

data class FileEntry(
    val name: String,
    val isDirectory: Boolean,
    val sizeBytes: Long,
    val path: String
)

/**
 * Minimal file-browsing ViewModel for Phase 1.
 * Root defaults to external storage. Deeper Storage Access Framework /
 * scoped-storage handling comes in a later hardening pass.
 */
class FileExplorerViewModel : ViewModel() {

    private val rootPath: String = Environment.getExternalStorageDirectory().absolutePath
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
