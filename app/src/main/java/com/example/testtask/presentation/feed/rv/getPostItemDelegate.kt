package com.example.testtask.presentation.feed.rv

import androidx.constraintlayout.widget.ConstraintSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.testtask.databinding.ItemPostBinding
import com.example.testtask.domain.models.ListItem
import com.example.testtask.domain.models.Post
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

interface PostClickListener {
    fun onClick(post: Post)
}

fun getPostItemDelegate(
    listener: PostClickListener
) =
    adapterDelegateViewBinding<Post, ListItem, ItemPostBinding>(
        { layoutInflater, parent ->
            ItemPostBinding.inflate(layoutInflater, parent, false)

        }
    ) {

        binding.run {
            root.setOnClickListener {
                listener.onClick(item)
            }
        }

        val constraintSet = ConstraintSet()

        fun adjustImageViewDimensions() {
            val width = item.imageWidth
            val height = item.imageHeight
            binding.run {
                val ratio = String.format("%d:%d", width, height)
                constraintSet.clone(binding.root)
                constraintSet.setDimensionRatio(binding.ivPreview.id, ratio)
                constraintSet.applyTo(binding.root)
                binding.ivPreview.requestLayout()
            }
        }

        bind {
            adjustImageViewDimensions()
            binding.run {
                Glide.with(context)
                    .load(item.largeImageURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivPreview)
                textView.text = item.authorName
            }
        }
    }