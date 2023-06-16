package com.example.testtask.presentation.main

import com.example.testtask.domain.interactor.AuthInteractor
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainFragmentVM @Inject constructor(
    private val authInteractor: AuthInteractor
): BaseVM() {

    fun decideScreen(){
        setEvent(when(authInteractor.isAuthorized){
            true ->  BaseEvent.OpenHome
            false -> BaseEvent.OpenAuthFlow
        })
    }
}