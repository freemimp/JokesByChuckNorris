package com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesApi
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class JokeDataSource @Inject constructor(private val listOfJokesApi: ListOfJokesApi) : ItemKeyedDataSource<Int, Value>() {

    val errorResponse = SingleLiveEvent<String>()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Value>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jokes = mutableListOf<Value>()
                val retrofitResponse = listOfJokesApi.getListOfJokesAsync().await()
                if (retrofitResponse.isSuccessful) {
                    jokes.addAll(listOfJokesApi.getListOfJokesAsync().await().body()?.value?.toMutableList()
                            ?: mutableListOf())
                } else {
                    errorResponse.postValue("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")

                }
                val totalJokesCount = listOfJokesApi.getTotalCountOfJokesAsync().await().value //this is total count of the jokes at the minute, once we implement logic to not allow duplicates, this list will become finite and will be response driven
                callback.onResult(jokes, 0, totalJokesCount)
            } catch (e: UnknownHostException) {
                errorResponse.postValue("Can't reach server, please check your internet connection")

            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Value>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jokes = mutableListOf<Value>()
                val retrofitResponse = listOfJokesApi.getListOfJokesAsync().await()
                if (retrofitResponse.isSuccessful) {
                    jokes.addAll(listOfJokesApi.getListOfJokesAsync().await().body()?.value?.toMutableList()
                            ?: mutableListOf())
                } else {
                    errorResponse.postValue("Server error:${retrofitResponse.code()}, ${retrofitResponse.errorBody().toString()}")

                }
                callback.onResult(jokes)
            } catch (e: Exception) {
                errorResponse.postValue("Can't reach server, please check your internet connection")

            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Value>) {
    }

    override fun getKey(item: Value): Int = item.id
}