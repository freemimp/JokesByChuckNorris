package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val randomJokeApi: RandomJokeApi) : ViewModel() {

    var errorResponse = MutableLiveData<String?>()

    suspend fun getRandomJoke(): String {
        var response = ""
        val retrofitResponse = randomJokeApi.getRandomJokeAsync().await()
        if (retrofitResponse.isSuccessful) {
            response = randomJokeApi.getRandomJokeAsync().await().body()?.value?.joke ?: ""
        } else {
            withContext(Dispatchers.Main) {
                errorResponse.value = ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
            }
        }
        return response
    }
}
