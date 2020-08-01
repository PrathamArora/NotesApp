package com.wheelseye.notesapp.login.model.service

import com.wheelseye.notesapp.base.GenericAPIModel
import com.wheelseye.notesapp.login.model.UserNotesModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUserNotesEndPoint {

    @POST("/loginUser")
    fun getUserNotes(@Body emailID: String): Call<GenericAPIModel<UserNotesModel>>
}