package com.wheelseye.notesapp.home.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.wheelseye.notesapp.R
import com.wheelseye.notesapp.base.BaseActivity
import com.wheelseye.notesapp.crudNotes.model.NoteModel
import com.wheelseye.notesapp.crudNotes.view.AlterNoteActivity
import com.wheelseye.notesapp.home.model.repository.IDeleteNotesCallback
import com.wheelseye.notesapp.home.view.adapter.NotesAdapter
import com.wheelseye.notesapp.home.viewmodel.HomeViewModel
import com.wheelseye.notesapp.login.model.UserNotesModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.progress_bar_fullscreen.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    IDeleteNotesCallback {

    private var navTvEmailId: TextView? = null
    private var userNotesModel: UserNotesModel? = null
    private var mHomeViewModel: HomeViewModel? = null
    private var mNotesAdapter: NotesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Home"

        if (intent.hasExtra(USER_NOTES_MODEL_KEY))
            userNotesModel = intent.getSerializableExtra(USER_NOTES_MODEL_KEY) as UserNotesModel

        initViews()
        initViewModel()
        setObservers()
        setUserNavData()
        initListeners()
    }

    private fun initListeners() {
        fabAddNote.setOnClickListener {
            val intent = Intent(this, AlterNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(allNotesList: ArrayList<NoteModel>) {
        if (allNotesList.isNullOrEmpty()) {
            imgNoNotes.visibility = View.VISIBLE
            rvAllNotes.visibility = View.GONE
            return
        }

        imgNoNotes.visibility = View.GONE
        rvAllNotes.visibility = View.VISIBLE

        mNotesAdapter = NotesAdapter(allNotesList, this, this)
        rvAllNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        
        rvAllNotes.adapter = mNotesAdapter
    }

    private fun initViews() {
        navView.setNavigationItemSelectedListener(this)
        navView.bringToFront()
        val toggle =
            ActionBarDrawerToggle(this, drawerLayout!!, toolbar!!, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()

        val headerView = navView.getHeaderView(0)
        navTvEmailId = headerView.findViewById(R.id.tvEmailID)
    }

    private fun setUserNavData() {
        val sharedPreference = getSharedPreferences(USER_DETAILS_SHARED_PREF, Context.MODE_PRIVATE)
        navTvEmailId?.text = sharedPreference.getString(USER_EMAIL_ID, "Guest User")
    }


    override fun initViewModel() {
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mHomeViewModel?.init(userNotesModel?.userNotes)
    }

    override fun setObservers() {
        mHomeViewModel?.isUpdating()?.observe(this, Observer {
            if (it.first) {
                pbLayout.visibility = View.VISIBLE
                pbText.text = it.second
            } else {
                pbLayout.visibility = View.GONE
                pbText.text = it.second
            }
        })

        mHomeViewModel?.getAllNotes()?.observe(this, Observer {
            initRecyclerView(it)
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.menuAddNotes -> {
                val intent = Intent(this, AlterNoteActivity::class.java)
                startActivity(intent)
            }
            R.id.menuSyncNotes -> {
                mHomeViewModel?.syncNotes(this)
            }
            R.id.menuLogout -> {

            }
        }
        return true
    }

    override fun deleteCurrentNote(noteModel: NoteModel) {
        // Doubt
        // How to add icon based on theme?

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete")
            .setMessage("Do you want to delete this note from " + noteModel.date + "?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ ->
                mHomeViewModel?.deleteNote(noteModel)
            }
            .setNegativeButton(
                "No"
            ) { _, _ -> }
            .show()


    }
}