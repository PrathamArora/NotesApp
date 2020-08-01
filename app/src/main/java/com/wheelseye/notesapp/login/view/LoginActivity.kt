package com.wheelseye.notesapp.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.R
import com.wheelseye.notesapp.home.view.activity.HomeActivity
import com.wheelseye.notesapp.login.model.UserNotesModel
import com.wheelseye.notesapp.login.viewmodel.LoginViewModel
import com.wheelseye.notesapp.utility.Utility
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.progress_bar_fullscreen.*

class LoginActivity : BaseActivity() {

    private var mLoginViewModel: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        initViewModel()
        setObservers()
        initListeners()
    }

    private fun initListeners() {
        btnLogin.setOnClickListener {
            hideKeyboard()
            val emailID = tieEmailID.text.toString().trim()

            if (Utility.isEmailValid(emailID))
                mLoginViewModel?.performLogin(this, emailID)
            else
                Snackbar.make(container, "Invalid Email ID", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun initViewModel() {
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun setObservers() {
        mLoginViewModel?.isUpdating()?.observe(this, Observer {
            if (it.first) {
                pbLayout.visibility = View.VISIBLE
                pbText.text = it.second
            } else {
                pbLayout.visibility = View.GONE
            }
        })

        mLoginViewModel?.getUserNotes()?.observe(this, Observer {
            if (it == null) {
                Snackbar.make(container, "Internal Error Occurred", Snackbar.LENGTH_LONG).show()
            } else {
                saveUser(it)
                moveToHomeScreen(it)
            }
        })
    }

    private fun moveToHomeScreen(userNotesModel: UserNotesModel) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(USER_NOTES_MODEL_KEY, userNotesModel)
        startActivity(intent)
        finish()
    }

    private fun saveUser(userNotesModel: UserNotesModel) {
        val sharedPreference = getSharedPreferences(USER_DETAILS_SHARED_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt(USER_ID, userNotesModel.userID!!)
        editor.putString(USER_EMAIL_ID, tieEmailID.text.toString().trim())
        editor.apply()
    }
}