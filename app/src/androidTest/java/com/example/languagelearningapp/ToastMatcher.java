package com.example.languagelearningapp;

import android.os.IBinder;
import android.view.WindowManager;
import android.view.View;
import androidx.test.espresso.Root;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ToastMatcher extends TypeSafeMatcher<Root> {

    @Override
    public void describeTo(Description description) {
        description.appendText("is toast");
    }

    @Override
    public boolean matchesSafely(Root root) {
        View decorView = root.getDecorView();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) decorView.getLayoutParams();

        // Kiểm tra loại cửa sổ (Window Type)
        if (layoutParams != null &&
                (layoutParams.type == WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY ||
                        layoutParams.type == WindowManager.LayoutParams.TYPE_TOAST)) {

            // Kiểm tra xem WindowToken và ApplicationToken có khớp nhau không
            IBinder windowToken = decorView.getWindowToken();
            IBinder appToken = decorView.getApplicationWindowToken();
            return windowToken == appToken;
        }

        return false;
    }
}

