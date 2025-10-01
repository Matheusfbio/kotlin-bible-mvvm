package com.br.android_learn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.br.android_learn.data.api.RetrofitInstance
import com.br.android_learn.repository.BibleRepository
import com.br.android_learn.ui.screen.BibleVerseScreen
import com.br.android_learn.viewModel.BibleViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = BibleViewModel(RetrofitInstance.api)

        setContent {
            BibleVerseScreen(viewModel)
        }
    }
}