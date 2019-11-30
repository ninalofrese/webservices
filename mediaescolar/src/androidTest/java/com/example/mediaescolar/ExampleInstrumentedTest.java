package com.example.mediaescolar;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testeActivityCalculo() {
        onView(withId(R.id.bimestre1)).perform(typeText("10"));
        onView(withId(R.id.bimestre2)).perform(typeText("8.5"));
        onView(withId(R.id.bimestre3)).perform(typeText("10"));
        onView(withId(R.id.bimestre4)).perform(typeText("7.5"));

        onView(withId(R.id.calcular)).perform(click());

        onView(withText("Nota final: 9.0")).check(matches(isDisplayed()));
    }


}
