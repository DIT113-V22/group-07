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

    // val delay = Thread.sleep(3000)

    @Test
    fun test_navigation_to_apple() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.appleIb)).perform(click())
        onView(withId(R.id.appleID)).check(matches(isDisplayed()))
    }
    @Test
    fun test_navigation_to_apple_and_back() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.appleIb)).perform(click())
        pressBack()
        onView(withId(R.id.appleIb)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_bottle() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.bottleIb)).perform(click())
        onView(withId(R.id.bottleID)).check(matches(isDisplayed()))
    }
    @Test
    fun test_navigation_to_bottle_and_back() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.bottleIb)).perform(click())
        pressBack()
        onView(withId(R.id.bottleIb)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_can() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.canIb)).perform(click())
        onView(withId(R.id.canID)).check(matches(isDisplayed()))
    }
    @Test
    fun test_navigation_to_can_and_back() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.canIb)).perform(click())
        pressBack()
        onView(withId(R.id.canIb)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_plastic() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.plasticIb)).perform(click())
        onView(withId(R.id.plasticID)).check(matches(isDisplayed()))
    }
    @Test
    fun test_navigation_to_plastic_and_back() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.plasticIb)).perform(click())
        pressBack()
        onView(withId(R.id.plasticIb)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_paper() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.paperIb)).perform(click())
        onView(withId(R.id.paperID)).check(matches(isDisplayed()))
    }
    @Test
    fun test_navigation_to_paper_and_back() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.paperIb)).perform(click())
        pressBack()
        onView(withId(R.id.paperIb)).check(matches(isDisplayed()))
    }

    @Test
    fun test_intro_to_game() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.button_play)).perform(click())
        onView(withId(R.id.speedometerIv)).check(matches(isDisplayed()))
    }
    @Test
    fun test_intro_to_game_and_back(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.button_play)).perform(click())
        pressBack()
        onView(withId(R.id.button_play)).check(matches(isDisplayed()))
    }

    @Test
    fun test_intro_to_end_screen(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.button_play)).perform(click())
        onView(withId(R.id.endGame)).perform(click())
        onView(withId(R.id.home_end)).check(matches(isDisplayed()))
    }
    @Test
    fun test_intro_to_end_screen_and_back(){
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.button_start)).perform(click())
        onView(withId(R.id.button_play)).perform(click())
        onView(withId(R.id.endGame)).perform(click())
        pressBack()
        onView(withId(R.id.endGame)).check(matches(isDisplayed()))
    }
}