package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination.JokeDataSource
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination.JokesDataSourceFactory
import com.freemimp.android.jokesbychucknorris.utils.SingleLiveEvent
import javax.inject.Inject

class ListOfJokesViewModel @Inject constructor(private val jokesDataSourceFactory: JokesDataSourceFactory, jokeDataSource: JokeDataSource)
    : ViewModel() {

    private val pageListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
            .setPageSize(PAGE_SIZE)
            .build()

    val jokesList =
            LivePagedListBuilder<Int, Value>(jokesDataSourceFactory, pageListConfig).build()


    fun getError(): SingleLiveEvent<String> {
        return jokesDataSourceFactory.dataSource.errorResponse
    }

    companion object {
        const val INITIAL_LOAD_SIZE = 12
        const val PAGE_SIZE = 12
    }
}
