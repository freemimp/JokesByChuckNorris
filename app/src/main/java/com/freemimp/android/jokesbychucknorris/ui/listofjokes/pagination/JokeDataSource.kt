package com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination

import android.arch.paging.ItemKeyedDataSource
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeDataSource @Inject constructor(private val repository: Repository) : ItemKeyedDataSource<Int, Value>() {


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Value>) {
        CoroutineScope(Dispatchers.IO).launch {
            val jokes = repository.fetchListOfJokes()
            val totalJokesCount = repository.fetchTotalCountOfJokes()
            callback.onResult(jokes, 0, totalJokesCount)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Value>) {
        CoroutineScope(Dispatchers.IO).launch {
            val jokes = repository.fetchListOfJokes()
            callback.onResult(jokes)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Value>) {
    }

    override fun getKey(item: Value): Int = item.id

}