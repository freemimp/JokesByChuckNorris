package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.jokedialog.JokeDialog
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.ListOfJokesFragment
import com.freemimp.android.jokesbychucknorris.ui.namedjoke.NamedJokeFragment
import com.freemimp.android.jokesbychucknorris.utils.replaceFragment
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.listOfJokesButton
import kotlinx.android.synthetic.main.home_fragment.randomJokeButton
import kotlinx.android.synthetic.main.home_fragment.randomJokeWithNameButton
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.getError().observe(this, Observer { errorResponse ->
            errorResponse?.let {
                snackbar(errorResponse)
            }
        })

        viewModel.showRandomJoke().observe(this, Observer { joke ->
            joke?.let {
                showJokeDialog(R.string.random_joke, joke)
            }
        })
        randomJokeButton.setOnClickListener { showRandomJoke() }
        randomJokeWithNameButton.setOnClickListener { goToNamedJokeFragment() }
        listOfJokesButton.setOnClickListener { goToListOfInfiniteJokes() }
    }

    private fun showRandomJoke() {
        viewModel.getRandomJoke()
    }


    private fun goToNamedJokeFragment() {
        replaceFragment(R.id.container, NamedJokeFragment.newInstance(), NamedJokeFragment.Tag)
    }

    private fun goToListOfInfiniteJokes() {
        replaceFragment(R.id.container, ListOfJokesFragment.newInstance(), ListOfJokesFragment.Tag)
    }

    private fun showJokeDialog(@StringRes title: Int, joke: String) {
        JokeDialog.newInstance(title, joke).show(activity?.supportFragmentManager, JokeDialog.Tag)
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}
