package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NamedJokeViewModel @Inject constructor(private val namedRandomJokeApi: NamedRandomJokeApi) : ViewModel() {

    var errorResponse = MutableLiveData<String>()

    suspend fun getNamedRandomJoke(name: String): String {
        val firstName = name.trim().substringBeforeLast(" ").capitalize()
        val lastName = name.trim().substringAfterLast(" ").capitalize()
        var response = ""
        val retrofitResponse = namedRandomJokeApi.getRandomNamedJokeAsync(firstName, lastName).await()
        if (retrofitResponse.isSuccessful) {
            response = retrofitResponse.body()?.value?.joke ?: ""
        } else {
            withContext(Dispatchers.Main) {
                errorResponse.value = ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
            }
        }
        return response
    }

}
