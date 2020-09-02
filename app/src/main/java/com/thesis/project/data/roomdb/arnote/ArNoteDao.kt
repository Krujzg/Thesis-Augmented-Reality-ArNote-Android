package com.thesis.project.data.roomdb.arnote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thesis.project.models.arnote.ArNote

@Dao
interface ArNoteDao
{
    @Insert
    suspend fun insertArNote(arnote : ArNote)

    @Query("SELECT * from arnote_table")
    fun getAllArNote() : LiveData<List<ArNote>>
}