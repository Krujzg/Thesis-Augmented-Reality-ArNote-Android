package com.thesis.project.ui.mynotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.thesis.project.data.roomdb.arnote.ArNoteDao
import com.thesis.project.data.roomdb.arnote.ArNoteLocalDataBase
import com.thesis.project.models.arnote.ArNote

class MyNotesActivityViewModel(application: Application) : AndroidViewModel(application)
{
    private var arnoteDao : ArNoteDao

    init {
        arnoteDao = ArNoteLocalDataBase
            .getDatabase(application)
            .arNoteDao()

    }

    fun getAllArNotes() : LiveData<List<ArNote>> =  arnoteDao.getAllArNote()
}