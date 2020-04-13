package com.example.numbers_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val numbersRepository = NumbersRepository()

    /**
     * This property points direct to the LiveDatain the repository, that value
     * get's updated when user clicks FAB. This happens through the refreshNumber() in this class :)
     */
    val trivia = numbersRepository.trivia

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    /**
     * Expose non MutableLiveData via getter
     * errorText can be observed from Activity for error showing
     * Encapsulation :)
     */
    val errorText: LiveData<String>
        get() = _errorText

    /**
     * The viewModelScope is bound to Dispatchers.Main and will automatically be cancelled when the ViewModel is cleared.
     * Extension method of lifecycle-viewmodel-ktx library
     */
    fun refreshNumber() {
        viewModelScope.launch {
            try {
                //the numberRepository sets it's own livedata property
                //our own trivia property points to this one
                numbersRepository.getRandomNumberTrivia()
            } catch (error: NumbersRepository.TriviaRefreshError) {
                _errorText.value = error.message
            }
        }
    }
}