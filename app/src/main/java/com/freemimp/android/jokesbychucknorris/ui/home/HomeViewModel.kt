package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.fetchRandomJoke()
        }
    }

    fun showRandomJoke(): SingleLiveEvent<String> {
        return repository.joke
    }

    fun getError(): SingleLiveEvent<String> {
        return repository.errorResponse
    }
}
