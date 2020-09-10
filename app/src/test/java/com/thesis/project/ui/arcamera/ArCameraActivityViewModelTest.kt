package com.thesis.project.ui.arcamera

import android.os.Build
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.thesis.project.data.roomdb.arnote.ArNoteDao
import com.thesis.project.data.roomdb.arnote.ArNoteLocalDataBase
import com.thesis.project.ui.testutil.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P, minSdk = Build.VERSION_CODES.P)
class ArCameraActivityViewModelTest {

    private lateinit var arNoteDao: ArNoteDao
    private lateinit var db : ArNoteLocalDataBase

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private val testDispatcher = coroutinesTestRule.testDispatcher

    @Before
    fun setUp()
    {
        val context = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(context,ArNoteLocalDataBase::class.java).build()
        arNoteDao = db.arNoteDao()
    }

    @After @Throws(IOException::class) fun closeDb() { db.close() }
}