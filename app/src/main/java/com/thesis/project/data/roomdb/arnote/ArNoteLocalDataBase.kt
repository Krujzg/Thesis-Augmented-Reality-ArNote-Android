package com.thesis.project.data.roomdb.arnote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thesis.project.models.arnote.ArNote

@Database(entities = [ArNote::class], version = 1, exportSchema = false)
abstract class ArNoteLocalDataBase : RoomDatabase()
{
    abstract fun arNoteDao(): ArNoteDao

    companion object
    {
        @Volatile
        var INSTANCE: ArNoteLocalDataBase? = null

        fun getDatabase(context: Context): ArNoteLocalDataBase
        {
            val tempInstance = INSTANCE
            if(tempInstance != null) { return tempInstance }

            synchronized(this)
            {
                val instance =  Room.databaseBuilder(context.applicationContext,
                    ArNoteLocalDataBase::class.java,"arnote_database")
                    .build()

                INSTANCE = instance
                return instance;
            }
        }
    }
}