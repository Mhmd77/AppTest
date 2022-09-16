package com.example.testproject.utilities

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.ceil

object AndroidUtilities {
    var statusBarHeight = 0
    private var density = 1f
    fun fillStatusBarHeight(context: Context?) {
        if (context == null || statusBarHeight > 0) {
            return
        }
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
    }

    fun checkDisplaySize(context: Context) {
        try {
            density = context.resources.displayMetrics.density
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun dp(value: Float): Int {
        return if (value == 0f) {
            0
        } else ceil((density * value).toDouble())
            .toInt()
    }

    fun getScreenHeight(activity: Activity): Int {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }
}