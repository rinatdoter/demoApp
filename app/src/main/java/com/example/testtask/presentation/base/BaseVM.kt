package com.example.testtask.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.SingleLiveEvent
import com.example.testtask.domain.util.ServerErrorHandler
import com.example.testtask.extensions.serializedMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
open class BaseVM @Inject constructor() : ViewModel() {

    @Inject
    lateinit var serverErrorHandler: ServerErrorHandler

    protected val _event: SingleLiveEvent<BaseEvent?> by lazy {
        SingleLiveEvent()
    }
    val event: LiveData<BaseEvent?>
        get() = _event

    protected val _error: MutableLiveData<String?> by lazy {
        MutableLiveData()
    }
    val error: LiveData<String?>
        get() = _error

    private val _isLoading: MutableLiveData<Boolean?> by lazy {
        MutableLiveData()
    }
    val isLoading: LiveData<Boolean?>
        get() = _isLoading

    protected fun launchWithErrorHandling(
        call: suspend (scope: CoroutineScope) -> Unit,
        scope: CoroutineScope? = viewModelScope,
        finally: (suspend () -> Unit)? = null,
        handleError: ((e: Throwable) -> Unit)? = null,
        isLoadingFlow: MutableStateFlow<Boolean>? = null
    ): Job {
        return scope?.launch {
            try {
                isLoadingFlow?.value = true
                call(scope)
            } catch (e: Throwable) {
                handleError?.invoke(e) ?: this@BaseVM.handleError(e)
            } finally {
                hideLoading()
                finally?.invoke()
                isLoadingFlow?.value = false
            }
        } ?: Job()
    }

    open fun handleError(e: Throwable, flowToEmitError: MutableStateFlow<String?>? = null) {
        if(e is HttpException && e.code() in 500 .. 502){
            serverErrorHandler.onCode500HttpException()
            return
        }

        when (e) {
            is SocketTimeoutException -> serverErrorHandler.onTimeOutError()
            is UnknownHostException -> serverErrorHandler.onNoInternetConnectionError()
            is HttpException ->{
                e.serializedMessage?.let { message ->
                    if(flowToEmitError != null)
                        flowToEmitError.value = message
                    else
                        _error.value = message
                } ?:
                serverErrorHandler.onUnknownError()
            }
            is CancellationException -> throw e
            else ->  serverErrorHandler.onUnknownError()
        }
    }

    protected open fun hideLoading(){
        _isLoading.value = false
    }

    protected open fun showLoading(){
        _isLoading.value = true
    }

    protected fun setEvent(event: BaseEvent){
        _event.setValue(event)
    }
}