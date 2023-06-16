package com.example.testtask.presentation.auth.login

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.example.testtask.databinding.FragmentLoginBinding
import com.example.testtask.extensions.findListenerByParent
import com.example.testtask.extensions.getText
import com.example.testtask.presentation.main.MainNavigateAble
import com.example.testtask.presentation.auth.AuthFlowListener
import com.example.testtask.presentation.auth.AuthVM
import com.example.testtask.presentation.auth.register.RegistrationFragment
import com.example.testtask.presentation.base.AuthEvent
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<AuthVM,FragmentLoginBinding>(
    AuthVM::class.java,
    {
        FragmentLoginBinding.inflate(it)
    }
) {

    companion object{
        fun newInstance() = LoginFragment()
    }

    private lateinit var authFlowListener: AuthFlowListener
    private lateinit var mainNavigateAble: MainNavigateAble

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authFlowListener = findListenerByParent()
        mainNavigateAble = findListenerByParent()
    }

    override fun setupViews() {
        super.setupViews()
        binding.run {
            buttonLogin.setOnClickListener {
                vm.tryLogin(
                    loginInputEmail.getText(),
                    loginInputPassword.getText()
                )
            }

            loginButtonRegister.setOnClickListener {
                mainNavigateAble.navigate(RegistrationFragment.newInstance())
            }

            loginInputEmail.editText?.addTextChangedListener {
                toggleButtonEnabledState()
                vm.clearEmailError()
            }
            loginInputPassword.editText?.addTextChangedListener {
                toggleButtonEnabledState()
                vm.clearPasswordError()
            }
        }
    }

    private fun toggleButtonEnabledState(){
        binding.run {
            buttonLogin.isEnabled =
                loginInputEmail.getText().isNotEmpty() &&
                        loginInputPassword.getText().isNotEmpty()
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.emailError.observe(viewLifecycleOwner) {
            binding.loginInputEmail.error = it
        }

        vm.passwordError.observe(viewLifecycleOwner) {
            binding.loginInputPassword.error = it
        }

        vm.event.observe(viewLifecycleOwner){
            onNewEvent(it)
        }
    }

    private fun onNewEvent(it: BaseEvent?) {
        when (it) {
            is AuthEvent.LoginSuccess -> authFlowListener.onAuthFlowComplete()
            else -> {}
        }
    }
}