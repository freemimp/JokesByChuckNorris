package com.freemimp.android.jokesbychucknorris.ui.listofjokes.di

import com.freemimp.android.jokesbychucknorris.ui.listofjokes.ListOfJokesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListOfJokesFragmentModule {
    @ContributesAndroidInjector
    abstract fun listOfJokesFragment(): ListOfJokesFragment
}