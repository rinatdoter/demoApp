package com.example.testtask.domain.util

interface ServerErrorHandler {
    fun onNoInternetConnectionError()

    fun onUnknownError()
    fun onTimeOutError()

    fun onCode500HttpException()
}