package com.example.nytimesapicompose.ui.theme

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nytimesapicompose.data.Books
import com.example.nytimesapicompose.model.BooksApi
import com.example.nytimesapicompose.model.BooksApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Displays all data but not in list view
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Screen(){
    Column {
        val service = BooksApi()
        val mainScope = MainScope()
        // Move the LaunchedEffect to a composable function
        BookList(service)
    }
}

@Composable
fun BookList(service: BooksApiService) {
    var response by remember { mutableStateOf<Resource<Books>>(Resource.Loading()) }

    LaunchedEffect(Unit) {
        response = service.getBestSellers().toResource()
    }

    when (val result = response) {
        is Resource.Loading -> {
            Text("Loading books...")
        }
        is Resource.Success -> {
            val books = result.data.results
//            if (books.isNotEmpty()) {
//                DisplayBookComposable(books[0].book_details[0].title, books[0].book_details[0].author)
//            }
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(books) { book ->
                    for (bookDetail in book.book_details) {
                        DisplayBookComposable(bookDetail.title, bookDetail.author)
                    }
                    Divider()
                }
            }


        }
        is Resource.Failure -> {
            Text("Failed to load books: ${result.throwable.message}")
        }
    }
}

@Composable
fun DisplayBook(title: String, author: String) {
    // Use Jetpack Compose to display the book title and author
    Column {
        Text(text = "Title: $title")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Author: $author")
    }
}

@Composable
fun DisplayBookComposable(title: String, author: String) {
    DisplayBook(title, author)
}

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val throwable: Throwable) : Resource<Nothing>()
}

fun <T> Response<T>.toResource(): Resource<T> {
    return if (isSuccessful && body() != null) {
        Resource.Success(body()!!)
    } else {
        Resource.Failure(Throwable(message()))
    }
}

