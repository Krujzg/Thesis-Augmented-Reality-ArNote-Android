package com.thesis.project.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.thesis.project.databinding.ActivityRegisterBinding
import com.thesis.project.R
import com.thesis.project.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerActivityViewModel = ViewModelProvider(this).get(RegisterActivityViewModel::class.java)

        DataBindingUtil.setContentView<ActivityRegisterBinding>(this,R.layout.activity_register).apply {
            this.lifecycleOwner = this@RegisterActivity
            this.registerUserModel = registerActivityViewModel
        }

        register_RegActivity.setOnClickListener {
            val isRegistrationCorrect =  registerActivityViewModel.saveNewUserIntoLocalDb()

            when(isRegistrationCorrect)
            {
                true -> startLoginPage()
            }
        }
    }

    private fun startLoginPage() { startActivity(Intent(this, LoginActivity::class.java)) }
}