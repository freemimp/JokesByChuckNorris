package com.freemimp.android.jokesbychucknorris.repository

import android.util.Log
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesApi
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeApi
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
import com.freemimp.android.jokesbychucknorris.restapi.request.JokeApiResponse
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.delay
import java.net.UnknownHostException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val listOfJokesApi: ListOfJokesApi,
                                         private val randomJokeApi: RandomJokeApi,
                                         private val namedRandomJokeApi: NamedRandomJokeApi)
    : Repository {
    override val errorResponse = SingleLiveEvent<String>()
    override val joke = SingleLiveEvent<String>()
    override val namedJoke = SingleLiveEvent<String>()


    override suspend fun fetchListOfJokes(): List<Value> {
        val listOfJokeValues = mutableListOf<Value>()
        try {

            val retrofitResponse = listOfJokesApi.getListOfJokesAsync().await()
            if (retrofitResponse.isSuccessful) {
                listOfJokeValues.addAll(listOfJokesApi.getListOfJokesAsync().await().body()?.value?.toMutableList()
                        ?: mutableListOf())
            } else {
                errorResponse.postValue("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")

            }

        } catch (e: UnknownHostException) {
            errorResponse.postValue("Can't reach server, please check your internet connection ")

        }

        return listOfJokeValues
    }


    override suspend fun fetchRandomJoke(): JokeApiResponse {
        return try {
             val retrofitResponse = randomJokeApi.getRandomJokeAsync().await()

             if (retrofitResponse.isSuccessful) {
                 delay(DELAY)
                 JokeApiResponse(randomJokeApi.getRandomJokeAsync().await().body()?.value?.joke, null)
             } else {
                 JokeApiResponse(null, "Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
             }
         } catch (e: UnknownHostException) {
             JokeApiResponse(null, "Can't reach server, please check your internet connection ")
         }
    }

    override suspend fun fetchRandomNamedJoke(firstName: String, lastName: String) {
        try {
            val retrofitResponse = namedRandomJokeApi.getRandomNamedJokeAsync(firstName, lastName).await()

            if (retrofitResponse.isSuccessful) {

                namedJoke.postValue(namedRandomJokeApi
                        .getRandomNamedJokeAsync(firstName, lastName).await().body()?.value?.joke
                        ?: "")
            } else {
                errorResponse
                        .postValue("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
            }
        } catch (e: UnknownHostException) {
            errorResponse
                    .postValue("Can't reach server, please check your internet connection ")
        }
    }

    override suspend fun fetchTotalCountOfJokes(): Int {
        var totalNumberOfJokes = 0
        try {
            val retrofitResponse = listOfJokesApi.getTotalCountOfJokesAsync().await()
            if (retrofitResponse.isSuccessful) {
                totalNumberOfJokes = listOfJokesApi.getTotalCountOfJokesAsync().await().body()?.value
                        ?: 1
            } else {
                Log.e(Tag, "Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")
            }

        } catch (e: UnknownHostException) {
            Log.e(Tag, "${e.message}")
        }

        return totalNumberOfJokes
    }

    companion object {
        val Tag: String = RepositoryImpl::class.java.simpleName
        const val DELAY = 3000L
    }
}