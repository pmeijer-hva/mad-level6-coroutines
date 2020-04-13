package com.example.numbers_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.withTimeout

class NumbersRepository {
    private val numbersApi: NumbersApiService = NumbersApi.createApi()

    private val _trivia:  MutableLiveData<Trivia> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * Encapsulation :)
     */
    val trivia: LiveData<Trivia>
        get() = _trivia

    /**
     * suspend function that calls a suspend function from the numbersApi call
     */
    suspend fun getRandomNumberTrivia()  {
        try {
            //timeout the request after 5 seconds
            val result = withTimeout(5_000) {
                numbersApi.getRandomNumberTrivia()
            }

            _trivia.value = result
        } catch (error: Throwable) {
            throw TriviaRefreshError("Unable to refresh trivia", error)
        }
    }

    class TriviaRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}
