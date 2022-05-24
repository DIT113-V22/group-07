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
        ActivityScenario.launch(MainActivity::class.java)

        // Navigation to start fragment
        onView(withId(R.id.button_start)).perform(click())

        // verify
        onView(withId(R.id.button_play)).check(matches(isDisplayed()))

        // Navigation to apple
        onView(withId(R.id.appleIb)).perform(click())

        // verify
        onView(withId(R.id.appleID)).check(matches(isDisplayed()))
        pressBack()

        // verify
        onView(withId(R.id.appleIb)).check(matches(isDisplayed()))

        // Navigation to bottle
        onView(withId(R.id.bottleIb)).perform(click())

        // verify
        onView(withId(R.id.bottleID)).check(matches(isDisplayed()))
        pressBack()

        // verify
        onView(withId(R.id.bottleIb)).check(matches(isDisplayed()))

        // Navigation to can
        onView(withId(R.id.canIb)).perform(click())

        //verify
        onView(withId(R.id.canID)).check(matches(isDisplayed()))
        pressBack()

        //verify
        onView(withId(R.id.canIb)).check(matches(isDisplayed()))

        // Navigation to plastic bottle
        onView(withId(R.id.plasticIb)).perform(click())

        //verify
        onView(withId(R.id.plasticID)).check(matches(isDisplayed()))
        pressBack()

        //verify
        onView(withId(R.id.plasticIb)).check(matches(isDisplayed()))

        // Navigation to paper
        onView(withId(R.id.paperIb)).perform(click())

        //verify
        onView(withId(R.id.paperID)).check(matches(isDisplayed()))
        pressBack()

        //verify
        onView(withId(R.id.button_play)).check(matches(isDisplayed()))

        // Navigation to game intro fragment
        onView(withId(R.id.button_play)).perform(click())

        // verify
        onView(withId(R.id.speedometerIv)).check(matches(isDisplayed()))

        // Navigation to end fragment
        onView(withId(R.id.endGame)).perform(click())

        // verify
        onView(withId(R.id.add_score)).check(matches(isDisplayed()))

        // Navigation to leaderboard fragment
        onView(withId(R.id.leaderboard)).perform(click())

        // verify
        onView(withId(R.id.lb_username)).check(matches(isDisplayed()))
        pressBack()

        // verify
        onView(withId(R.id.leaderboard)).check(matches(isDisplayed()))

        // Navigation to home screen and test is complete
        onView(withId(R.id.home_end)).perform(click())

        // verify
        onView(withId(R.id.button_start)).check(matches(isDisplayed()))

    }
}