package com.freemimp.android.jokesbychucknorris.utils

sealed class Resource {
    class Joke(val joke: String) : Resource()
    class Error(val error: Event<String>) : Resource()
}