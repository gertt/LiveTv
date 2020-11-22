package com.gprifti.livetv.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBar {

    companion object {
        fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
            Snackbar.make(this, message, duration).show()
        }
    }
}