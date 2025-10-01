package com.br.android_learn.ui.screen

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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.android_learn.data.api.RetrofitInstance
import com.br.android_learn.viewModel.BibleViewModel

@Composable
fun BibleVerseScreen(viewModel: BibleViewModel) {

    val verse by viewModel.verse
    val loading by viewModel.loading
    val error by viewModel.error
    var inputText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            // Remover foco do TextField ao clicar fora dele
//            .clickable(
//                indication = null,
//                interactionSource = remember { MutableInteractionSource() })
//            {
//                focusManager.clearFocus()
//            }
    ) {
        // Campo de pesquisa
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Digite a passagem (ex: john 3:16)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Alignment.Center

        Button(
            onClick = {
                if (inputText.isNotBlank()) {
                    viewModel.fetchVerse(inputText)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(),
            enabled = !loading
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Conteúdo
        when {
            loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator()
                }
            }
//            loading -> CircularProgressIndicator()
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
@Preview(name = "Preview - Dados", showBackground = true, showSystemUi = false)
@Composable
fun BibleVerseScreenPreviewWithData() {
    // Fake ViewModel só para o Preview
    val fakeViewModel = object : BibleViewModel(RetrofitInstance.api) {
        init {
            // Preenche manualmente o estado
            _verse.value = com.br.android_learn.data.model.BibleVerse(
                reference = "John 3:16, Genesis 1:1", // Exemplo de referência
                verses = listOf(
                    com.br.android_learn.data.model.Verse(
                        book_id = "JHN", // Exemplo de book_id
                        book_name = "John",
                        chapter = 3,
                        verse = 16,
                        text = "For God so loved the world..."
                    ),
                    com.br.android_learn.data.model.Verse(
                        book_id = "GEN", // Exemplo de book_id
                        book_name = "Genesis",
                        chapter = 1,
                        verse = 1,
                        text = "In the beginning, God created the heavens and the earth."
                    )
                ),
                text = "For God so loved the world... In the beginning, God created the heavens and the earth." // Exemplo de texto combinado
            )
            _loading.value = false
            _error.value = null
        }
    }

    MaterialTheme {
        BibleVerseScreen(viewModel = fakeViewModel)
    }
}

@Preview(name = "Preview - Carregando", showBackground = true, showSystemUi = true)
@Composable
fun BibleVerseScreenPreviewLoading() {
    val fakeViewModel = object : BibleViewModel(RetrofitInstance.api) {
        init {
            _loading.value = true
        }
    }

    MaterialTheme {
        BibleVerseScreen(viewModel = fakeViewModel)
    }
}

@Preview(name = "Preview - Erro", showBackground = true, showSystemUi = true)
@Composable
fun BibleVerseScreenPreviewError() {
    val fakeViewModel = object : BibleViewModel(RetrofitInstance.api) {
        init {
            _error.value = "Falha ao carregar versículo"
        }
    }

    MaterialTheme {
        BibleVerseScreen(viewModel = fakeViewModel)
    }
}
