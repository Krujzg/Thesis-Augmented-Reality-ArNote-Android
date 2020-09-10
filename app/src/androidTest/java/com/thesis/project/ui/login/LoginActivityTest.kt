package com.thesis.project.ui.login

import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thesis.project.R.id.*
import com.thesis.project.androidtestutil.checkThat
import com.thesis.project.androidtestutil.checkToastMessageText
import com.thesis.project.androidtestutil.perform
import com.thesis.project.androidtestutil.replaceTextWith
import com.thesis.project.androidtestutil.testrule.EspressoIdlingResourceRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    private val data_entry  = "teszt"

    private val toastMessageTextWelcome  = "Welcome $data_entry"

    private val toastMessageTextErrorLogin  = "The given inputs are incorrect"


    @Test
    fun test_isRegisterTextViewClickBroughtUpRegisterActivity()
    {
        register_text perform click()
        register_main_layout checkThat isDisplayed()
    }

    @Test
    fun test_isToastMessageTextWelcomeShowsUpAfterSuccessfulLogin()
    {
        editText_username_login replaceTextWith data_entry
        edittext_password_login replaceTextWith data_entry
        login_button perform click()
        linearlayout_login checkToastMessageText toastMessageTextWelcome
    }

    @Test
    fun test_isToastMessageTextErrorLoginShowsUpAfterDeniedLogin()
    {
        editText_username_login replaceTextWith "not_good_user"
        edittext_password_login replaceTextWith "not_good_password"
        login_button perform click()
        linearlayout_login checkToastMessageText toastMessageTextErrorLogin
    }

    @Test
    fun test_isLoginImageVisible()
    {
        login_image checkThat isDisplayed()
    }
}