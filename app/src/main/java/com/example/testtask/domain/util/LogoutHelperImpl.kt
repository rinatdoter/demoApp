package com.example.testtask.domain.util

import android.content.Context
import android.content.Intent
import com.example.testtask.data.datasource.local.AppPreferences
import com.example.testtask.presentation.main.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject
import javax.inject.Singleton

@Bound
@Singleton
class LogoutHelperImpl @Inject constructor(
    private val prefs: AppPreferences,
    @ApplicationContext private val context: Context
): LogoutHelper {
    override fun logout() {
        prefs.clear()

        context.startActivity(
            Intent(context, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                       }

        )
    }
}