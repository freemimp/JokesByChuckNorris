package com.freemimp.android.jokesbychucknorris.ui.listofjokes.recyclerview

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.restapi.request.Value

class JokeAdapter : PagedListAdapter<Value, JokesViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        return JokesViewHolder(parent)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        val value = getItem(position)

        with(holder) {
            bindTo(value)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Value>() {
            override fun areItemsTheSame(oldItem: Value, newItem: Value): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Value, newItem: Value): Boolean =
                    oldItem == newItem
        }
    }

}