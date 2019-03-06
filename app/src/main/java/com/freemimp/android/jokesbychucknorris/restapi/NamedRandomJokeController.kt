package com.freemimp.android.jokesbychucknorris.restapi

import com.freemimp.android.jokesbychucknorris.restapi.request.RandomJokeModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NamedRandomJokeController {

    @GET("random/")
    fun getRandomNamedJokeAsync(@Query("firstName") name: String,
                                @Query("lastName") lastName: String): Deferred<Response<RandomJokeModel>>
}