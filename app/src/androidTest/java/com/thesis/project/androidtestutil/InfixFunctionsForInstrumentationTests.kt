package com.thesis.project.androidtestutil

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher

infix fun Int.perform(action: ViewAction) { onView(withId(this)).perform(action) }

infix fun Int.checkThat(matcher: Matcher<in View>) { onView(withId(this)).check(matches(matcher)) }

infix fun Int.checkThatTextIs(text: String) { onView(withId(this)).check(matches(withText(text))) }

infix fun Int.replaceTextWith(text: String?) { onView(withId(this)).perform(replaceText(text)) }

infix fun Int.checkToastMessageText(toastMessageText : String) { onView(withText(toastMessageText)).inRoot(ToastMatcher()).check(matches(isDisplayed())) }