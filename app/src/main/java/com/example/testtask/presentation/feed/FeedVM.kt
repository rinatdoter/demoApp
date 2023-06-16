package com.example.testtask.presentation.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.domain.interactor.FeedInteractor
import com.example.testtask.domain.models.Post
import com.example.testtask.domain.util.LogoutHelper
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedVM @Inject constructor(
    private val feedInteractor: FeedInteractor,
    private val logoutHelper: LogoutHelper
): BaseVM() {

    private var currentPage = 0

    private var totalPosts = 0
    private var totalLoadedPosts = 0

    private val _posts = MutableLiveData<List<Post>?>()
    val posts: LiveData<List<Post>?>
    get() = _posts

    init {
        showLoading()
        loadFirstBatch()
    }

    fun refreshFeed(){
        totalPosts = 0
        currentPage = 0
        totalLoadedPosts = 0
        loadFirstBatch()
    }

    private fun loadFirstBatch() {
        launchWithErrorHandling({
            feedInteractor.getPosts(page = ++currentPage).let {
                _posts.value = it.hits
                totalPosts = it.totalHits
                totalLoadedPosts = it.hits.count()
            }

        }, handleError = {
            --currentPage
            super.handleError(it, null)
        }, finally = {
            setEvent(BaseEvent.HideSwipeRefresh)
        })
    }


    fun onLoadMoreCallback(){
        if(isLoading.value == false && areThereAnyPostsLeftToLoad())
            loadNextPage()
    }

    private fun areThereAnyPostsLeftToLoad() = totalPosts > totalLoadedPosts

    private fun loadNextPage(){
        launchWithErrorHandling({
            showLoading()
            val newPage = feedInteractor.getPosts(page = ++currentPage)
            _posts.value = mutableListOf<Post>().apply {
                addAll(_posts.value ?: emptyList())
                addAll(newPage.hits)
            }
        },
            handleError = {
                --currentPage
                super.handleError(it,null)
            })
    }

    fun logout(){
        logoutHelper.logout()
    }
}