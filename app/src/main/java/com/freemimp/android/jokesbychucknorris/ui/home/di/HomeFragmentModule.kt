package com.freemimp.android.jokesbychucknorris.ui.home.di

import android.arch.lifecycle.ViewModel
import com.freemimp.android.jokesbychucknorris.di.annotations.ViewModelKey
import com.freemimp.android.jokesbychucknorris.ui.home.HomeFragment
import com.freemimp.android.jokesbychucknorris.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentModule {
    @ContributesAndroidInjector
    abstract fun findHomeFragment(): HomeFragment

}