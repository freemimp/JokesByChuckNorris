package com.freemimp.android.jokesbychucknorris.ui.listofjokes.pagination

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import javax.inject.Inject

class JokesDataSourceFactory @Inject constructor(
        private val dataSource: JokeDataSource
) : DataSource.Factory<Int, Value>() {

    private val jokesLiveData = MutableLiveData<JokeDataSource>()

    override fun create(): DataSource<Int, Value> {
        jokesLiveData.postValue(dataSource)
        return dataSource
    }
}