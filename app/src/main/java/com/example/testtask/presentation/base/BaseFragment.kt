package com.example.testtask.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import com.example.testtask.extensions.findListenerByParent
import com.example.testtask.extensions.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class BaseFragment<vm : BaseVM, viewBinding : ViewBinding>(
    private val vmClass: Class<vm>,
    inline val bindingCreator: (LayoutInflater) -> viewBinding
): Fragment() {

    protected lateinit var fragmentViewScope: CoroutineScope
    private var  _binding: viewBinding? = null
    protected val binding: viewBinding
        get() = _binding!!
    protected lateinit var vm: vm
    protected open val needToRetainView = false


    protected val loadingProgressDisplayable: LoadingProgressDisplayable by lazy {
        findListenerByParent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this)[vmClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingCreator(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentViewScope = CoroutineScope(Dispatchers.Main)
        setupViews()
        subscribeToLiveData()
    }

    protected open fun setupViews(){

    }

    protected open fun subscribeToLiveData() {
        vm.isLoading.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    true -> showLoading()
                    false -> hideLoading()
                }
            }
        }

        vm.error.observe(viewLifecycleOwner){
            it?.let { handleErrorEmission(it) }
        }
    }

    protected open fun handleErrorEmission(message: String){
       showToast(message,Toast.LENGTH_SHORT)
    }

    open fun showLoading(){
        loadingProgressDisplayable.shouldShowLoading(true)
    }

    open fun hideLoading(){
        loadingProgressDisplayable.shouldShowLoading(false)
    }

    protected fun shouldBlocScreenClick(shouldBlock: Boolean){
        loadingProgressDisplayable.shouldBlockClick(shouldBlock)
    }

    fun runDelayed(call: () -> Unit, delayInMillis: Long){
        fragmentViewScope.launch {
            delay(delayInMillis)
            call()
        }
    }

    override fun onDestroyView() {
        fragmentViewScope.cancel()
        if(!needToRetainView) _binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        runCatching { hideLoading() }
        super.onDestroy()
    }

    protected fun delayTransition(duration: Long = 200){
        runCatching {
            TransitionManager.beginDelayedTransition(
                binding.root as ViewGroup, AutoTransition()
                    .apply { this.duration = duration })
        }
    }
}