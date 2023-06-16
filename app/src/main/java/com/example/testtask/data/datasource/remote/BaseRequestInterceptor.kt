package com.example.testtask.data.datasource.remote

import com.example.testtask.BuildConfig
import com.example.testtask.data.datasource.local.AppPreferences
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


@Singleton
open class BaseRequestInterceptor @Inject constructor(
    protected val prefs: AppPreferences
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()


        val requestBuilder =
            original.newBuilder()
                .url(newUrl)
                .addHeader("Content-Type", "application/json")



        performAdditionalActions(requestBuilder)
        return chain.proceed(requestBuilder.build())
    }

    open fun performAdditionalActions(requestBuilder: Request.Builder){}
}