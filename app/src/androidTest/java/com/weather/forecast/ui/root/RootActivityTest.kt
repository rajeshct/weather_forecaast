package com.weather.forecast.ui.root


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import androidx.test.runner.AndroidJUnit4
import com.weather.forecast.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RootActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(RootActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Test
    fun rootActivityTest() {
        pressBack()

        val button = onView(
            allOf(
                withId(R.id.btn_retry),
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout),
                        childAtPosition(
                            withId(R.id.nav_host),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val textView = onView(
            allOf(
                withId(R.id.tv_error), withText("Something went wrong at our end!"),
                childAtPosition(
                    allOf(
                        withId(R.id.constraint_layout),
                        childAtPosition(
                            withId(R.id.nav_host),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Something went wrong at our end!")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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
}
