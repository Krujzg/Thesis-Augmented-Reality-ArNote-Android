package com.thesis.project.ui.arcamera

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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArCameraActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun before()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        login_button perform click()
        arnote perform click()
        negative_button perform click()
    }

    @After
    fun after() { IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource) }

    @Test
    fun test_isArcamera_relativelayoutVisible() { arcamera_relativelayou checkThat isDisplayed() }

    @Test
    fun test_issetting_buttonVisible() { setting_button checkThat isDisplayed() }

    @Test
    fun test_isback_buttonVisible() { back_button checkThat isDisplayed() }

    @Test
    fun test_afterClickOnBack_buttonTheAppGoesBackToMainActivity()
    {
        back_button perform click()
        main_linearlayout checkThat isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsTheLayoutFilter()
    {
        setting_button perform click()
        layout_filter_linearlayout checkThat isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsaction_buttons_textviewWithGivenText()
    {
        setting_button perform click()
        action_buttons_textview checkThat isDisplayed()
        action_buttons_textview checkThatTextIs "Action buttons"
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsaction_buttons_view()
    {
        setting_button perform click()
        action_buttons_view checkThat isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsresolve_buttonWithGivenText()
    {
        setting_button perform click()
        resolve_button checkThat isDisplayed()
        resolve_button checkThatTextIs "Resolve"
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsclear_buttonWithGivenText()
    {
        setting_button perform click()
        clear_button checkThat  isDisplayed()
        clear_button checkThatTextIs "Clear"

    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowstype_textviewWithGivenText()
    {
        setting_button perform click()
        type_textview checkThat  isDisplayed()
        type_textview checkThatTextIs "ArNote type:"
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsspinnerSelection()
    {
        setting_button perform click()
        spinnerSelection checkThat  isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsarnote_text_textviewWithGivenText()
    {
        setting_button perform click()
        arnote_text_textview checkThat isDisplayed()
        arnote_text_textview checkThatTextIs "ArNote Text:"
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsarnote_view_textview()
    {
        setting_button perform click()
        arnote_view_textview checkThat  isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsWantedTextEdit()
    {
        setting_button perform click()
        WantedTextEdit checkThat isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsThenegative_button()
    {
        setting_button perform click()
        negative_button checkThat  isDisplayed()
    }

    @Test
    fun test_afterClickOnSetting_buttonTheAppShowsThepositive_button()
    {
        setting_button perform click()
        positive_button checkThat  isDisplayed()
    }
}