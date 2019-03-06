package com.freemimp.android.jokesbychucknorris.restapi

import com.freemimp.android.jokesbychucknorris.restapi.request.RandomJokeListModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ListOfJokesController {

    @GET("random/12")
    fun getListOfJokes(): Deferred<Response<RandomJokeListModel>>
}