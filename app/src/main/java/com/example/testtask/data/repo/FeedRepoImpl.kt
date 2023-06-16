package com.example.testtask.data.repo

import com.example.testtask.data.datasource.remote.FeedApi
import com.example.testtask.data.models.toDomain
import com.example.testtask.domain.util.Dispatcher
import com.example.testtask.domain.models.PostsPage
import com.example.testtask.domain.repo.FeedRepo
import it.czerwinski.android.hilt.annotations.Bound
import javax.inject.Inject
import kotlinx.coroutines.withContext

@Bound
class FeedRepoImpl @Inject constructor(
    private val feedApi: FeedApi,
    private val dispatcher: Dispatcher
) : FeedRepo {

    override suspend fun getPosts(
        query: String?,
        perPage: Int?,
        page: Int
    ): PostsPage = withContext(dispatcher.io()) {

        feedApi.getPosts(page = page).toDomain()
    }
}