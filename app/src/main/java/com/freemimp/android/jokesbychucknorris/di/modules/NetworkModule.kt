package com.freemimp.android.jokesbychucknorris.di.modules

import android.content.Context
import android.util.Log
import com.freemimp.android.jokesbychucknorris.BuildConfig
import com.freemimp.android.jokesbychucknorris.di.annotations.AppContext
import com.freemimp.android.jokesbychucknorris.restapi.ListOfJokesController
import com.freemimp.android.jokesbychucknorris.restapi.NamedRandomJokeController
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeController
import com.freemimp.android.jokesbychucknorris.utils.Constants
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson, okHttpClient: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.apiEndppoint)
                .callFactory { okHttpClient.get().newCall(it) }
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    fun provideClient(cache: Cache, httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun providesHttpCache(@AppContext context: Context): Cache {

        val cacheFile = File(context.cacheDir, "httpCache")
        return Cache(cacheFile, 10 * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("NetworkModule", message) }).apply {
            this.level = when (BuildConfig.DEBUG) {
                true -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun providesRandomJokeController(retrofit: Retrofit): RandomJokeController = retrofit.create(RandomJokeController::class.java)

    @Provides
    fun providesNamedRandomJokeController(retrofit: Retrofit): NamedRandomJokeController = retrofit.create(NamedRandomJokeController::class.java)

    @Provides
    fun providesListOfJokesController(retrofit: Retrofit): ListOfJokesController = retrofit.create(ListOfJokesController::class.java)
}