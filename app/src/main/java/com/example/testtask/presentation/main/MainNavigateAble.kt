package com.example.testtask.presentation.main

import androidx.fragment.app.Fragment

interface MainNavigateAble {
    fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean? = true,
        isPopNeeded: Boolean? = null
    )

    fun chain(vararg fragments: Fragment)

    fun navigateBack()

    fun popBackUntilFirst()
}