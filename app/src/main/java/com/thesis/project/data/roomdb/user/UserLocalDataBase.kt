package com.thesis.project.data.roomdb.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thesis.project.models.user.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserLocalDataBase : RoomDatabase()
{
    abstract fun userDao(): UserDao

    companion object
    {
        @Volatile
        var INSTANCE: UserLocalDataBase? = null

        fun getDatabase(context: Context): UserLocalDataBase
        {
            val tempInstance = INSTANCE
            if(tempInstance != null) { return tempInstance }

            synchronized(this)
            {
                val instance =  Room.databaseBuilder(context.applicationContext,
                    UserLocalDataBase::class.java,"user_database")
                    .build()

                INSTANCE = instance
                return instance;
            }
        }
    }
}