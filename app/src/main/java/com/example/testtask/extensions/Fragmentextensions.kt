package com.example.testtask.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.findListenerByParent(): T {
    var fragment = this
    while (fragment.parentFragment != null) {
        fragment = fragment.requireParentFragment()
        if ((T::class.java.isInstance(fragment))) {
            return fragment as T
        }
    }
    return if (T::class.java.isInstance(activity)) activity as T
    else throw IllegalStateException(
        "Nor targetFragment, neither parentFragment (or activity) implements " +
                "listener ${T::class.java.simpleName}")
}

fun Fragment.showToast(text: String, length: Int = Toast.LENGTH_SHORT){
    context?.showToast(text,length)
}

fun Fragment.showToast(stringResId: Int, length: Int = Toast.LENGTH_SHORT){
    context?.showToast(stringResId,length)
}


fun Context.showToast(text: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,text,length).show()
}

fun Context.showToast(stringResId: Int, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,getString(stringResId),length).show()
}