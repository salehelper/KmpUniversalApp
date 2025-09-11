package com.example.kmpuniversalapp.features.home

import kotlinx.serialization.Serializable

@Serializable
data class BannerModel(
    val id: String,
    val title: String,
    val image: String,
    val url: String? = null,
    val sort: Int = 0,
    val isActive: Boolean = true,
    val createdAt: String,
    val extra: Map<String, String> = emptyMap()
)
