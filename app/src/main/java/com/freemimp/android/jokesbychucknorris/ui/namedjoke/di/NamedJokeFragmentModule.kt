package com.freemimp.android.jokesbychucknorris.ui.namedjoke.di

import com.freemimp.android.jokesbychucknorris.ui.namedjoke.NamedJokeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NamedJokeFragmentModule {
    @ContributesAndroidInjector
    abstract fun bindNamedJokeFragment(): NamedJokeFragment
}