package com.example.numbers_kotlin
import retrofit2.http.GET

interface NumbersApiService {
    // The GET method needed to retrieve a random number trivia.
    @GET("/random/trivia?json")
    suspend fun getRandomNumberTrivia(): Trivia
}
