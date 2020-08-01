package com.wheelseye.notesapp.home.model.service

import com.wheelseye.notesapp.base.GenericAPIModel
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IAllNotesEndPoint {

    @POST("/getAllNotes")
    fun getAllNotes(@Body userID: Int): Call<GenericAPIModel<ArrayList<NoteModel>>>
}