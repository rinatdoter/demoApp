package com.example.testtask.di

import android.content.Context
import com.example.testtask.data.datasource.local.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideAppPreferences(@ApplicationContext context: Context) = AppPreferences(context)
}