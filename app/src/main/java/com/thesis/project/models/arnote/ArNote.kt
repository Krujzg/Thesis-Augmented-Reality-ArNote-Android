package com.thesis.project.models.arnote

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="arnote_table")
data class ArNote (
    @PrimaryKey(autoGenerate = true)
    val id: Long=0L,

    @ColumnInfo(name="type")
    var type: String= "",

    @ColumnInfo(name="text")
    var text: String= "",

    @ColumnInfo(name="date")
    var date: String= "",

    @ColumnInfo(name="shortcode")
    var shortcode: String ="",

    @ColumnInfo(name="cloudAnchorId")
    var cloudAnchorId: String ="")