package com.example.languagelearningapp;

import androidx.test.espresso.IdlingResource;

public class SimpleIdlingResource implements IdlingResource {
    private ResourceCallback resourceCallback;
    private boolean isIdleNow = false;

    // Cập nhật trạng thái idle và thông báo callback khi đã idle
    public void setIdleState(boolean isIdle) {
        isIdleNow = isIdle;
        if (isIdleNow && resourceCallback != null) {
            resourceCallback.onTransitionToIdle(); // Chuyển trạng thái idle và thông báo
        }
    }

    @Override
    public String getName() {
        return this.getClass().getName(); // Trả về tên lớp
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow;  // Trả về trạng thái idle (true nếu idle)
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;  // Đăng ký callback để thông báo khi trạng thái chuyển thành idle
    }
}
