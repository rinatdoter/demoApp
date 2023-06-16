package com.example.testtask.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext

class AppPreferences(@ApplicationContext context: Context) {

    private val masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREF_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var isAuthorized: Boolean
        get() {
             return prefs.getBoolean(IS_AUTHORIZED, false)
        }
        set(value) {
            prefs.edit {
                putBoolean(IS_AUTHORIZED, value)
            }
        }

    fun clear() {
        isAuthorized = false
    }

    companion object {
        const val PREF_NAME = "com.example.testtask.prefs"
        const val IS_AUTHORIZED = "com.example.testtask.isAthorized"
    }
}