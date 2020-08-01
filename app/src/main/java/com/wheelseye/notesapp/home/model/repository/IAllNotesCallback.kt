package com.wheelseye.notesapp.home.model.repository

import com.wheelseye.notesapp.crudNotes.model.NoteModel

interface IAllNotesCallback {
    fun updateAllNotes(noteModelList: ArrayList<NoteModel>?)
}