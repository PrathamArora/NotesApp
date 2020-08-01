package com.wheelseye.notesapp.crudNotes.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NoteModel(
    @Expose
    @SerializedName("notesID")
    val notesID: Int?,

    @Expose
    @SerializedName("title")
    val title: String?,

    @Expose
    @SerializedName("message")
    val message: String?,

    @Expose
    @SerializedName("date")
    val date: String?
) : Serializable