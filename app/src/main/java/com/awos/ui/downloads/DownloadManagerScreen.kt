package com.awos.ui.downloads

import android.os.Environment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

data class DownloadedFile(val name: String, val sizeKb: Long, val modified: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadManagerScreen(onBack: () -> Unit) {
    val downloadsDir = remember {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }
    val files = remember {
        mutableStateOf(
            downloadsDir.listFiles()
                ?.filter { it.isFile }
                ?.sortedByDescending { it.lastModified() }
                ?.map {
                    DownloadedFile(
                        name = it.name,
                        sizeKb = it.length() / 1024,
                        modified = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(it.lastModified()))
                    )
                } ?: emptyList()
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Downloads") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (files.value.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No downloads yet.")
            }
            return@Scaffold
        }

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(files.value) { file ->
                ListItem(
                    headlineContent = { Text(file.name) },
                    supportingContent = { Text("${file.sizeKb} KB • ${file.modified}") },
                    leadingContent = { Icon(Icons.Filled.InsertDriveFile, contentDescription = null) }
                )
                Divider()
            }
        }
    }
}
