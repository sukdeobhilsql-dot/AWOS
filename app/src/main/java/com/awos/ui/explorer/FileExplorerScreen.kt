package com.awos.ui.explorer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileExplorerScreen(onBack: () -> Unit, viewModel: FileExplorerViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("File Explorer") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (!viewModel.navigateUp()) onBack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text(
                text = viewModel.currentPath.value,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(viewModel.entries.value) { entry ->
                    ListItem(
                        headlineContent = { Text(entry.name) },
                        supportingContent = {
                            if (!entry.isDirectory) Text("${entry.sizeBytes / 1024} KB")
                        },
                        leadingContent = {
                            Icon(
                                imageVector = if (entry.isDirectory) Icons.Filled.Folder else Icons.Filled.Description,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.clickable(enabled = entry.isDirectory) {
                            viewModel.loadDirectory(entry.path)
                        }
                    )
                    Divider()
                }
            }
        }
    }
}