package com.example.languagelearningapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.allOf;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.ViewInteraction;

import static org.hamcrest.CoreMatchers.containsString;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.activity.QuizActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizActivityTest {
    @Rule
    public ActivityScenarioRule<QuizActivity> activityScenarioRule =
            new ActivityScenarioRule<>(QuizActivity.class);

    @Test
    public void TestQuizFail() throws InterruptedException {

        String incorrect = "wrong answer";
        onView(withId(R.id.answerEditText)).perform(typeText(incorrect));
        onView(withId(R.id.checkButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.resultText))
                .check(matches(withSubstring("Incorrect")));

    }
}