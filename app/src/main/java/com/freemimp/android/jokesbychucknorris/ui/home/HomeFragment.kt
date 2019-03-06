package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.ui.namedjoke.NamedJokeFragment
import com.freemimp.android.jokesbychucknorris.utils.replaceFragment
import com.freemimp.android.jokesbychucknorris.utils.showJokeDialog
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import com.freemimp.android.jokesbychucknorris.utils.toast
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.listOfJokesButton
import kotlinx.android.synthetic.main.home_fragment.randomJokeButton
import kotlinx.android.synthetic.main.home_fragment.randomJokeWithNameButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel.errorResponse.observe(this, Observer { errorResponse ->
            errorResponse?.let {
                snackbar(errorResponse)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomJokeButton.setOnClickListener { showRandomJoke() }
        randomJokeWithNameButton.setOnClickListener { goToNamedJokeFragment() }
        listOfJokesButton.setOnClickListener { goToListOfInfiniteJokes() }
    }

    private fun showRandomJoke() {
        CoroutineScope(IO).launch {
            try {
                val joke = viewModel.getRandomJoke()
                delay(3000)
                withContext(Main) {
                    showJokeDialog(R.string.random_joke, joke)
                }
            } catch (e : UnknownHostException) {
                snackbar("Can't reach server, please check your internet connection")
            }
        }
    }


    private fun goToNamedJokeFragment() {
        replaceFragment(R.id.container, NamedJokeFragment.newInstance(), NamedJokeFragment.Tag)
    }

    private fun goToListOfInfiniteJokes() {
        toast("list is opened")
    }

    companion object {
        val Tag: String = HomeFragment::class.java.simpleName
        fun newInstance() = HomeFragment()
    }

}
