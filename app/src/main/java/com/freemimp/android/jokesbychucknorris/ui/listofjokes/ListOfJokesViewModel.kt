package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesController
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import javax.inject.Inject

class ListOfJokesViewModel @Inject constructor(private val listOfJokesController: ListOfJokesController) : ViewModel() {

    var errorResponse = MutableLiveData<String?>()

    suspend fun getListOfRandomJokes(): List<Value>? {
        var response: List<Value>? = emptyList<Value>()
        val retrofitResponse = listOfJokesController.getListOfJokes().await()
        if (retrofitResponse.isSuccessful) {
            response = retrofitResponse.body()?.value
        } else {
            errorResponse.value = ("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
        }
        return response
    }
}
