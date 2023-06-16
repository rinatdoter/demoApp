package com.example.testtask.domain.interactor

interface AuthInteractor {

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String, age: Int)
    val  isAuthorized: Boolean
}