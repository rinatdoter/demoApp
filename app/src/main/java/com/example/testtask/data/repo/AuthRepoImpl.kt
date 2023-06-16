package com.example.testtask.data.repo

import com.example.testtask.data.datasource.local.AppPreferences
import com.example.testtask.data.datasource.remote.AuthApi
import com.example.testtask.domain.util.Dispatcher
import com.example.testtask.domain.repo.AuthRepo
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Bound
class AuthRepoImpl @Inject constructor(
    private val authApi: AuthApi,
    private val appPreferences: AppPreferences,
    private val dispatcher: Dispatcher
): AuthRepo {
    override suspend fun login(email: String, password: String)  =
        withContext(dispatcher.io()){
            delay(1000)
        }

    override suspend fun register(
        email: String,
        password: String,
        age: Int
    ) = withContext(dispatcher.io()){
        delay(1000)
    }

    override suspend fun setIsAuthorized(isAuthorized: Boolean) {
        appPreferences.isAuthorized = isAuthorized
    }

    override fun getIsAuthorized() = appPreferences.isAuthorized

}