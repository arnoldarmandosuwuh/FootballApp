package com.aas.footballapp.search

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.aas.footballapp.R.id.*
import com.aas.footballapp.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {

       onView(withId(action_search)).perform(click())

        onView(isAssignableFrom(EditText::class.java)).perform(
            typeText("man united"),
            pressImeActionButton()
        )

        delay()

        onView(withId(rv_search)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        delay()

        onView(withId(fav)).perform(click())

        delay()

        Espresso.pressBack()

        delay()

        Espresso.pressBack()

        delay()

        Espresso.pressBack()

        delay()

    }

    private fun delay() {
        try {
            Thread.sleep(4000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}