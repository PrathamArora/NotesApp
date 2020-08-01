package com.wheelseye.notesapp.crudNotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.wheelseye.notesapp.R
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import com.wheelseye.notesapp.crudNotes.viewmodel.AlterNoteViewModel
import kotlinx.android.synthetic.main.activity_alter_note.*

class AlterNoteActivity : BaseActivity() {

    private var isEditMode = false
    private var mAlterNoteViewModel: AlterNoteViewModel? = null
    private var noteID = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_note)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add Note"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if (intent.hasExtra(SINGLE_NOTE_KEY) && intent.getSerializableExtra(SINGLE_NOTE_KEY) != null) {
            isEditMode = true
            supportActionBar?.title = "Edit Note"
            setData(intent.getSerializableExtra(SINGLE_NOTE_KEY) as NoteModel)
        }
        initViewModel()
        initListeners()
    }

    private fun initListeners() {
        btnSave.setOnClickListener {
            hideKeyboard()
            checkAndSave()
        }
    }

    private fun checkAndSave() {
        val title = etTitle.text.toString().trim()
        val note = etMessage.text.toString().trim()

        if (title.isEmpty() && note.isEmpty()) {
            Snackbar.make(container, "Unable to save empty note.", Snackbar.LENGTH_SHORT).show()
            return
        }

        mAlterNoteViewModel?.saveNote(this, noteID, title, note)
    }

    private fun setData(noteModel: NoteModel) {
        etTitle.setText(noteModel.title.toString())
        etMessage.setText(noteModel.message.toString())
        noteID = noteModel.notesID!!
    }

    override fun initViewModel() {
        mAlterNoteViewModel = ViewModelProvider(this).get(AlterNoteViewModel::class.java)
    }

    override fun setObservers() {
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}