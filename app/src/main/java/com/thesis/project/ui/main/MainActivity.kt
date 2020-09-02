package com.thesis.project.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thesis.project.R
import com.thesis.project.ui.arcamera.ArCameraActivity
import com.thesis.project.ui.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        arnote.setOnClickListener{ startActivity(Intent(this, ArCameraActivity::class.java)) }

        settings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
            overridePendingTransition( R.xml.slide_in_up, R.xml.slide_out_up )
        }

        logout.setOnClickListener{ finishAffinity() }
    }
}
