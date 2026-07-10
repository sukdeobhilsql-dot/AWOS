package com.awos.ui.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminalScreen(onBack: () -> Unit) {
    val engine = remember { TerminalEngine() }
    val history = remember { mutableStateListOf("AWOS Basic Terminal — type 'help' to get started") }
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Terminal") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                items(history) { line ->
                    Text(
                        text = line,
                        color = androidx.compose.ui.graphics.Color(0xFF7CFC00),
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = engine.prompt(),
                    color = androidx.compose.ui.graphics.Color(0xFF7CFC00),
                    fontFamily = FontFamily.Monospace
                )
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f).testTag("terminal_input"),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.Black,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.Black,
                        focusedTextColor = androidx.compose.ui.graphics.Color(0xFF7CFC00),
                        unfocusedTextColor = androidx.compose.ui.graphics.Color(0xFF7CFC00)
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(fontFamily = FontFamily.Monospace),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = {
                        submit(input, engine, history) { input = "" }
                        scope.launch { listState.animateScrollToItem(history.size - 1) }
                    })
                )
            }
        }
    }
}

private fun submit(
    input: String,
    engine: TerminalEngine,
    history: MutableList<String>,
    clearInput: () -> Unit
) {
    if (input.isBlank()) return
    history.add("${engine.prompt()}$input")
    val output = engine.run(input)
    when {
        output == "__CLEAR__" -> history.clear()
        output.isNotBlank() -> history.add(output)
    }
    clearInput()
}