package com.example.testtask.domain.interactor

import com.example.testtask.domain.models.PostsPage
import com.example.testtask.domain.repo.FeedRepo
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject

@Bound
class FeedInteractorImpl @Inject constructor(
    private val feedRepo: FeedRepo
) : FeedInteractor {
    override suspend fun getPosts(
        query: String?,
        perPage: Int?,
        page: Int
    ): PostsPage {
        return feedRepo.getPosts(query,perPage,page)
    }
}