package com.example.testtask.di

import com.example.testtask.BuildConfig
import com.example.testtask.data.datasource.remote.AuthApi
import com.example.testtask.data.datasource.remote.BaseRequestInterceptor
import com.example.testtask.data.datasource.remote.FeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideAuthApi(
        @AuthRetrofit retrofit: Retrofit
    ): AuthApi = retrofit.create(AuthApi::class.java)

    @Provides
    fun provideNewsFeedApi(
        @RetrofitWithInterceptor retrofit: Retrofit
    ): FeedApi = retrofit.create(FeedApi::class.java)


    @Provides
    @Singleton
    @AuthRetrofit
    fun provideAuthRetrofit(
        @AuthOkHttpClient
        okhttpClient: OkHttpClient): Retrofit = createRetrofit(okhttpClient)

    @Provides
    @Singleton
    @RetrofitWithInterceptor
    fun provideRetrofitWithTokenChecker(
        @OkHttpClientWithInterceptor
        okhttpClient: OkHttpClient): Retrofit = createRetrofit(okhttpClient)


    @Provides
    @Singleton
    @AuthOkHttpClient
    fun provideOkHttpClient(
        requestInterceptor: BaseRequestInterceptor
    ): OkHttpClient =
        createOkHttpClientBuilder(
            requestInterceptor
        ).build()

    @Provides
    @Singleton
    @OkHttpClientWithInterceptor
    fun provideOkHttpClientWithTokenChecker(
        requestInterceptor: BaseRequestInterceptor
    ): OkHttpClient =
        createOkHttpClientBuilder(
            requestInterceptor
        ).build()




    private fun createOkHttpClientBuilder(vararg interceptors: Interceptor, authenticator: Authenticator? = null): OkHttpClient.Builder {
        val interceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        return OkHttpClient.Builder()
            .apply {
                connectTimeout(TIME_OUT_DURATION, TimeUnit.MINUTES)
                writeTimeout(TIME_OUT_DURATION, TimeUnit.MINUTES)
                readTimeout(TIME_OUT_DURATION, TimeUnit.MINUTES)
                interceptors.forEach {
                    addInterceptor(it)
                }

                authenticator?.let {
                    authenticator(it)
                }

                if (BuildConfig.DEBUG) {
                    addInterceptor(interceptor)
                }
            }
    }

    private fun createRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }


    companion object{
        const val TIME_OUT_DURATION = 1L
    }
}