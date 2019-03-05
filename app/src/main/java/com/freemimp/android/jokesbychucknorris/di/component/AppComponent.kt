package com.freemimp.android.jokesbychucknorris.di.component

import com.freemimp.android.jokesbychucknorris.JokesApp
import com.freemimp.android.jokesbychucknorris.di.modules.AppModule
import com.freemimp.android.jokesbychucknorris.di.modules.BuilderModule
import com.freemimp.android.jokesbychucknorris.di.modules.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, AppModule::class, BuilderModule::class,NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(jokesApp: JokesApp): Builder
        fun build(): AppComponent
    }

    fun inject(jokesApp: JokesApp)
}