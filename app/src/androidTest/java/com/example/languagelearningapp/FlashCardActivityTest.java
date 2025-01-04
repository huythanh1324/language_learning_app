package com.example.languagelearningapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.Matchers.anyOf;

import static java.util.function.Predicate.not;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.activity.FlashCardActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FlashCardActivityTest {
    @Rule
    public ActivityScenarioRule<FlashCardActivity> activityScenarioRule =
            new ActivityScenarioRule<>(FlashCardActivity.class);

    @Before
    public void setUp() {
        Intents.init(); // Khởi tạo Espresso Intents
    }

    @After
    public void tearDown() {
        Intents.release(); // Giải phóng tài nguyên Espresso Intents
    }
    @Test
    public void AddNewCard () {
        onView(withId(R.id.englishInput)).perform(typeText("Do"));
        onView(withId(R.id.vietnameseInput)).perform(typeText("lam"));
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.flashCardText))
                .check(matches(anyOf(withText("Do"), withText("Lam"))));

    }
    @Test
    public void DeleteACard () throws InterruptedException {
        onView(withId(R.id.deleteWordInput)).perform(typeText("Elephant"));

        onView(withId(R.id.deleteButton)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.viewFlashCardListButton)).perform(click());

//        Thread.sleep(2000);
//        onView(withId(R.id.english_text))
//                .check(matches(hasDescendant(withText("Elephant")))); // Kiểm tra "Elephant" là con của một phần tử trong RecyclerView

    }

}
