package com.freemimp.android.jokesbychucknorris.di.modules

import android.content.Context
import com.freemimp.android.jokesbychucknorris.JokesApp
import com.freemimp.android.jokesbychucknorris.di.annotations.AppContext
import com.freemimp.android.jokesbychucknorris.repository.Repository
import com.freemimp.android.jokesbychucknorris.repository.RepositoryImpl
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesApi
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeApi
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
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
    fun provideJokeDataSource(repository: Repository): JokeDataSource {
        return JokeDataSource(repository)
    }

    @Provides
    @Singleton
    fun provideRepository(listOfJokesApi: ListOfJokesApi, randomJokeApi: RandomJokeApi, namedRandomJokeApi: NamedRandomJokeApi): Repository {
        return RepositoryImpl(listOfJokesApi, randomJokeApi, namedRandomJokeApi)
    }

}
