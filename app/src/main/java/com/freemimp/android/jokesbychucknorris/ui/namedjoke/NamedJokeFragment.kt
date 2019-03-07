package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.utils.isCorrectName
import com.freemimp.android.jokesbychucknorris.utils.showJokeDialog
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.named_joke_fragment.doneButton
import kotlinx.android.synthetic.main.named_joke_fragment.nameEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import javax.inject.Inject

class NamedJokeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NamedJokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NamedJokeViewModel::class.java)
        viewModel.errorResponse.observe(this, Observer { errorResponse ->
            errorResponse?.let {
                snackbar(errorResponse)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.named_joke_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doneButton.setOnClickListener { showRandomNamedJoke() }

    }

    private fun showRandomNamedJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            if (nameEditText.toString().isNotBlank() && nameEditText.text.toString().isCorrectName()) {
                val name = nameEditText.text.toString()
                try {
                    val joke = viewModel.getNamedRandomJoke(name)
                    withContext(Dispatchers.Main) {
                        showJokeDialog(R.string.random_joke_with_name, joke)
                    }
                } catch (e: UnknownHostException) {
                    snackbar("Can't reach server, please check your internet connection")
                }
            } else {
                snackbar("Please enter name as hint shows!")
            }
        }
    }

    companion object {
        val Tag: String = NamedJokeFragment::class.java.simpleName

        fun newInstance() = NamedJokeFragment()
    }

}
