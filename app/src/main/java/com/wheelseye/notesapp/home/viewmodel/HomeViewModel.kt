package com.wheelseye.notesapp.home.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import com.wheelseye.notesapp.home.model.repository.HomeRepository
import com.wheelseye.notesapp.home.model.repository.IAllNotesCallback

class HomeViewModel : ViewModel(), IAllNotesCallback {
    private val mIsUpdating = MutableLiveData<Pair<Boolean, String>>()
    private val mHomeRepository = HomeRepository()
    private val mUserNotesLiveData = MutableLiveData<ArrayList<NoteModel>>()

    fun init(userNotesList: ArrayList<NoteModel>?) {
        mIsUpdating.value = Pair(BaseActivity.SHOW_LOADER, "Updating...")
        mHomeRepository.init(userNotesList, this)
    }


//    fun getAllNotes(context: Context, userID: Int) {
//        mIsUpdating.value = Pair(BaseActivity.SHOW_LOADER, "Fetching all notes")
//        mHomeRepository.getAllNotes(context, userID, this)
//    }

    fun isUpdating(): LiveData<Pair<Boolean, String>> {
        return mIsUpdating
    }

    fun getAllNotes(): LiveData<ArrayList<NoteModel>> {
        return mUserNotesLiveData
    }


    override fun updateAllNotes(noteModelList: ArrayList<NoteModel>?) {
        mIsUpdating.value = Pair(BaseActivity.HIDE_LOADER, "")
        mUserNotesLiveData.value = noteModelList
    }

    fun deleteNote(noteModel: NoteModel) {
        mIsUpdating.value = Pair(BaseActivity.SHOW_LOADER, "Deleting Note")
        mHomeRepository.deleteNote(noteModel, this)
    }

    fun syncNotes(context: Context) {
        mIsUpdating.value = Pair(BaseActivity.SHOW_LOADER, "Updating...")
        mHomeRepository.syncNotes(context, this)
    }

}