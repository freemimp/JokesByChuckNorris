package com.freemimp.android.jokesbychucknorris

import android.app.Activity
import android.app.Application
import com.freemimp.android.jokesbychucknorris.di.component.AppComponent
import com.freemimp.android.jokesbychucknorris.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class JokesApp : Application(), HasActivityInjector {

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    lateinit var component: AppComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initDaggerComponent()
    }

    private fun initDaggerComponent() {
        component = DaggerAppComponent
                .builder()
                .application(this)
                .build()

        component.inject(this)
    }
}