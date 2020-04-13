package com.example.numbers_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val numbersRepository = NumbersRepository()

    /**
     * MutableLiveData always private in our repo, no one else should modify this
     */
    val trivia = numbersRepository.trivia

    private var _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from Activity for error showing
     */
    val errorText: LiveData<String>
        get() = _errorText

    fun refreshNumber() = launchDataLoad {
        numbersRepository.getRandomNumberTrivia()
    }

    /**
     * Helper function to run function after work completes
     * block() is a UI thread function, hence the name block
     * Also sets _error String if failure
     */
    private fun launchDataLoad(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (error: NumbersRepository.TriviaRefreshError) {
                _errorText.value = error.message
            }
        }
    }
}