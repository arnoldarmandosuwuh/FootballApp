package com.gdk.arnold.footballapp.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.gdk.arnold.footballapp.R.id.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        delay()
        //Tampilan Awal
        //Menampilkan recycler view untuk next match
        onView(withId(rvEvent))
            .check(matches(isDisplayed()))
        onView(withId(rvEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(rvEvent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click())
        )

        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))

        //Ketika Add To Favorite diklik
        onView(withId(add_to_favorite)).perform(click())

        pressBack()
        delay()

        //Ketika diswipe down
        onView(withId(swipeRefresh)).perform(swipeDown())

        delay()

        //Ketika bottom navigasi untuk last match diklik
        onView(withId(navigation_last)).perform(click())
        delay()

        //Menampilkan recycler view untuk last match
        onView(withId(rvEvent))
            .check(matches(isDisplayed()))

        //Ketika item di recycle view diklik
        onView(withId(rvEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(rvEvent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click())
        )

        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))

        //Ketika Add To Favorite diklik
        onView(withId(add_to_favorite)).perform(click())

        pressBack()
        delay()

        //Ketika diswipe down
        onView(withId(swipeRefresh)).perform(swipeDown())

        delay()

        //Ketika bottom navigasi untuk favorite match diklik
        onView(withId(navigation_fav)).perform(click())
        delay()

        //Menampilkan recycler view untuk favorite match
        onView(withId(rvEvent))
            .check(matches(isDisplayed()))

        //Ketika item di recycle view diklik
        onView(withId(rvEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
        onView(withId(rvEvent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click())
        )

        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))

        //Ketika Add To Favorite diklik
        onView(withId(add_to_favorite)).perform(click())

        pressBack()
        delay()

        //Ketika diswipe down
        onView(withId(swipeRefresh)).perform(swipeDown())

        delay()

        //Ketika bottom navigasi untuk next match diklik
        onView(withId(navigation_upcoming)).perform(click())
        delay()

        //Menampilkan recycler view untuk last match
        onView(withId(rvEvent))
            .check(matches(isDisplayed()))

        //Ketika item di recycle view diklik
        onView(withId(rvEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(rvEvent)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click())
        )

        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))

        //Ketika Add To Favorite diklik
        onView(withId(add_to_favorite)).perform(click())

        pressBack()
        delay()

        //Ketika diswipe down
        onView(withId(swipeRefresh)).perform(swipeDown())

        delay()
    }

    private fun delay() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}