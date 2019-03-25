package com.freemimp.android.jokesbychucknorris.restapi

import com.freemimp.android.jokesbychucknorris.restapi.request.RandomJokeModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RandomJokeApi {

    @GET("random/")
    fun getRandomJokeAsync(): Deferred<Response<RandomJokeModel>>
}