package com.freemimp.android.jokesbychucknorris.restapi

import com.freemimp.android.jokesbychucknorris.restapi.request.RandomJokeModel
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RandomJokeController {

    @GET("random/")
    fun getRandomJoke(): Deferred<Value>
}