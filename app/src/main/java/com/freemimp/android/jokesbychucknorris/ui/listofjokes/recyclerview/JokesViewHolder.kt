package com.freemimp.android.jokesbychucknorris.ui.listofjokes.recyclerview

import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.freemimp.android.jokesbychucknorris.R
import com.freemimp.android.jokesbychucknorris.restapi.request.Value

class JokesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.joke_item_layout, parent, false)
) {
    private val jokeTextView = itemView.findViewById<TextView>(R.id.jokeTextView)
    private val loading = itemView.findViewById<LinearLayout>(R.id.itemLoadingBar)
    private var value: Value? = null

    fun bindTo(value: Value?) {
        this.value = value

        if(value == null) {
            loading.visibility = VISIBLE
            jokeTextView.visibility = GONE
        } else {
            loading.visibility = GONE
            jokeTextView.text = value.joke

        }
    }
}