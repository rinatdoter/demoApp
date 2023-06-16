package com.example.testtask.extensions

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.lifecycle.LifecycleOwner

fun OnBackPressedDispatcher.addCallback(
    activity: Activity,
    owner: LifecycleOwner? = null,
    enabled: Boolean = true,
    onBackPressed: OnBackPressedCallback.() -> Boolean
): OnBackPressedCallback {
    return addCallback(owner, enabled) {
        val intercepted = onBackPressed.invoke(this)
        if (!intercepted) {
            isEnabled = false
            activity.onBackPressed()
            isEnabled = true
        }
    }
}