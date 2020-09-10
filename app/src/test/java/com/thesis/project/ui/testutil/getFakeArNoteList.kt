package com.thesis.project.ui.testutil

import com.thesis.project.models.arnote.ArNote

fun getFakeArNotes() = mutableListOf<ArNote>(

    ArNote(
        type = "Normal",
        text = "valami",
        date = "fakeidőpont",
        shortcode = "141",
        cloudAnchorId = "fakeid"),

    ArNote(
        type = "Warning",
        text = "valami2",
        date = "fakeidőpont2",
        shortcode = "142",
        cloudAnchorId = "fakeid2"),

    ArNote(
        type = "Urgent",
        text = "valami3",
        date = "fakeidőpont3",
        shortcode = "143",
        cloudAnchorId = "fakeid3"),

    ArNote(
        type = "Normal",
        text = "valami4",
        date = "fakeidőpont4",
        shortcode = "144",
        cloudAnchorId = "fakeid4"),

    ArNote(
        type = "Warning",
        text = "valami5",
        date = "fakeidőpont5",
        shortcode = "145",
        cloudAnchorId = "fakeid5"),

    ArNote(
        type = "Urgent",
        text = "valami6",
        date = "fakeidőpont6",
        shortcode = "146",
        cloudAnchorId = "fakeid6")
)