package com.freemimp.android.jokesbychucknorris.ui.listofjokes.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.restapi.request.Value
import kotlinx.android.synthetic.main.joke_item_layout.view.itemLoadingBar
import kotlinx.android.synthetic.main.joke_item_layout.view.jokeTextView

class JokesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.joke_item_layout, parent, false)
) {
    private var value: Value? = null

    fun bindTo(value: Value?) {
        this.value = value

        if (value == null) {
            itemView.itemLoadingBar.visibility = VISIBLE
            itemView.jokeTextView.visibility = GONE
        } else {
            itemView.itemLoadingBar.visibility = GONE
            itemView.jokeTextView.text = value.joke
        }
    }
}