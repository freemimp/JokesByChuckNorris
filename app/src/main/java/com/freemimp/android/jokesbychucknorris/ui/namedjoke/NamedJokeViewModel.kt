package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeController
import javax.inject.Inject

class NamedJokeViewModel @Inject constructor(private val namedRandomJokeController: NamedRandomJokeController) : ViewModel() {

    var errorResponse = MutableLiveData<String?>()

    suspend fun getNamedRandomJoke(name: String): String {
        val firstName = name.trim().substringBeforeLast(" ").capitalize()
        val lastName = name.trim().substringAfterLast(" ").capitalize()
        var response = ""
        val retrofitResponse = namedRandomJokeController.getRandomNamedJokeAsync(firstName, lastName).await()
        if (retrofitResponse.isSuccessful) {
            response = retrofitResponse.body()?.value?.joke ?: ""
        } else {
            errorResponse.value = ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
        }
        return response
    }

}
