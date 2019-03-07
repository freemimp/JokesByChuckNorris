package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination.JokesDataSourceFactory
import javax.inject.Inject

class ListOfJokesViewModel @Inject constructor(jokesDataSourceFactory: JokesDataSourceFactory)
    : ViewModel() {

    var errorResponse = MutableLiveData<String?>()


    private val pageListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(12)
            .setPageSize(6)
            .build()

    val jokesList = LivePagedListBuilder<Int, Value>(jokesDataSourceFactory, pageListConfig).build()

}
