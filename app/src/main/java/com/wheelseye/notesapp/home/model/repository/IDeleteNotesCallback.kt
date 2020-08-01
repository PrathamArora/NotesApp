package com.wheelseye.notesapp.home.model.repository

import com.wheelseye.notesapp.crudNotes.model.NoteModel

interface IDeleteNotesCallback {
    fun deleteCurrentNote(noteModel: NoteModel)
}