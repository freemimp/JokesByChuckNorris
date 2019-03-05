package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeController
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val randomJokeController: RandomJokeController) : ViewModel() {

    suspend fun getRandomJoke(): String {
        return randomJokeController.getRandomJoke().await().joke
    }
}
