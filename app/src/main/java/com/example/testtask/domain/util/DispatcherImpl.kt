package com.example.testtask.domain.util

import kotlinx.coroutines.Dispatchers


class DispatcherImpl : Dispatcher {
    override fun ui() = Dispatchers.Main
    override fun io() = Dispatchers.IO
    override fun default()  = Dispatchers.Default
    override fun unconfined() = Dispatchers.Unconfined
}