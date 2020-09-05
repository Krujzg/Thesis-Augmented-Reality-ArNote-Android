package com.thesis.project.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thesis.project.R
import com.thesis.project.ui.arcamera.ArCameraActivity
import com.thesis.project.ui.login.LoginActivity
import com.thesis.project.ui.mynotes.MyNotesActivity
import com.thesis.project.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
{
    private var doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arnote.setOnClickListener{ startActivity(Intent(this, ArCameraActivity::class.java)) }

        settings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition( R.xml.slide_in_up, R.xml.slide_out_up )
        }

        logout.setOnClickListener{ finishAffinity() }

        mynotes.setOnClickListener{
            startActivity(Intent(this, MyNotesActivity::class.java))
            overridePendingTransition( R.xml.slide_in_up, R.xml.slide_out_up )
        }
    }

    override fun onBackPressed()
    {
        when(doubleBackToExitPressedOnce)
        {
            true -> startActivity(Intent(this,LoginActivity::class.java))
            false -> delayTimeBetweenTwoBackButtonPress()
        }
    }

    private fun delayTimeBetweenTwoBackButtonPress()
    {
        this.doubleBackToExitPressedOnce = true
        showMessageBoxWhenPressingBackTwice()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun showMessageBoxWhenPressingBackTwice() { Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show() }
}
