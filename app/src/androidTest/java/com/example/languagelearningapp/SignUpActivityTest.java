package com.example.languagelearningapp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.After;
import org.junit.Test;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.activity.SignUpActivity;
import com.example.languagelearningapp.activity.MainActivity;

import org.junit.Before;

import org.junit.runner.RunWith;
import org.junit.Rule;

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityScenarioRule =
            new ActivityScenarioRule<>(SignUpActivity.class);

    @Before
    public void setUp() {
        Intents.init(); // Khởi tạo Espresso Intents
    }

    @After
    public void tearDown() {
        Intents.release(); // Giải phóng tài nguyên Espresso Intents
    }

    @Test
    public void testSignUpSuccessNavigates() throws InterruptedException {
        // Nhập giá trị vào các trường
        onView(withId(R.id.sign_up_email)).perform(typeText("ndlanh3@gmail.com"));
        onView(withId(R.id.sign_up_name)).perform(typeText("Aiu"));
        onView(withId(R.id.sign_up_password)).perform(typeText("Anh22052002"));
        onView(withId(R.id.sign_up_password2)).perform(typeText("Anh22052002"));

        // Click vào nút đăng ký
        onView(withId(R.id.sign_up_btn)).perform(click());

        // Kiểm tra Intent chuyển đến MainActivity
        //intended(hasComponent(MainActivity.class.getName()));

        Thread.sleep(4000);
        // Kiểm tra MainActivity hiển thị tên người dùng
        onView(withId(R.id.greeting_text))
                .check(matches(withText("Hello, Aiu"))); // Kiểm tra tên người dùng trong greetingText
    }
    @Test
    public void testSignUpWithExistedAccount() throws InterruptedException {
        // Nhập giá trị vào các trường
        onView(withId(R.id.sign_up_email)).perform(typeText("ndlanh2@gmail.com"));
        onView(withId(R.id.sign_up_name)).perform(typeText("Aiu"));
        onView(withId(R.id.sign_up_password)).perform(typeText("Anh22052002"));
        onView(withId(R.id.sign_up_password2)).perform(typeText("Anh22052002"));

        // Click vào nút đăng ký
        onView(withId(R.id.sign_up_btn)).perform(click());

        // Kiểm tra Intent chuyển đến MainActivity
        //intended(hasComponent(MainActivity.class.getName()));

        Thread.sleep(4000);
        // Kiểm tra MainActivity hiển thị tên người dùng
        onView(withId(R.id.greeting_text))
                .check(matches(withText("Hello, Aiu"))); // Kiểm tra tên người dùng trong greetingText
    }

}