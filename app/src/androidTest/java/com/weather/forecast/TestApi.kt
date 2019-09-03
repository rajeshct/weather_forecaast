package com.weather.forecast

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.weather.forecast.ui.root.RootActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class TestApiFlow {

    private var mockServer: MockWebServer? = null

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RootActivity::class.java, false, false)

    @Before
    fun before() {
        mockServer = MockWebServer()
        mockServer?.start(8080)
    }

    @Test
    fun testApiFlow() {
        mockServer?.setDispatcher(MockResonseDispatcher().RequestDispatcher())
        mActivityTestRule.launchActivity(Intent())
        val textView2 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.tv_location), ViewMatchers.withText("Gurugram"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.constraint_layout),
                        childAtPosition(
                            ViewMatchers.withId(R.id.nav_host),
                            0
                        )
                    ),
                    1
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView2.check(ViewAssertions.matches(ViewMatchers.withText("Gurugram")))

        val textView3 = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.tv_day), ViewMatchers.withText("Tuesday"),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.recycler_view),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView3.check(ViewAssertions.matches(ViewMatchers.withText("Tuesday")))
    }


    private fun childAtPosition(parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockServer?.shutdown()
    }


}