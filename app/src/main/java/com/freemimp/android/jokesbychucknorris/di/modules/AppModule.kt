package com.freemimp.android.jokesbychucknorris.di.modules

import android.content.Context
import com.freemimp.android.jokesbychucknorris.JokesApp
import com.freemimp.android.jokesbychucknorris.di.annotations.AppContext
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesApi
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination.JokeDataSource
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination.JokesDataSourceFactory
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
    fun providesJokesDataFactory(jokesDataSource: JokeDataSource): JokesDataSourceFactory {
        return JokesDataSourceFactory(jokesDataSource)
    }

    @Provides
    fun provideJokeDataSource(listOfJokesController: ListOfJokesApi): JokeDataSource {
        return JokeDataSource(listOfJokesController)
    }

}
