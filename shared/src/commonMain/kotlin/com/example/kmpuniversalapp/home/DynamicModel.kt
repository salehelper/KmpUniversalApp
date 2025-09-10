package com.example.kmpuniversalapp.home

import kotlinx.serialization.Serializable

@Serializable
data class DynamicModel(
    val id: String,
    val title: String,
    val content: String,
    val type: String,
    val image: String? = null,
    val url: String? = null,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isTop: Boolean = false,
    val createdAt: String,
    val extra: Map<String, String> = emptyMap()
)
