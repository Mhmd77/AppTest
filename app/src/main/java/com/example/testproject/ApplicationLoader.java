package com.example.testproject;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class ApplicationLoader extends Application {
    public static volatile Handler applicationHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationHandler = new Handler(Looper.getMainLooper());
    }
}
