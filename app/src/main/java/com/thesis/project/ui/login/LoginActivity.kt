package com.thesis.project.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.thesis.project.R
import com.thesis.project.databinding.ActivityLoginBinding
import com.thesis.project.databinding.ActivityRegisterBinding
import com.thesis.project.ui.main.MainActivity
import com.thesis.project.ui.register.RegisterActivity
import com.thesis.project.ui.register.RegisterActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity()
{
    lateinit var loginActivityViewModel : LoginActivityViewModel
    var isLoginSuccessful = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginActivityViewModel = ViewModelProvider(this).get(LoginActivityViewModel::class.java)

        DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login).apply {
            this.lifecycleOwner = this@LoginActivity
            this.loginUserModel = loginActivityViewModel
        }

        login_button.setOnClickListener {
            getLoginStatus()
            loginSetOnClickListener()
        }
        register_text.setOnClickListener{startRegisterPage()}
    }

    private fun startRegisterPage() { startActivity(Intent(this, RegisterActivity::class.java)) }

    private fun getLoginStatus() { isLoginSuccessful = loginActivityViewModel.startLogin() }

    private fun loginSetOnClickListener() { when(isLoginSuccessful) {true -> startActivity(Intent(this, MainActivity::class.java)) } }
}