package com.example.testtask.domain.util
import android.content.Context
import com.example.testtask.R
import com.example.testtask.extensions.showToast
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class ServerErrorHandlerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
): ServerErrorHandler {

    override fun onNoInternetConnectionError() {
        context.showToast(context.getString(R.string.error_no_internet))
    }

    override fun onUnknownError() {
        context.showToast(context.getString(R.string.error_unknown))
    }

    override fun onTimeOutError() {
        context.showToast(context.getString(R.string.error_timeout))
    }

    override fun onCode500HttpException() {
        context.showToast(context.getString(R.string.server_error))
    }
}