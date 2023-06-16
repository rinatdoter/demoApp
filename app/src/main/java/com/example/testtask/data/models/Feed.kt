package com.example.testtask.data.models

import com.example.testtask.domain.models.Post
import com.example.testtask.domain.models.PostType
import com.example.testtask.domain.models.PostsPage
import com.google.gson.annotations.SerializedName


data class PostsPageDto(
    val totalHits: Int,
    val hits: List<PostDto>
)

data class PostDto(
    val id: Long,
    val type: PostTypeDto?,
    val tags: String?,
    val previewURL: String?,
    val previewWidth: Int,
    val previewHeight: Int,
    val largeImageURL: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val imageSize: Long,
    val views: Int?,
    val downloads: Int,
    val likes: Int,
    val comments: Int,
    @SerializedName("user")
    val authorName: String,
    @SerializedName("userImageURL")
    val authorAvatarURL: String

)


enum class PostTypeDto {
    @SerializedName("all")
    ALL,
    @SerializedName("photo")
    PHOTO,
    @SerializedName("illustration")
    ILLUSTRATION,
    @SerializedName("vector")
    VECTOR
}

fun PostsPageDto.toDomain() = PostsPage(
    totalHits,
    hits.map { it.toDomain() }
)

fun PostDto.toDomain(): Post = Post(
    id,
    type?.toDomain(),
    tags,
    previewURL,
    previewWidth,
    previewHeight,
    largeImageURL,
    imageWidth,
    imageHeight,
    imageSize,
    views,
    downloads,
    likes,
    comments,
    authorName,
    authorAvatarURL
)

fun PostTypeDto?.toDomain(): PostType? = when (this) {
    PostTypeDto.PHOTO -> PostType.PHOTO
    PostTypeDto.ALL -> PostType.ALL
    PostTypeDto.ILLUSTRATION -> PostType.ILLUSTRATION
    PostTypeDto.VECTOR-> PostType.VECTOR
    else -> null
}