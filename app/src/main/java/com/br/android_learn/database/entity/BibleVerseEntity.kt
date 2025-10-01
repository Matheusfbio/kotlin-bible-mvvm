package com.br.android_learn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BibleVerseEntity (
    @PrimaryKey
    val reference: String,
    val verses: List<Verse>,
    val text: String
)

@Entity
data class Verse(
    @PrimaryKey
    val book_id: String,
    val book_name: String,
    val chapter: Int,
    val verse: Int,
    val text: String
)