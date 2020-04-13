package com.example.numbers_kotlin
import retrofit2.http.GET

interface NumbersApiService {
    /**
     * The GET method needed to retrieve a random number trivia.
     * Since retrofit 2.6.0 suspend is supported with coroutines!
     */
    @GET("/random/trivia?json")
    suspend fun getRandomNumberTrivia(): Trivia
}
