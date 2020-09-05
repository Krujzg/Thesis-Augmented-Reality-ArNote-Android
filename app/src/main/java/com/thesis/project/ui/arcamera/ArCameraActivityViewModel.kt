package com.thesis.project.ui.arcamera

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thesis.project.data.roomdb.arnote.ArNoteDao
import com.thesis.project.data.roomdb.arnote.ArNoteLocalDataBase
import com.thesis.project.models.arnote.ArNote
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ArCameraActivityViewModel(application: Application) : AndroidViewModel(application)
{
    private var arnoteDao : ArNoteDao

    init {
        arnoteDao = ArNoteLocalDataBase
            .getDatabase(application)
            .arNoteDao()
    }
    fun insertNodeIntoLocalDb(arNote: ArNote) { viewModelScope.launch { arnoteDao.insertArNote(arNote) } }
}