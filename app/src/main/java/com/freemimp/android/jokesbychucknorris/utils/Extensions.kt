package com.freemimp.android.jokesbychucknorris.utils

import android.app.Activity
import android.content.Context
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.freemimp.android.jokesbychucknorris.jokedialog.JokeDialog

fun Fragment.showJokeDialog(@StringRes title: Int, joke: String) {
    JokeDialog.newInstance(title, joke).show(activity?.supportFragmentManager, JokeDialog.Tag)
}

fun Fragment.snackbar(msg: String, view: View? = activity?.findViewById(android.R.id.content)) {
    view?.let {
        hideKeyboard()
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.toast(text: String) = activity?.let {  Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show() }

fun Fragment.replaceFragment(viewGroupId: Int, fragment: Fragment, tag: String) {
    activity?.supportFragmentManager?.beginTransaction()
            ?.replace(viewGroupId, fragment)
            ?.addToBackStack(tag)
            ?.commit()
}

fun AppCompatActivity.replaceFragment(viewGroupId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction()
            .replace(viewGroupId, fragment)
            .commit()
}

fun Fragment.hideKeyboard(focusView: View? = null) {
    val view = focusView ?: activity?.currentFocus
    view?.let {
        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun String.isCorrectName(): Boolean = this.trim().split(" ").size > 1
