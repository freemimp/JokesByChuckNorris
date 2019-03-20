package com.freemimp.android.jokesbychucknorris.repository

import com.freemimp.android.jokesbychucknorris.restapi.request.JokeApiResponse
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent

interface Repository {

    val errorResponse: SingleLiveEvent<String>

    suspend fun fetchListOfJokes(): List<Value>
    suspend fun fetchRandomJoke(): JokeApiResponse
    suspend fun fetchRandomNamedJoke(firstName: String, lastName: String): JokeApiResponse
    suspend fun fetchTotalCountOfJokes(): Int
}