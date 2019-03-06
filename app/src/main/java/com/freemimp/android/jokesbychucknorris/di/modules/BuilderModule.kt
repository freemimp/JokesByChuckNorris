package com.freemimp.android.jokesbychucknorris.di.modules

import com.freemimp.android.jokesbychucknorris.MainActivity
import com.freemimp.android.jokesbychucknorris.ui.home.di.HomeFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])

    abstract fun bindMainActivity(): MainActivity
}