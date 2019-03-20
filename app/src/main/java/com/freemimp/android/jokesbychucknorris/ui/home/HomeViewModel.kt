package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.restapi.request.JokeApiResponse
import com.freemimp.android.jokesbychucknorris.utils.Event
import com.freemimp.android.jokesbychucknorris.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _joke = MediatorLiveData<Resource>()
    val joke: LiveData<Resource> = _joke

    private var _randomJokeSource: LiveData<JokeApiResponse>? = null

    fun getRandomJoke() {
        _randomJokeSource = null
        CoroutineScope(Dispatchers.IO).launch {
            val source: LiveData<JokeApiResponse> = _randomJokeSource ?: repository.fetchRandomJoke()

            val mappedResponse = Transformations.map(source, object : Function<JokeApiResponse, Resource> {
                override fun apply(response: JokeApiResponse): Resource {
                    return if (response.joke.isNullOrBlank()) {
                        Resource.Error(Event(response.error ?: ""))
                    } else {
                        Resource.Joke(response.joke)
                    }
                }
            })
            _joke.removeSource(source)

            _randomJokeSource = source

            withContext(Dispatchers.Main) {
                _joke.addSource(mappedResponse) {
                    _joke.value = it
                }
            }
        }
    }
}

