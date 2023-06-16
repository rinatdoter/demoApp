package com.example.testtask.data.datasource.remote

interface AuthApi {

    suspend fun login(email: String, password: String)
    suspend fun register(
        email: String,
        password: String,
        age: Int
    )
}