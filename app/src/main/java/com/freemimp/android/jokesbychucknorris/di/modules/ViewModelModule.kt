package com.freemimp.android.jokesbychucknorris.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.freemimp.android.jokesbychucknorris.di.annotations.ViewModelKey
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.ui.home.HomeViewModel
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.ListOfJokesViewModel
import com.freemimp.android.jokesbychucknorris.ui.namedjoke.NamedJokeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(NamedJokeViewModel::class)
    abstract fun bindNamedJokeViewModel(namedJokeViewModel: NamedJokeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(ListOfJokesViewModel::class)
    abstract fun bindListOfJokesViewModel(listOfJokesViewModel: ListOfJokesViewModel): ViewModel
}