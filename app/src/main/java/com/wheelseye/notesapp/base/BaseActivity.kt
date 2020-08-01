package com.wheelseye.notesapp.base

import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), IViewInitializer {

    companion object {
        const val BASE_URL = "https://demo1431226.mockable.io"

        const val SHOW_LOADER = true
        const val HIDE_LOADER = false

        const val USER_DETAILS_SHARED_PREF = "USER_DETAILS_SHARED_PREF"
        const val USER_ID = "USER_ID"
        const val USER_EMAIL_ID = "USER_EMAIL_ID"

        const val USER_NOTES_MODEL_KEY = "USER_NOTES_MODEL_KEY"
        const val SINGLE_NOTE_KEY = "SINGLE_NOTE_KEY"
    }

    fun hideKeyboard() {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }
}