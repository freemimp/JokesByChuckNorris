package com.freemimp.android.jokesbychucknorris.ui.home

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.jokedialog.JokeDialog
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.home_fragment.randomJokeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomJokeButton.setOnClickListener { showRandomJoke() }
    }

    private fun showRandomJoke() {
        CoroutineScope(IO).launch {
            val joke = viewModel.getRandomJoke()
            delay(3000)

            withContext(Main) {
                showJokeDialog(R.string.random_joke, joke)
            }
        }
    }

    private fun showJokeDialog(@StringRes title: Int, joke: String) {
        JokeDialog.newInstance(title, joke).show(activity?.supportFragmentManager, JokeDialog.Tag)
    }


    companion object {
        fun newInstance() = HomeFragment()
    }

}
