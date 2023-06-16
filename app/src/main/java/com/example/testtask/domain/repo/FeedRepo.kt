package com.example.testtask.domain.repo

import com.example.testtask.domain.models.PostsPage

interface FeedRepo {
    suspend fun getPosts(
        query: String? = null,
        perPage: Int? = null,
        page: Int
    ): PostsPage
}