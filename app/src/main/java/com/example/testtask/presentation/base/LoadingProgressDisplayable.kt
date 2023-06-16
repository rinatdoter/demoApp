package com.example.testtask.presentation.base

interface LoadingProgressDisplayable {
    fun shouldShowLoading(isLoading: Boolean)
    fun shouldBlockClick(needBlock: Boolean)
}