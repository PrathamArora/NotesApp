package com.wheelseye.notesapp.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericAPIModel<T>(
    @Expose
    @SerializedName("Status")
    val status: Int?,

    @Expose
    @SerializedName("Message")
    val message: String?,

    @Expose
    @SerializedName("Data")
    val data: T?
)