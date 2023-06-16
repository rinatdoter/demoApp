package com.example.testtask.presentation.auth.register

import android.content.Context
import androidx.core.widget.addTextChangedListener
import com.example.testtask.databinding.FragmentRegistrationBinding
import com.example.testtask.extensions.findListenerByParent
import com.example.testtask.extensions.getText
import com.example.testtask.presentation.main.MainNavigateAble
import com.example.testtask.presentation.auth.AuthFlowListener
import com.example.testtask.presentation.auth.AuthVM
import com.example.testtask.presentation.base.AuthEvent
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment<AuthVM, FragmentRegistrationBinding>
    (
    AuthVM::class.java,
    {
        FragmentRegistrationBinding.inflate(it)
    }
) {

    companion object {
        fun newInstance() = RegistrationFragment()
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
            buttonRegister.setOnClickListener {
                vm.tryRegistering(
                    registrationInputEmail.getText(),
                    registrationInputPassword.getText(),
                    registrationInputAge.getText().toInt()
                )
            }
            registrationInputEmail.editText?.addTextChangedListener {
                toggleButtonEnabledState()
                vm.clearEmailError()
            }
            registrationInputPassword.editText?.addTextChangedListener {
                toggleButtonEnabledState()
                vm.clearPasswordError()
            }
            registrationInputAge.editText?.addTextChangedListener {
                toggleButtonEnabledState()
                vm.clearAgeError()
            }
        }
    }

    private fun toggleButtonEnabledState() {
        binding.run {
            buttonRegister.isEnabled =
                registrationInputEmail.getText().isNotEmpty() &&
                        registrationInputPassword.getText().isNotEmpty() &&
                        registrationInputAge.getText().isNotEmpty()
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.emailError.observe(viewLifecycleOwner) {
            binding.registrationInputEmail.error = it
        }

        vm.passwordError.observe(viewLifecycleOwner) {
            binding.registrationInputPassword.error = it
        }

        vm.ageError.observe(viewLifecycleOwner){
            binding.registrationInputAge.error = it
        }

        vm.event.observe(viewLifecycleOwner) {
            onNewEvent(it)
        }
    }

    private fun onNewEvent(it: BaseEvent?) {
        when (it) {
            is AuthEvent.RegistrationSuccess -> authFlowListener.onAuthFlowComplete()
            else -> {}
        }
    }
}