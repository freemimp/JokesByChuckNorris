package com.freemimp.android.jokesbychucknorris.ui.listofjokes

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.mvvm.ViewModelFactory
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import com.freemimp.android.jokesbychucknorris.ui.listofjokes.recyclerview.JokeAdapter
import com.freemimp.android.jokesbychucknorris.utils.snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.list_of_jokes_fragment.jokesRecyclerView
import javax.inject.Inject

class ListOfJokesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val recyclerViewAdapter = JokeAdapter()

    private lateinit var viewModel: ListOfJokesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.list_of_jokes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ListOfJokesViewModel::class.java)
        viewModel.getError().observe(this, Observer { errorResponse ->
            errorResponse?.let {
                snackbar(errorResponse)
            }
        })
        setupRecyclerView()
        viewModel.jokesList.observe(this, Observer { pagedJokesList ->
            pagedJokesList?.let {
                present(pagedJokesList)
            }
        })
    }

    private fun present(pagedJokesList: PagedList<Value>) {
        recyclerViewAdapter.submitList(pagedJokesList)
    }

    private fun setupRecyclerView() {
        jokesRecyclerView.layoutManager = LinearLayoutManager(this.context)
        jokesRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        jokesRecyclerView.adapter = recyclerViewAdapter
    }

    companion object {
        val Tag: String = ListOfJokesFragment::class.java.simpleName
        fun newInstance() = ListOfJokesFragment()
    }

}


