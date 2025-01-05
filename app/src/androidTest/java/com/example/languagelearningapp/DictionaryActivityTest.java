package com.example.languagelearningapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.activity.DictionaryActivity;
import com.example.languagelearningapp.activity.IntroActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DictionaryActivityTest {
    @Rule
    public ActivityScenarioRule<DictionaryActivity> activityScenarioRule =
            new ActivityScenarioRule<>(DictionaryActivity.class);
    @Test
    public void TestSearchSuccess () throws InterruptedException {
        onView(withId(R.id.serach_input)).perform(typeText("do"));

        onView(withId(R.id.search_btn)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.word_textview))
                .check(matches(withText("do")));
    }
    @Test
    public void TestSearchFail () throws InterruptedException {
        onView(withId(R.id.serach_input)).perform(typeText("Khong"));
        onView(withId(R.id.search_btn)).perform(click());
        Thread.sleep(2000);
//        Espresso.onView(withText("Cannot found the word"))
//                .inRoot(new ToastMatcher())
//                .check(matches(ViewMatchers.isDisplayed()));


    }
}
