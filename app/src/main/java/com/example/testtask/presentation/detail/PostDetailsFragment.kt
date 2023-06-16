package com.example.testtask.presentation.detail

import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.testtask.R
import com.example.testtask.databinding.FragmentPostDetailsBinding
import com.example.testtask.domain.models.Post
import com.example.testtask.presentation.base.BaseFragment
import com.example.testtask.presentation.detail.PostDetailsVM.Companion.POST_DETAIL_PARAMS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment: BaseFragment<PostDetailsVM, FragmentPostDetailsBinding>(
    PostDetailsVM::class.java,
    {
        FragmentPostDetailsBinding.inflate(it)
    }
) {

    companion object{
        fun newInstance(post: Post):PostDetailsFragment{
            return PostDetailsFragment().apply {
                arguments = bundleOf(POST_DETAIL_PARAMS to post)
            }
        }
    }

    override fun subscribeToLiveData() {
        super.subscribeToLiveData()
        vm.post.observe(viewLifecycleOwner){
            setupPostData(it)
        }
    }

    private fun setupPostData(post: Post){
        adjustImageViewDimensions(post)
        loadImage(post.largeImageURL)
        fillInfoTextViews(post)
    }

    private fun adjustImageViewDimensions(post: Post) {
        val constraintSet = ConstraintSet()
        val width = post.imageWidth
        val height = post.imageHeight
        binding.run {
            val ratio = String.format("%d:%d", width, height)
            constraintSet.clone(binding.root)
            constraintSet.setDimensionRatio(binding.imageView.id, ratio)
            constraintSet.applyTo(binding.root)
            binding.imageView.requestLayout()
        }
    }

    private fun loadImage(url: String?){
        Glide.with(requireContext())
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageView)
    }

    private fun fillInfoTextViews(post: Post){
        binding.run {
            tvSize.text = getString(R.string.size, post.imageSize)
            tvType.text = getString(R.string.type, post.type.toString())
            tvTags.text = getString(R.string.tags, post.tags)

            tvAuthor.text = getString(R.string.author, post.authorName)
            tvViews.text = getString(R.string.views, post.views)
            tvLikes.text = getString(R.string.likes, post.likes)
            tvComments.text = getString(R.string.comments, post.comments)
            tvDownloads.text = getString(R.string.downloads, post.downloads)
        }
    }
}