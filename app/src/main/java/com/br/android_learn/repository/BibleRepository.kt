package com.br.android_learn.repository

import com.br.android_learn.data.api.BibleApiService
import com.br.android_learn.data.model.BibleVerse

class BibleRepository(private val api: BibleApiService) {

    suspend fun getVerse(reference: String): Result<BibleVerse> {
        return TODO("Provide the return value")
    }
}
