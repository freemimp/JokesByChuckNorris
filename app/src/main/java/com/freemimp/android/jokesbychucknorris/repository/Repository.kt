package com.freemimp.android.jokesbychucknorris.repository

import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent

interface Repository {

    val errorResponse: SingleLiveEvent<String>
    val joke: SingleLiveEvent<String>

    suspend fun fetchListOfJokes(): List<Value>
    suspend fun fetchRandomJoke()
    suspend fun fetchRandomNamedJoke(firstName: String, lastName: String)
    suspend fun fetchTotalCountOfJokes(): Int
}