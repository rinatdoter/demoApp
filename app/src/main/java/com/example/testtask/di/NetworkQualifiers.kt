package com.example.testtask.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitWithInterceptor