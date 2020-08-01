package com.wheelseye.notesapp.login.model.repository

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

class LoginRepository {

    private var mUserNotesModel: UserNotesModel? = null

    fun performLogin(context: Context, emailID: String, iLoginCallback: ILoginCallback) {
        if (mUserNotesModel != null) {
            iLoginCallback.updateLoginDetails(mUserNotesModel)
            return
        }
        getUserDetailsAndNotes(context, iLoginCallback, emailID)


//        dummyData(iLoginCallback)
    }

    private fun getUserDetailsAndNotes(
        context: Context,
        iLoginCallback: ILoginCallback,
        emailID: String
    ) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val iUserNotesEndPoint = retrofit.create(IUserNotesEndPoint::class.java)

        val callUserNotesModel = iUserNotesEndPoint.getUserNotes(emailID)

        callUserNotesModel.enqueue(
            object : Callback<GenericAPIModel<UserNotesModel>> {
                override fun onFailure(call: Call<GenericAPIModel<UserNotesModel>>, t: Throwable) {
                    iLoginCallback.updateLoginDetails(null)
                }

                override fun onResponse(
                    call: Call<GenericAPIModel<UserNotesModel>>,
                    response: Response<GenericAPIModel<UserNotesModel>>
                ) {
                    if (response.body() == null || !response.isSuccessful || response.body()?.status != 200) {
                        iLoginCallback.updateLoginDetails(null)
                        return
                    }
                    iLoginCallback.updateLoginDetails(response.body()?.data)
                }
            }
        )
    }

    private fun dummyData(iLoginCallback: ILoginCallback) {

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val noteModelList = ArrayList<NoteModel>()
                for (i in 1..5) {
                    if (i % 2 == 0) {
                        noteModelList.add(
                            NoteModel(
                                1,
                                "heading 1 heading 1 heading 1 heading 1 heading 1 heading 1 heading 1 ",
                                "message 1 message 1 message 1 message 1 message 1 message 1 message 1 ",
                                "29/07/2020"
                            )
                        )
                    }
                    noteModelList.add(
                        NoteModel(i, "heading $i", "message $i", "29/07/2020")
                    )
                }
                val tempUserNotesModel =
                    UserNotesModel(
                        1,
                        noteModelList
                    )
                iLoginCallback.updateLoginDetails(tempUserNotesModel)
            },
            3000
        )
    }

}