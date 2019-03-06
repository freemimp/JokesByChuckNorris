package com.freemimp.android.jokesbychucknorris

import android.os.Bundle
import com.freemimp.android.jokesbychucknorris.ui.home.HomeFragment
import com.freemimp.android.jokesbychucknorris.utils.replaceFragment
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
           replaceFragment(R.id.container, HomeFragment.newInstance())
        }
    }

}
