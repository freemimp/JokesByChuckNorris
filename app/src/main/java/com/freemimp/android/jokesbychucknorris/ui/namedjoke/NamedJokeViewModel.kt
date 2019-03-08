package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeApi
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class NamedJokeViewModel @Inject constructor(private val namedRandomJokeApi: NamedRandomJokeApi) : ViewModel() {

    val errorResponse = SingleLiveEvent<String>()
    val joke = SingleLiveEvent<String>()

    fun getNamedRandomJoke(name: String) {
        val firstName = name.trim().substringBeforeLast(" ").capitalize()
        val lastName = name.trim().substringAfterLast(" ").capitalize()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val retrofitResponse = namedRandomJokeApi.getRandomNamedJokeAsync(firstName, lastName).await()
                //normally I would implement repository interface to hide network logic in it,
                // but I wanted to save some time and app is small as well
                if (retrofitResponse.isSuccessful) {
                    joke.postValue(retrofitResponse.body()?.value?.joke ?: "")
                } else {
                    errorResponse.postValue("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
                }
            } catch (e: UnknownHostException) {
                errorResponse.postValue("Can't reach server, please check your internet connection")

            }
        }
    }

}
