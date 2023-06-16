package com.example.testtask.presentation.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.testtask.R
import com.example.testtask.databinding.FragmentMainBinding
import com.example.testtask.extensions.addCallback
import com.example.testtask.presentation.auth.AuthFlowListener
import com.example.testtask.presentation.auth.login.LoginFragment
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseFragment
import com.example.testtask.presentation.base.LoadingProgressDisplayable
import com.example.testtask.presentation.feed.FeedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: BaseFragment<MainFragmentVM, FragmentMainBinding>(
MainFragmentVM::class.java,
{
    FragmentMainBinding.inflate(it)
}
), MainNavigateAble, LoadingProgressDisplayable, AuthFlowListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            vm.decideScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpOnBackPressedCallback()
    }


    override fun subscribeToLiveData() {
        vm.event.observe(viewLifecycleOwner){
            when(it){
                is BaseEvent.OpenAuthFlow -> openFragment(LoginFragment.newInstance())
                is BaseEvent.OpenHome -> openFragment(FeedFragment.newInstance())
                else ->{}
            }
        }
    }

    private fun openFragment(
        fragment: Fragment,
        addToBackStack: Boolean? =  false,
        isPopNeeded: Boolean? = false
    ) {
        childFragmentManager.apply {
            if(isPopNeeded == true){
                popBackStack()
            }
        }
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .apply {
                if (addToBackStack == true) {
                    addToBackStack(null)
                }
            }
            .commit()
    }

    private fun clearBackStack() {
        if (childFragmentManager.backStackEntryCount > 0) {
            childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onAuthFlowComplete() {
        clearBackStack()
        openFragment(FeedFragment.newInstance())
    }

    private fun setUpOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), viewLifecycleOwner) {
            if (childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
                true
            } else {
                activity?.finish()
                false
            }
        }
    }

    override fun navigate(
        fragment: Fragment,
        addToBackStack: Boolean?,
        isPopNeeded: Boolean?,
    ) {
        openFragment(fragment,addToBackStack,isPopNeeded)
    }

    override fun chain(vararg fragments: Fragment) {
        fragments.forEach {
            childFragmentManager.beginTransaction()
                .replace(R.id.main_container,it)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun navigateBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun popBackUntilFirst() {
        if(childFragmentManager.backStackEntryCount > 0){
            val firstEntryId = childFragmentManager.getBackStackEntryAt(0).id
            childFragmentManager.popBackStackImmediate(firstEntryId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun shouldShowLoading(isLoading: Boolean) {
        binding.run {
            globalClickBlock.isVisible = isLoading
            progressBar.isVisible = isLoading
        }
    }

    override fun shouldBlockClick(needBlock: Boolean) {
        binding.globalClickBlock.isVisible = needBlock
    }
}