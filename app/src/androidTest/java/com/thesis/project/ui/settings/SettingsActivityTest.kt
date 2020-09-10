package com.thesis.project.ui.settings

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thesis.project.R
import com.thesis.project.R.id
import com.thesis.project.R.id.*
import com.thesis.project.androidtestutil.checkThat
import com.thesis.project.androidtestutil.checkThatTextIs
import com.thesis.project.androidtestutil.perform
import com.thesis.project.ui.login.LoginActivity
import com.thesis.project.util.testutil.EspressoIdlingResource
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    private val username = "teszt"

    private val email = "teszt@gmail.com"

    @Before
    fun before()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        login_button perform click()
        settings perform click()
    }

    @After
    fun after() { IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource) }

    @Test
    fun test_isLinearLayout_SettingsVisible() { linearlayout_settings checkThat isDisplayed() }

    @Test
    fun test_issettings_cardviewVisible() { settings_cardview checkThat isDisplayed() }

    @Test
    fun test_isusername_textview_settingsVisibleWithTitleUsername()
    {
        username_textview_settings checkThat isDisplayed()
        username_textview_settings checkThatTextIs "Username"
    }

    @Test
    fun test_isusername_view_settingsVisible() { username_view_settings checkThat isDisplayed() }

    @Test
    fun test_isusername_edittext_settingsVisibleWithGivenUserName()
    {
        username_edittext_settings checkThat isDisplayed()
        username_edittext_settings checkThatTextIs username
    }

    @Test
    fun test_isemail_textview_settingsVisibleWithTitleEmail()
    {
        email_textview_settings checkThat isDisplayed()
        email_textview_settings checkThatTextIs "Email"
    }

    @Test
    fun test_isemail_view_settingsVisible() { email_view_settings checkThat isDisplayed() }

    @Test
    fun test_isemail_edittext_settingsVisibleWithUserEmail()
    {
        email_edittext_settings checkThat isDisplayed()
        email_edittext_settings checkThatTextIs email
    }

    @Test
    fun test_ispassword_textview_settingsVisibleWithTitleNewPassword()
    {
        password_textview_settings checkThat isDisplayed()
        password_textview_settings checkThatTextIs "New password"
    }

    @Test
    fun test_isnew_password_textview_settingsVisibleWithTitleNewPasswordAgain()
    {
        new_password_textview_settings checkThat isDisplayed()
        new_password_textview_settings checkThatTextIs "New password again"
    }

    @Test
    fun test_ispassword_edittext_settingsVisible() { password_edittext_settings checkThat isDisplayed() }

    @Test
    fun test_isnew_password_edittext_settingsVisible() { new_password_edittext_settings checkThat isDisplayed() }
}