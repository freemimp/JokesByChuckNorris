package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeApi
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class NamedJokeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val joke = repository.namedJoke
    val errorResponse = repository.errorResponse

    fun getNamedRandomJoke(name: String) {
        val firstName = name.trim().substringBeforeLast(" ").capitalize()
        val lastName = name.trim().substringAfterLast(" ").capitalize()
        CoroutineScope(Dispatchers.IO).launch {
          repository.fetchRandomNamedJoke(firstName, lastName)
        }
    }

    fun showRandomNamedJoke(): SingleLiveEvent<String> {
        return repository.namedJoke
    }

    fun getError(): SingleLiveEvent<String> {
        return repository.errorResponse
    }

}
