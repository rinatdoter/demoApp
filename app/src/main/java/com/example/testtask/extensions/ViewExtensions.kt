package com.example.testtask.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getText(): String = editText?.text.toString() ?: ""