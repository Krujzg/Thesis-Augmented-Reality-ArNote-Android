package com.thesis.project.data.roomdb.arnote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thesis.project.models.arnote.ArNote

@Database(entities = [ArNote::class], version = 3, exportSchema = false)
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
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()

                INSTANCE = instance
                return instance;
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE arnote_table ADD COLUMN shortcode TEXT")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE arnote_table ADD COLUMN cloudAnchorId TEXT")
            }
        }
    }
}