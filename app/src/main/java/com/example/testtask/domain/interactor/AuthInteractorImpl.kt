package com.example.testtask.domain.interactor

import com.example.testtask.domain.repo.AuthRepo
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject

@Bound
class AuthInteractorImpl @Inject constructor(
    private val authRepo: AuthRepo
) : AuthInteractor {
    override suspend fun login(email: String, password: String) {
        authRepo.login(email, password).also {
            authRepo.setIsAuthorized(true)
        }
    }

    override suspend fun register(email: String, password: String, age: Int) {
        authRepo.register(email, password,age).also {
            authRepo.setIsAuthorized(true)
        }
    }

    override val  isAuthorized: Boolean
    get() = authRepo.getIsAuthorized()
}