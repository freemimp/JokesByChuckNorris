package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.AppComponentFactory
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_of_jokes_fragment.testTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ListOfJokesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ListOfJokesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListOfJokesViewModel::class.java)
        viewModel.errorResponse.observe(this, Observer { errorResponse ->
            errorResponse?.let {
                snackbar(errorResponse)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_of_jokes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch { val listOfJokes = viewModel.getListOfRandomJokes()
        withContext(Dispatchers.Main) {
            testTextView.text = listOfJokes.toString()
        }
        }

    }

    companion object {
        val Tag: String = ListOfJokesFragment::class.java.simpleName
        fun newInstance() = ListOfJokesFragment()
    }

}
