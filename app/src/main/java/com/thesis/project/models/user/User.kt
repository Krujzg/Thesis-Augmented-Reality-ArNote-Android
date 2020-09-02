package com.thesis.project.models.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Long=0L,

    @ColumnInfo(name="username")
    var username: String= "",

    @ColumnInfo(name="email")
    var email: String= "",

    @ColumnInfo(name="password")
    var password: String= "")