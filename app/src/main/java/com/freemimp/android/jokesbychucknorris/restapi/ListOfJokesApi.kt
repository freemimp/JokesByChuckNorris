package com.freemimp.android.jokesbychucknorris.restapi

import com.freemimp.android.jokesbychucknorris.restapi.request.JokeCountModel
import com.freemimp.android.jokesbychucknorris.restapi.request.RandomJokeListModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ListOfJokesApi {

    @GET("random/12")
    fun getListOfJokesAsync(): Deferred<Response<RandomJokeListModel>>

    @GET("count")
    fun getTotalCountOfJokesAsync():Deferred<JokeCountModel>
}