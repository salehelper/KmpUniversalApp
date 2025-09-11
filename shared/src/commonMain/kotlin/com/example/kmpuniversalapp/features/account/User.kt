package com.example.kmpuniversalapp.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String? = null
)

@Serializable
data class ApiResponse<T>(
    val data: T,
    val message: String,
    val success: Boolean
)
