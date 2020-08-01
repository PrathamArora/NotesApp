package com.wheelseye.notesapp.login.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.login.model.repository.ILoginCallback
import com.wheelseye.notesapp.login.model.repository.LoginRepository
import com.wheelseye.notesapp.login.model.UserNotesModel

class LoginViewModel : ViewModel(), ILoginCallback {

    private val mIsUpdating = MutableLiveData<Pair<Boolean, String>>()
    private val mLoginRepository = LoginRepository()
    private val mUserNotesLiveData = MutableLiveData<UserNotesModel>()

    override fun updateLoginDetails(userNotesModel: UserNotesModel?) {
        mIsUpdating.value = Pair(BaseActivity.HIDE_LOADER, "")
        mUserNotesLiveData.value = userNotesModel
    }

    fun performLogin(context: Context, emailID: String) {
        mIsUpdating.value = Pair(BaseActivity.SHOW_LOADER, "Checking Credentials")
        mLoginRepository.performLogin(context, emailID, this)
    }

    fun isUpdating(): LiveData<Pair<Boolean, String>> {
        return mIsUpdating
    }

    fun getUserNotes(): LiveData<UserNotesModel> {
        return mUserNotesLiveData
    }

}