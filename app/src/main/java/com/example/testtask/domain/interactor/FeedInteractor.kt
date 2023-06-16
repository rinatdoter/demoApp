package com.example.testtask.domain.interactor

import com.example.testtask.domain.models.PostsPage

interface FeedInteractor {
    suspend fun getPosts(
        query: String? = null,
        perPage: Int? = null,
        page: Int
    ): PostsPage
}