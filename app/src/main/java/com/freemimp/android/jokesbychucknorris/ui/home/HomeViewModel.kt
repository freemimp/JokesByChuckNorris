package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeController
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val randomJokeController: RandomJokeController) : ViewModel() {

    var errorResponse = MutableLiveData<String?>()

    suspend fun getRandomJoke(): String {
        var response = ""
        val retrofitResponse = randomJokeController.getRandomJokeAsync().await()
        if (retrofitResponse.isSuccessful) {
            response = randomJokeController.getRandomJokeAsync().await().body()?.value?.joke ?: ""
        } else {
            errorResponse.value = ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
        }
        return response
    }
}
