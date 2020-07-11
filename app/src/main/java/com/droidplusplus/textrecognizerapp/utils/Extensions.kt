package com.droidplusplus.textrecognizerapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * View Extension function
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * Toast Extension function
 * */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}