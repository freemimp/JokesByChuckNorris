package com.freemimp.android.jokesbychucknorris.jokedialog

import android.app.Dialog
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class JokeDialog : DialogFragment() {

    private var title: Int = 0
    private var message: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = savedInstanceState ?: arguments
        bundle?.let {
            title = it.getInt(TITLE_KEY)
            message = it.getString(MESSAGE_KEY) ?: "No Joke"
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Dismiss") { _, _ ->
                        dismissAllowingStateLoss()
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        val Tag: String = JokeDialog::class.java.simpleName
        private val TITLE_KEY = "$Tag.title.key"
        private val MESSAGE_KEY = "$Tag.message.key"

        @JvmStatic
        fun newInstance(@StringRes title: Int, message: String): JokeDialog {
            return JokeDialog().apply {
                arguments = Bundle().apply {
                    this.putInt(TITLE_KEY, title)
                    this.putString(MESSAGE_KEY, message)
                }
            }
        }
    }
}