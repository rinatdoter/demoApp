package com.example.testtask.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testtask.AGE_MAX_VALUE
import com.example.testtask.AGE_MIN_VALUE
import com.example.testtask.EMAIL_REGEX
import com.example.testtask.PASSWORD_MIN_LENGTH
import com.example.testtask.domain.interactor.AuthInteractor
import com.example.testtask.presentation.base.AuthEvent
import com.example.testtask.presentation.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val authInteractor: AuthInteractor
): BaseVM() {

    private val _emailError: MutableLiveData<String?> by lazy {
        MutableLiveData()
    }
    val emailError: LiveData<String?>
    get() = _emailError

    private val _passwordError: MutableLiveData<String?> by lazy {
        MutableLiveData()
    }
    val passwordError: LiveData<String?>
        get() = _passwordError

    private val _ageError: MutableLiveData<String?> by lazy {
        MutableLiveData()
    }
    val ageError: LiveData<String?>
        get() = _ageError


    fun tryLogin(email: String, password: String){
        if(validateEmail(email) && validatePassword(password)){
            launchWithErrorHandling({
                showLoading()
                authInteractor.login(email,password)
                setEvent(AuthEvent.LoginSuccess)
            })
        }
    }

    fun tryRegistering(email: String, password: String,age: Int){
        if(validateEmail(email) && validatePassword(password) && validateAge(age)){
            launchWithErrorHandling({
                showLoading()
                authInteractor.register(email,password,age)
                setEvent(AuthEvent.RegistrationSuccess)
            })
        }
    }

    private fun validateEmail(email: String): Boolean{
        return if(!email.matches(Regex(EMAIL_REGEX))){
            _emailError.value = "email invalid"
            false
        }
        else
            true
    }

    private fun validatePassword(password: String): Boolean{
        return if(password.length < PASSWORD_MIN_LENGTH){
            _passwordError.value = "password invalid"
            false
        }
        else
            true
    }

    private fun validateAge(age: Int): Boolean{
        return if(age < AGE_MIN_VALUE || age > AGE_MAX_VALUE){
            _ageError.value = "age invalid"
            false
        }
        else
            true
    }

    fun clearPasswordError(){ _passwordError.value = null }
    fun clearEmailError(){ _emailError.value = null }
    fun clearAgeError(){ _ageError.value = null }

}