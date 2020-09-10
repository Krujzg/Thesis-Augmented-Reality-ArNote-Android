package com.thesis.project.ui.main

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.hasBackground
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thesis.project.R
import com.thesis.project.R.*
import com.thesis.project.R.id.*
import com.thesis.project.androidtestutil.checkThat
import com.thesis.project.androidtestutil.checkThatTextIs
import com.thesis.project.androidtestutil.perform
import com.thesis.project.androidtestutil.testrule.EspressoIdlingResourceRule
import com.thesis.project.ui.login.LoginActivity
import com.thesis.project.util.testutil.EspressoIdlingResource
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun before()
    {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        login_button perform click()
    }

    @After
    fun after() { IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource) }

    @Test
    fun test_isMainLinearLayoutVisible() { main_linearlayout checkThat isDisplayed() }
/*
    @Test
    fun test_isMainLinearLayoutHasBackGroundTurquoise() { main_linearlayout checkThat hasBackground(R.color.turquoise) }
*/
    @Test
    fun test_isArNoteCardViewVisible() { arnote checkThat isDisplayed() }

    @Test
    fun test_isArnote_main_imageviewVisible() { arnote_main_imageview checkThat isDisplayed() }

    /*
    @Test
    fun test_isarnote_main_imageviewHasBackGroundcerclebackgroundpurple() { arnote_main_imageview checkThat hasBackground(drawable.cerclebackgroundpurple) }


     */
    @Test
    fun test_isArnote_main_titleContainsTitle() { ar_note_title checkThatTextIs "AR Note" }

    @Test
    fun test_isArnote_main_viewVisible() { ar_note_view checkThat isDisplayed() }

    @Test
    fun test_isArnote_main_descVisible() { ar_note_desc checkThat isDisplayed() }

    @Test
    fun test_isArnote_main_descContainsDescription() { ar_note_desc checkThatTextIs "Place your Augmented Reality note" }

    @Test
    fun test_ismynotesCardViewVisible() { mynotes checkThat isDisplayed() }

    @Test
    fun test_ismynotes_main_imageviewVisible() { mynotes_main_imageview checkThat isDisplayed() }

    /*
    @Test
    fun test_ismynotes_main_imageviewHasBackGroundcerclebackgroundpink() { mynotes_main_imageview checkThat hasBackground(drawable.cerclebackgroundpink) }
*/
    @Test
    fun test_ismynotes_main_titleContainsTitle() { mynotes_main_title checkThatTextIs "My Notes" }

    @Test
    fun test_ismynotes_main_viewVisible() { mynotes_main_view checkThat isDisplayed() }


    @Test
    fun test_ismynotes_main_descVisible() { mynotes_main_desc checkThat isDisplayed() }

    @Test
    fun test_ismynotes_main_descContainsDescription() { mynotes_main_desc checkThatTextIs "Check your notes" }

    @Test
    fun test_islogoutCardViewVisible() { logout checkThat isDisplayed() }

    @Test
    fun test_islogout_main_imageviewVisible() { logout_main_imageview checkThat isDisplayed() }
/*
    @Test
    fun test_islogout_main_imageviewHasBackGroundcerclebackgroundyello() { logout_main_imageview checkThat hasBackground(drawable.cerclebackgroundyello) }
*/
    @Test
    fun test_islogout_main_titleContainsTitle() { logout_main_title checkThatTextIs "Logout" }

    @Test
    fun test_islogout_main_viewVisible() { logout_main_view checkThat isDisplayed() }


    @Test
    fun test_islogout_main_descVisible() { logout_main_desc checkThat isDisplayed() }

    @Test
    fun test_islogout_main_descContainsDescription() { logout_main_desc checkThatTextIs "Logout from the app" }

    @Test
    fun test_issettingsCardViewVisible() { settings checkThat isDisplayed() }

    @Test
    fun test_issettings_main_imageviewVisible() { settings_main_imageview checkThat isDisplayed() }

    /*
    @Test
    fun test_issettings_main_imageviewHasBackGroundcerclebackgroundgreen() { settings_main_imageview checkThat hasBackground(drawable.cerclebackgroundgreen) }
*/
    @Test
    fun test_issettings_main_titleContainsTitle() { settings_main_title checkThatTextIs "Settings" }

    @Test
    fun test_issettings_main_viewVisible() { settings_main_view checkThat isDisplayed() }

    @Test
    fun test_issettings_main_descVisible() { settings_main_desc checkThat isDisplayed() }

    @Test
    fun test_issettings_main_descContainsDescription() { settings_main_desc checkThatTextIs "Change your data" }

    @Test
    fun test_willTheArCameraActivityShowUpAfterClickOnArNoteCard()
    {
        arnote perform click()
        negative_button perform click()
        arcamera_relativelayou checkThat isDisplayed()
    }

    @Test
    fun test_willTheMyNotesActivityShowUpAfterClickOnMyNotesCard()
    {
        mynotes perform click()
        mynotes_scrollview checkThat isDisplayed()
    }

    /*
    @Test
    fun test_willTheSettingsActivityShowUpAfterClickOnSettingCard()
    {
        settings perform click()
        arcamera_relativelayou checkThat isDisplayed()
    }

     */
/*
    @Test
    fun test_willTheAppGetKilledAfterClickOnLogoutCard()
    {
        logout perform click()
        main_linearlayout checkThat not(isDisplayed())

    }

 */

}