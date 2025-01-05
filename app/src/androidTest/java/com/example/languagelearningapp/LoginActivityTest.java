package com.example.languagelearningapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.R;
import com.example.languagelearningapp.activity.IntroActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<IntroActivity> activityScenarioRule =
            new ActivityScenarioRule<>(IntroActivity.class);

    @Test
    public void testLoginSuccess() throws InterruptedException {
        // Nhập email và mật khẩu hợp lệ
        onView(withId(R.id.login_email)).perform(typeText("ndlanh@gmail.com"));
        onView(withId(R.id.login_password)).perform(typeText("anh1234"));

        // Nhấn nút đăng nhập
        onView(withId(R.id.login_btn)).perform(click());


        // Kiểm tra thông báo thành công hoặc chuyển màn hình
        Thread.sleep(2000);

        onView(withId(R.id.greeting_text))
                .check(matches(withText("Hello, Anh")));
    }
    // Kiểm tra login fail with invalid email
    @Test
    public void testSignUpFail() throws InterruptedException {
        onView(withId(R.id.login_email)).perform(typeText("wrong@gmail.com"));
        onView(withId(R.id.login_password)).perform(typeText("anh1234"));
        onView(withId(R.id.login_btn)).perform(click());
        Thread.sleep(2000);
    }
    // Kiểm tra login fail with valid email và invalid password
    @Test
    public void testSignUpFailWithValidEmail() throws InterruptedException {
        onView(withId(R.id.login_email)).perform(typeText("ndlanh@gmail.com"));
        onView(withId(R.id.login_password)).perform(typeText("wrongpass123"));
        onView(withId(R.id.login_btn)).perform(click());
        Thread.sleep(2000);
    }
}
