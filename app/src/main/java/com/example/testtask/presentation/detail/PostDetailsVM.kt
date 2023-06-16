package com.example.testtask.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.example.testtask.domain.models.Post
import com.example.testtask.presentation.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostDetailsVM @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseVM() {

    companion object{
        const val POST_DETAIL_PARAMS = "PostDetailsVM.params"
    }

    val post = savedStateHandle.getLiveData<Post>(POST_DETAIL_PARAMS)


}