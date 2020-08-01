package com.wheelseye.notesapp.login.model.repository

import com.wheelseye.notesapp.login.model.UserNotesModel

interface ILoginCallback {
    fun updateLoginDetails(userNotesModel: UserNotesModel?)
}