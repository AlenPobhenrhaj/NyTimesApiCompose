package com.example.nytimesapicompose.model

import com.example.nytimesapicompose.data.Books
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BooksApiService {
    @GET("lists.json?list=hardcover-fiction&api-key=VnAPYRcntWaevUsIClh5lsmM5AbTkDxj")
    suspend fun getBestSellers(): Response<Books>
}

fun BooksApi(): BooksApiService {
    val BASE_URL = "https://api.nytimes.com/svc/books/v3/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: BooksApiService = retrofit.create(BooksApiService::class.java)

    return service
}