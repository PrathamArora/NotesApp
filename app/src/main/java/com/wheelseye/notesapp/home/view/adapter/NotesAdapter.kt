package com.wheelseye.notesapp.home.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wheelseye.notesapp.R
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import com.wheelseye.notesapp.crudNotes.view.AlterNoteActivity
import com.wheelseye.notesapp.home.model.repository.IDeleteNotesCallback

class NotesAdapter(
    private val mNoteModelList: ArrayList<NoteModel>,
    private val context: Context,
    private val iDeleteNotesCallback: IDeleteNotesCallback
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mNoteModelList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.initNoteDetails(context, mNoteModelList[position], iDeleteNotesCallback)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var noteTitle: TextView? = null
        private var noteMessage: TextView? = null
        private var noteDate: TextView? = null
        private var noteID: Int? = null
        private var cardNote: CardView? = null
        private var imgDelete: ImageView? = null

        init {
            noteTitle = itemView.findViewById(R.id.tvNoteTitle)
            noteMessage = itemView.findViewById(R.id.tvNoteMessage)
            noteDate = itemView.findViewById(R.id.tvNoteDate)
            cardNote = itemView.findViewById(R.id.cardNote)
            imgDelete = itemView.findViewById(R.id.imgDelete)
        }

        fun initNoteDetails(
            context: Context,
            noteModel: NoteModel,
            iDeleteNotesCallback: IDeleteNotesCallback
        ) {
            noteTitle?.text = noteModel.title
            noteMessage?.text = noteModel.message
            noteDate?.text = noteModel.date
            noteID = noteModel.notesID

            manageHeading(noteModel)
            setListeners(context, noteModel, iDeleteNotesCallback)
        }

        private fun setListeners(
            context: Context,
            noteModel: NoteModel,
            iDeleteNotesCallback: IDeleteNotesCallback
        ) {
            cardNote?.setOnClickListener {
                val intent = Intent(context, AlterNoteActivity::class.java)
                intent.putExtra(BaseActivity.SINGLE_NOTE_KEY, noteModel)
                context.startActivity(intent)
            }

            imgDelete?.setOnClickListener {
                iDeleteNotesCallback.deleteCurrentNote(noteModel)
            }
        }

        private fun manageHeading(noteModel: NoteModel) {
            if (noteModel.title?.trim()?.isEmpty()!!) {
                noteTitle?.visibility = View.GONE
            } else {
                noteTitle?.visibility = View.VISIBLE
            }

            if (noteModel.message?.trim()?.isEmpty()!!) {
                noteMessage?.visibility = View.GONE
            } else {
                noteMessage?.visibility = View.VISIBLE
            }
        }

    }

}