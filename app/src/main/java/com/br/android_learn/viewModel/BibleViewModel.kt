package com.br.android_learn.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.android_learn.data.model.BibleVerse
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.br.android_learn.data.api.BibleApiService

open class BibleViewModel(private val repository: BibleApiService) : ViewModel() {

    var _verse = mutableStateOf<BibleVerse?>(null)
    val verse: State<BibleVerse?> get() = _verse

    var _loading = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    var _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    fun fetchVerse(passage: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getVerse(passage)
                _verse.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
                _verse.value = null
            } finally {
                _loading.value = false
            }
        }
    }
}
