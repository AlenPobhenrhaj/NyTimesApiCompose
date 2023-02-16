package com.example.nytimesapicompose.data

data class BookDetail(
    val age_group: String,
    val author: String,
    val contributor: String,
    val contributor_note: String,
    val description: String,
    val price: String,
    val primary_isbn10: String,
    val primary_isbn13: String,
    val publisher: String,
    val title: String
)