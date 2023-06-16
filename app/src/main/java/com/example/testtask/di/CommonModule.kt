package com.example.testtask.di

import android.content.Context
import android.content.res.Resources
import com.example.testtask.domain.util.Dispatcher
import com.example.testtask.domain.util.DispatcherImpl
import com.example.testtask.domain.util.ServerErrorHandler
import com.example.testtask.domain.util.ServerErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher = DispatcherImpl()

    @Provides
    @Singleton
    fun provideServerErrorHandler(
        @ApplicationContext context: Context
    ): ServerErrorHandler = ServerErrorHandlerImpl(context)

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources

}