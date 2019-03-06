package com.freemimp.android.jokesbychucknorris.di.modules

import com.freemimp.android.jokesbychucknorris.MainActivity
import com.freemimp.android.jokesbychucknorris.ui.home.di.HomeFragmentModule
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.di.ListOfJokesFragmentModule
import com.freemimp.android.jokesbychucknorris.ui.namedjoke.di.NamedJokeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [
        HomeFragmentModule::class,
        NamedJokeFragmentModule::class,
        ListOfJokesFragmentModule::class])

    abstract fun bindMainActivity(): MainActivity
}