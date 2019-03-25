package com.freemimp.android.jokesbychucknorris.ui.namedjoke

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.jokedialog.JokeDialog
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.utils.Resource
import com.freemimp.android.jokesbychucknorris.utils.hideKeyboard
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.named_joke_fragment.doneButton
import kotlinx.android.synthetic.main.named_joke_fragment.nameEditText
import javax.inject.Inject

class NamedJokeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: NamedJokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NamedJokeViewModel::class.java)
        viewModel.namedJoke.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Joke -> {
                    Log.d(Tag, "showDialog with Joke")
                    showJokeDialog(R.string.random_joke_with_name, resource.joke)
                }
                is Resource.Error -> {
                    resource.error.getContentIfNotHandled()?.let {
                        hideKeyboard()
                        snackbar(it)
                    }
                }
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
        if (nameEditText.text.isNotBlank() && nameEditText.text.toString().isCorrectName()) {
            val name = nameEditText.text.toString()
            viewModel.getNamedRandomJoke(name)
        } else {
            hideKeyboard()
            snackbar("Please enter name as hint shows!")
        }
    }

    private fun String.isCorrectName(): Boolean = this.trim().split(" ").size > 1

    private fun showJokeDialog(@StringRes title: Int, joke: String) {
        JokeDialog.newInstance(title, joke).show(activity?.supportFragmentManager, JokeDialog.Tag)
    }

    companion object {
        val Tag: String = NamedJokeFragment::class.java.simpleName
        fun newInstance() = NamedJokeFragment()
    }
}

