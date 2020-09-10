package com.thesis.project.ui.register

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thesis.project.R
import com.thesis.project.R.id
import com.thesis.project.R.id.*
import com.thesis.project.androidtestutil.checkThat
import com.thesis.project.androidtestutil.checkToastMessageText
import com.thesis.project.androidtestutil.perform
import com.thesis.project.androidtestutil.replaceTextWith
import com.thesis.project.androidtestutil.testrule.EspressoIdlingResourceRule
import com.thesis.project.ui.login.LoginActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class RegisterActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(RegisterActivity::class.java)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    private val toastMessageTextAtPasswordError  = "The passwords are not the same"
    private val toastMessageTextAtMissingEdittextInputData = "Some of the fields are not filled"

    @Test
    fun test_isRegisteringUserWithDifferentPasswordShowsToastMessageTextAtPasswordError()
    {
        editText_username replaceTextWith "something"
        editText_email replaceTextWith "something@gmail.com"
        edittext_password replaceTextWith "something"
        edittext_second_password replaceTextWith "something_different"
        register_RegActivity perform click()
        register_main_layout checkToastMessageText toastMessageTextAtPasswordError
    }

    @Test
    fun test_isRegisteringUserWithLeftOutUserNameShowsToastMessageTextAtMissingEdittextInputData()
    {
        editText_username replaceTextWith ""
        editText_email replaceTextWith "something@gmail.com"
        edittext_password replaceTextWith "something"
        edittext_second_password replaceTextWith "something"
        register_RegActivity perform click()
        register_main_layout checkToastMessageText toastMessageTextAtMissingEdittextInputData
    }

    @Test
    fun test_isRegisteringUserWithLeftOutEmailShowsToastMessageTextAtMissingEdittextInputData()
    {
        editText_username replaceTextWith "something"
        editText_email replaceTextWith ""
        edittext_password replaceTextWith "something"
        edittext_second_password replaceTextWith "something"
        register_RegActivity perform click()
        register_main_layout checkToastMessageText toastMessageTextAtMissingEdittextInputData
    }

    @Test
    fun test_isRegisteringUserWithLeftOutPasswordEdittextsShowsToastMessageTextAtMissingEdittextInputData()
    {
        editText_username replaceTextWith "something"
        editText_email replaceTextWith "something@gmail.com"
        edittext_password replaceTextWith ""
        edittext_second_password replaceTextWith ""
        register_RegActivity perform click()
        register_main_layout checkToastMessageText toastMessageTextAtMissingEdittextInputData
    }
}