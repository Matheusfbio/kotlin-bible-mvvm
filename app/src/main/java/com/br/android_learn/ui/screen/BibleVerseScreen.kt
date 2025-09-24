package com.br.android_learn.ui.screen

import ads_mobile_sdk.h5
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.br.android_learn.viewModel.BibleViewModel

@Composable
fun BibleVerseScreen(viewModel: BibleViewModel) {

    val verse by viewModel.verse
    val loading by viewModel.loading
    val error by viewModel.error
    var inputText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Campo de pesquisa
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Digite a passagem (ex: john 3:16)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.fetchVerse(inputText)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Conteúdo
        when {
            loading -> CircularProgressIndicator()
            error != null -> Text(text = "Erro: $error", color = Color.Red)
            verse != null -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(verse!!.verses) { v ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "${v.book_name} ${v.chapter}:${v.verse}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = v.text,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
