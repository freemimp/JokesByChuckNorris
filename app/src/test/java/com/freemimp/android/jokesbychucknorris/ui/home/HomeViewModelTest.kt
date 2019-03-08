package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.freemimp.android.jokesbychucknorris.restapi.RandomJokeApi
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class HomeViewModelTest {
// normally would have BaseViewModelTest class that all ViewModelTest classes extend, which contains
// all common stuff used in all ViewModelTest classes
    private val errorResponseBody: ResponseBody = ResponseBody.create(MediaType.parse("text/plain"), "Mock response")

    @Mock
    lateinit var randomJokeApi: RandomJokeApi

    @Mock
    lateinit var retrofitCall: Deferred<Void>

    lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(randomJokeApi)
    }

    //Normally with RMG policy I strive for for overall code coverage with Jacoco - UI + Unit tests >70%
    @Test
    fun givenServerReturnsErrorWhenGettingDataThenErrorIsPosted() {
        //Given
        whenever(runBlocking { retrofitCall.await() }).then { Response.error<String>(400, errorResponseBody) }
        whenever(randomJokeApi.getRandomJokeAsync()).then { retrofitCall }

        //When
        homeViewModel.getRandomJoke()
        val error = homeViewModel.errorResponse.value


        //Then
        assert(homeViewModel.joke.value.isNullOrBlank())
        assert(error == homeViewModel.errorResponse.value) { "$error is not the same as ${homeViewModel.errorResponse.value}" }
    }
}