package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.AppComponentFactory
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ListOfJokesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ListOfJokesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_of_jokes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListOfJokesViewModel::class.java)
    }

    companion object {
        val Tag: String = ListOfJokesFragment::class.java.simpleName
        fun newInstance() = ListOfJokesFragment()
    }

}
