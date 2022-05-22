package com.example.greengarbageapp.navigation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.greengarbageapp.R
import com.example.greengarbageapp.activities.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class NavigationTest{

    @Test
    fun testFragmentsNavigation() {

        // SETUP
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        // Navigation to start fragment
        onView(withId(R.id.button_start)).perform(click())

        // verify
        onView(withId(R.id.button_play)).check(matches(isDisplayed()))

        /*
        @TODO lägg till pop up äpple grejerna
        // Navigation
        onView(withId(R.id.appleIb)).perform(click())

        // verify
        onView(withId(R.drawable.apple)).check(matches(isDisplayed()))
        pressBack()

        // verify
        onView(withId(R.id.appleIb)).check(matches(isDisplayed()))
         */

        // Navigation to game intro fragment
        onView(withId(R.id.button_play)).perform(click())

        // verify
        onView(withId(R.id.speedometerIv)).check(matches(isDisplayed()))

        // Navigation to end fragment
        onView(withId(R.id.endGame)).perform(click())

        // verify
        onView(withId(R.id.add_score)).check(matches(isDisplayed()))

        // @TODO fortsätt från end > leaderboard > pressback hela vägen

    }
}