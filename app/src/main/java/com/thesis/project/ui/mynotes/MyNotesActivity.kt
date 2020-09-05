package com.thesis.project.ui.mynotes

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
import com.thesis.project.R
import com.thesis.project.models.arnote.ArNote

class MyNotesActivity : AppCompatActivity()
{
    lateinit var myNotesActivityViewModel : MyNotesActivityViewModel
    lateinit var arNoteActivityAdapter: MyNotesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mynotes)

        myNotesActivityViewModel = ViewModelProvider(this).get(MyNotesActivityViewModel::class.java)
        arNoteActivityAdapter = MyNotesAdapter(this)
        setRecyclerViewComponents()

    }

    private fun setRecyclerViewComponents()
    {
        val arNoteRecyclerView = findViewById<MultiSnapRecyclerView>(R.id.arnote_recycler_view)
        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        arNoteRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = arNoteActivityAdapter
        }
        getDataFromTheViewModel()
    }

    //ittt a baaaaj
    private fun getDataFromTheViewModel()
    {
        val currentArNoteLiveData  = myNotesActivityViewModel.getAllArNotes()
        observeLyricsDataToUi(currentArNoteLiveData,arNoteActivityAdapter)
    }

    private fun observeLyricsDataToUi(arnoteLiveData : LiveData<List<ArNote>>, adapter: MyNotesAdapter)
    {
        arnoteLiveData.observe(this, Observer {arNoteList -> adapter.setArNoteList(arNoteList) })
    }
}