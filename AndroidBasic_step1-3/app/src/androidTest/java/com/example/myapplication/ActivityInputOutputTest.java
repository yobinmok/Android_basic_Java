package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import android.app.Activity;
import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {

//    @Rule
//    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
//            MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

//    @Test
//    public void activityLaunch() {
//        ActivityScenario<TwoActivities> scenario = ActivityScenario.launch(TwoActivities.class);
//        scenario.onActivity(
//                activity -> {
//                    // Test 1. TwoActivity에서 아무런 입력 없이 버튼을 눌러도 secondActivity로 전환되는지
//                    // button_main 버튼이 포함된 보기를 찾고 클릭을 수행하는 ViewAction 표현식
//                    onView(withId(R.id.button_main)).perform(click());
//                    // secondActivity 에 위치하는 text_head 뷰가 표시 되었는지 확인
//                    onView(withId(R.id.text_header)).check(matches(isDisplayed()));
//
//                    // Test 2. secondActivity -> TwoActivity
//                    onView(withId(R.id.button_second)).perform(click());
//                    onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()));
//                }
//        );
//    }

    @Test
    public void activityLaunch() {
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_header)).check(matches(isDisplayed()));
        onView(withId(R.id.button_second)).perform(click());
        onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()));
    }

    @Test
    public void textInputOutput() {
        onView(withId(R.id.editText_main)).perform(
                typeText("This is a test."));
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_message)).check(
                matches(withText("This is a test.")));
    }

}