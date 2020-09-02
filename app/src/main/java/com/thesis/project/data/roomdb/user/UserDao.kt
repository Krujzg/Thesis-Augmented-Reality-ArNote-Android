package com.thesis.project.data.roomdb.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thesis.project.models.user.User

@Dao
interface UserDao
{
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * from user_table")
    fun getAllUsers() : List<User>

    @Update
    suspend fun updateUser(user: User)
}