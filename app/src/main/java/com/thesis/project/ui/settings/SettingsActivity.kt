package com.thesis.project.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.thesis.project.R
import com.thesis.project.databinding.ActivitySettingsBinding
import com.thesis.project.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class SettingsActivity : AppCompatActivity()
{
    lateinit var settingsActivityViewModel: SettingsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        settingsActivityViewModel = ViewModelProvider(this).get(SettingsActivityViewModel::class.java)

        DataBindingUtil.setContentView<ActivitySettingsBinding>(this,R.layout.activity_settings).apply {
            this.lifecycleOwner = this@SettingsActivity
            this.settingsUserModel = settingsActivityViewModel
        }
    }

    override fun onBackPressed()
    {
        startActivity(Intent(this,MainActivity::class.java))
        overridePendingTransition( R.xml.slide_in_down, R.xml.slide_out_down )
    }
}