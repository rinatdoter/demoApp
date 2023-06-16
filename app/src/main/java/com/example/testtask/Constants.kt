package com.example.testtask

const val PASSWORD_MIN_LENGTH = 6
const val AGE_MIN_VALUE = 18
const val AGE_MAX_VALUE = 99
const val EMAIL_REGEX = ("^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
        + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$")