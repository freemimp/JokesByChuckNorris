package com.freemimp.android.jokesbychucknorris.ui.home.di

import com.freemimp.android.jokesbychucknorris.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    abstract fun findHomeFragment(): HomeFragment

}