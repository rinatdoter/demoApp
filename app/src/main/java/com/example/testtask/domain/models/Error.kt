package com.example.testtask.domain.models

import com.google.gson.annotations.SerializedName

data class ErrorList(
    @SerializedName("errors")
    var errors: List<Error>
)

data class Error(
    @SerializedName("message")
    var message: String,
    @SerializedName("field")
    val field: String
)
