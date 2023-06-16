package com.example.testtask.data.datasource.remote

import com.example.testtask.data.models.PostsPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    companion object{
        const val PER_PAGE = 15
        const val DEFAULT_QUERY = "cute+kittens"
    }

    @GET("api")
    suspend fun getPosts(
        @Query("q") query: String? = DEFAULT_QUERY,
        @Query("per_page") perPage: Int? = PER_PAGE,
        @Query("page") page: Int
    ): PostsPageDto
}