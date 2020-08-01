package com.wheelseye.notesapp.home.model.repository

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.base.GenericAPIModel
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import com.wheelseye.notesapp.login.model.UserNotesModel
import com.wheelseye.notesapp.login.model.service.IUserNotesEndPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeRepository {

    private val allNotesList = ArrayList<NoteModel>()

    fun getAllNotes(context: Context, userID: Int, iAllNotesCallback: IAllNotesCallback) {
        // Retrofit call
        // input - ID
        // output - All notes
    }

    fun init(userNotesList: ArrayList<NoteModel>?, iAllNotesCallback: IAllNotesCallback) {

        if (userNotesList.isNullOrEmpty()) {
            iAllNotesCallback.updateAllNotes(allNotesList)
            return
        }

        allNotesList.clear()
        allNotesList.addAll(userNotesList)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                iAllNotesCallback.updateAllNotes(allNotesList)
            },
            2000
        )

    }

    fun deleteNote(noteModel: NoteModel, iAllNotesCallback: IAllNotesCallback) {
        allNotesList.remove(noteModel)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                iAllNotesCallback.updateAllNotes(allNotesList)
            },
            2000
        )
    }

    fun syncNotes(context: Context, iAllNotesCallback: IAllNotesCallback) {
        val emailID = context.getSharedPreferences(
            BaseActivity.USER_DETAILS_SHARED_PREF,
            Context.MODE_PRIVATE
        ).getString(
            BaseActivity.USER_EMAIL_ID, "guest"
        )!!

        val retrofit = Retrofit.Builder()
            .baseUrl(BaseActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val iUserNotesEndPoint = retrofit.create(IUserNotesEndPoint::class.java)

        val callUserNotesModel = iUserNotesEndPoint.getUserNotes(emailID)

        callUserNotesModel.enqueue(
            object : Callback<GenericAPIModel<UserNotesModel>> {
                override fun onFailure(call: Call<GenericAPIModel<UserNotesModel>>, t: Throwable) {
                    iAllNotesCallback.updateAllNotes(null)
                }

                override fun onResponse(
                    call: Call<GenericAPIModel<UserNotesModel>>,
                    response: Response<GenericAPIModel<UserNotesModel>>
                ) {
                    if (response.body() == null || !response.isSuccessful || response.body()?.status != 200) {
                        iAllNotesCallback.updateAllNotes(null)
                        return
                    }
                    allNotesList.clear()
                    allNotesList.addAll(response.body()?.data?.userNotes!!)
                    iAllNotesCallback.updateAllNotes(allNotesList)
                }
            }
        )

    }
}