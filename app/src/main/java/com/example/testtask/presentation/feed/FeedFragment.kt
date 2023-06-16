package com.example.testtask.presentation.feed

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.databinding.FragmentFeedBinding
import com.example.testtask.domain.models.Post
import com.example.testtask.extensions.findListenerByParent
import com.example.testtask.presentation.main.MainNavigateAble
import com.example.testtask.presentation.base.BaseDelegateAdapter
import com.example.testtask.presentation.base.BaseEvent
import com.example.testtask.presentation.base.BaseFragment
import com.example.testtask.presentation.detail.PostDetailsFragment
import com.example.testtask.presentation.feed.rv.PostClickListener
import com.example.testtask.presentation.feed.rv.getPostItemDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment: BaseFragment<FeedVM,FragmentFeedBinding>(
    FeedVM::class.java,{
        FragmentFeedBinding.inflate(it)
    }
), PostClickListener {

    companion object{
        fun newInstance() = FeedFragment()
    }

    lateinit var mainNavigateAble: MainNavigateAble

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainNavigateAble = findListenerByParent()
    }

    private val feedAdapter by lazy {
        BaseDelegateAdapter(
            getPostItemDelegate(this)
        )
    }

    override fun setupViews() {
        binding.run {
            rvFeed.run {
                adapter = feedAdapter
                addOnScrollListener( object: RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if(!recyclerView.canScrollVertically(1))
                            vm.onLoadMoreCallback()
                    }
                })
            }
            buttonLogout.setOnClickListener {
                vm.logout()
            }

            swipeRefresh.setOnRefreshListener { vm.refreshFeed() }
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.posts.observe(viewLifecycleOwner){
            it?.let {
                feedAdapter.submitItems(it)
            }
        }

        vm.event.observe(viewLifecycleOwner){
            onNewEvent(it)
        }
    }

    private fun onNewEvent(it: BaseEvent?) {
        when (it) {
            is BaseEvent.HideSwipeRefresh -> {
                binding.swipeRefresh.isRefreshing = false
            }
            else -> {}
        }
    }

    override fun onClick(post: Post) {
        mainNavigateAble.navigate(PostDetailsFragment.newInstance(post))
    }

}