package com.example.testtask.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PostsPage(
    val totalHits: Int,
    val hits: List<Post>
)
@Parcelize
data class Post(
    val id: Long,
    val type: PostType?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int,
    val previewHeight: Int,
    val largeImageURL: String?,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Long,
    val views: Int?,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    val authorName: String,
    val authorAvatarURL: String
): ListItem, Parcelable

enum class PostType{
    ALL,
    PHOTO,
    ILLUSTRATION,
    VECTOR
}