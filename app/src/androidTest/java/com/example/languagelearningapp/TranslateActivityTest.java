package com.example.languagelearningapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.languagelearningapp.activity.TranslateActivity;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.widget.Button;


@RunWith(AndroidJUnit4.class)
public class TranslateActivityTest {
    @Rule
    public ActivityScenarioRule<TranslateActivity> activityScenarioRule =
            new ActivityScenarioRule<>(TranslateActivity.class);


    private SimpleIdlingResource idlingResource;

    @Before
    public void setUp() {
        // Tạo và đăng ký IdlingResource
        idlingResource = new SimpleIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);  // Đăng ký IdlingResource
    }

    @After
    public void tearDown() {
        // Hủy đăng ký IdlingResource
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
    @Test
    public void TestTranslateSuccess() throws InterruptedException {

        idlingResource.setIdleState(true);  // Đảm bảo IdlingResource được set thành idle

        // Nhập văn bản vào EditText
        onView(withId(R.id.input_edit_text)).perform(typeText("i love my school"));

        // Đảm bảo rằng IdlingResource sẽ cho phép ứng dụng thực thi các tác vụ cần thiết
        IdlingRegistry.getInstance().register(idlingResource);

        // Đảm bảo nút translate_btn có thể nhìn thấy (visible)
        onView(withId(R.id.translate_btn)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(Button.class); // Đảm bảo là Button
            }

            @Override
            public String getDescription() {
                return "Set translate_btn visibility to VISIBLE";
            }

            @Override
            public void perform(UiController uiController, View view) {
                // Kiểm tra nếu nút đang ở trạng thái GONE hoặc INVISIBLE, thay đổi thành VISIBLE
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        // Click vào nút translate
        onView(withId(R.id.translate_btn)).perform(click());

        // Đợi cho quá trình dịch hoàn tất và TextView cập nhật (sử dụng IdlingResource thay vì Thread.sleep)
        IdlingRegistry.getInstance().unregister(idlingResource);

        // Kiểm tra nội dung của TextView
        onView(withId(R.id.output_textview))
                .check(matches(withText("tôi yêu trường của tôi")));
    }
    @Test
    public void TestTranslateFail() throws InterruptedException {
        String input = "khong ton tai";
        idlingResource.setIdleState(true);  // Đảm bảo IdlingResource được set thành idle

        // Nhập văn bản vào EditText
        onView(withId(R.id.input_edit_text)).perform(typeText(input));

        // Đảm bảo rằng IdlingResource sẽ cho phép ứng dụng thực thi các tác vụ cần thiết
        IdlingRegistry.getInstance().register(idlingResource);

        // Đảm bảo nút translate_btn có thể nhìn thấy (visible)
        onView(withId(R.id.translate_btn)).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(Button.class); // Đảm bảo là Button
            }

            @Override
            public String getDescription() {
                return "Set translate_btn visibility to VISIBLE";
            }

            @Override
            public void perform(UiController uiController, View view) {
                // Kiểm tra nếu nút đang ở trạng thái GONE hoặc INVISIBLE, thay đổi thành VISIBLE
                if (view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });

        // Click vào nút translate
        onView(withId(R.id.translate_btn)).perform(click());

        // Đợi cho quá trình dịch hoàn tất và TextView cập nhật (sử dụng IdlingResource thay vì Thread.sleep)
        IdlingRegistry.getInstance().unregister(idlingResource);

        // Kiểm tra nội dung của TextView
        onView(withId(R.id.output_textview))
                .check(matches(withText(input)));
    }

}
