package com.example.testtask.domain.repo

interface AuthRepo {
    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String, age: Int)
    suspend fun setIsAuthorized(isAuthorized: Boolean)
    fun getIsAuthorized(): Boolean
}