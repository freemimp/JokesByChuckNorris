package com.freemimp.android.jokesbychucknorris.ui.home

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import kotlinx.android.synthetic.main.home_fragment.randomJokeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }
    @Inject
    lateinit var viewModel: HomeViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomJokeButton.setOnClickListener { showRandomJoke()}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun showRandomJoke() {
         CoroutineScope(IO).launch {
          val joke =  viewModel.getRandomJoke()
             delay(3000)

             withContext(Main) {
                 val dialog = AlertDialog
                         .Builder(activity)
                         .setTitle("Random Joke")
                         .setMessage(joke).create()


             }
        }
    }

}
