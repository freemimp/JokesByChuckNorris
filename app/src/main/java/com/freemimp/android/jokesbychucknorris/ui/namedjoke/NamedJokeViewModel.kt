package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.restapi.request.JokeApiResponse
import com.freemimp.android.jokesbychucknorris.utils.Event
import com.freemimp.android.jokesbychucknorris.utils.Resource
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NamedJokeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _namedJoke = MediatorLiveData<Resource>()
    val namedJoke: LiveData<Resource> = _namedJoke

    fun getNamedRandomJoke(name: String) {
        val firstName = name.trim().substringBeforeLast(" ").capitalize()
        val lastName = name.trim().substringAfterLast(" ").capitalize()
        CoroutineScope(Dispatchers.IO).launch {
            val source = MutableLiveData<JokeApiResponse>()
            source.postValue(repository.fetchRandomNamedJoke(firstName, lastName))

            val mappedResponse = Transformations.map(source, object : Function<JokeApiResponse, Resource> {
                override fun apply(response: JokeApiResponse): Resource {
                    return if (response.joke.isNullOrBlank()) {
                        Resource.Error(Event(response.error ?: "Unknown Error"))
                    } else {
                        Resource.Joke(response.joke)
                    }
                }
            })
            _namedJoke.removeSource(source)

            withContext(Dispatchers.Main) {
                _namedJoke.addSource(mappedResponse) {
                    _namedJoke.value = it
                }
            }
        }
    }
}
