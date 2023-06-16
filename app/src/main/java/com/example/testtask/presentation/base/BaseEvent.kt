package com.example.testtask.presentation.base


sealed class BaseEvent {
    object OpenAuthFlow: BaseEvent()
    object OpenHome: BaseEvent()
    class ShowToast(val message: String): BaseEvent()
    object HideSwipeRefresh: BaseEvent()
}

sealed class AuthEvent: BaseEvent(){
    object LoginSuccess: AuthEvent()
    object RegistrationSuccess: AuthEvent()
}