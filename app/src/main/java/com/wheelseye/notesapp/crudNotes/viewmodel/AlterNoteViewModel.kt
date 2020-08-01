package com.wheelseye.notesapp.crudNotes.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.wheelseye.notesapp.crudNotes.model.AlterNoteRepository

class AlterNoteViewModel : ViewModel() {

    private val mAlterNoteRepository = AlterNoteRepository()

    fun saveNote(context: Context, noteID: Int, title: String, note: String) {
//        mAlterNoteRepository.saveNote(context, noteID, title, note)
    }
}