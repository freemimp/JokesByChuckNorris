package com.freemimp.android.jokesbychucknorris.di.modules

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.freemimp.android.jokesbychucknorris.JokesApp
import com.freemimp.android.jokesbychucknorris.di.annotations.AppContext
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeController
import com.freemimp.android.jokesbychucknorris.ui.home.HomeViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    @AppContext
    fun providesAppContext(jokesApp: JokesApp): Context = jokesApp

    @Provides
    fun providesViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory = viewModelFactory
}
