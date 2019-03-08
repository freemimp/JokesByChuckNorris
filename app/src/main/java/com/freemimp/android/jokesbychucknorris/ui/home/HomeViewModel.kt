package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val randomJokeApi: RandomJokeApi) : ViewModel() {

    val errorResponse = SingleLiveEvent<String?>()
    val joke = SingleLiveEvent<String>()

    fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofitResponse =
                        randomJokeApi.getRandomJokeAsync().await()
                //normally I would implement repository interface to hide network logic in it,
                // but I wanted to save some time and app is small as well
                if (retrofitResponse.isSuccessful) {
                    delay(DELAY)
                    joke.postValue(randomJokeApi.getRandomJokeAsync().await().body()?.value?.joke
                            ?: "")
                } else {
                    errorResponse.value =
                            ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
                }
            } catch (e: UnknownHostException) {
                errorResponse.postValue("Can't reach server, please check your internet connection") //normally have all string in values/strings.xml
            }
        }
    }

    companion object {
        const val DELAY = 3000L
    }
}
