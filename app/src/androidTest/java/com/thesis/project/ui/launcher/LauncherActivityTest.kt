package com.thesis.project.ui.launcher

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thesis.project.R
import com.thesis.project.R.id
import com.thesis.project.R.id.*
import com.thesis.project.androidtestutil.ImageViewHasDrawableMatcher.hasDrawable
import com.thesis.project.androidtestutil.checkThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LauncherActivityTest
{
    @get:Rule
    val activityRule = ActivityScenarioRule(LauncherActivity::class.java)

    @Test
    fun test_isLauncherViewVisible() { launcher_view checkThat isDisplayed() }

    @Test
    fun test_isLauncherRelativeLayoutVisible() { launcher_relative_layout checkThat isDisplayed() }

    @Test
    fun test_isImage_launcherLogoVisible() {image_launcher_logo checkThat isDisplayed()}


}