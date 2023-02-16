package com.example.nytimesapicompose.data

data class Books(
    val copyright: String,
    val last_modified: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)