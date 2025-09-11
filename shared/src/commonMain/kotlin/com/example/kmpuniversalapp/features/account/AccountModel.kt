package com.example.kmpuniversalapp.features.account

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val rememberMe: Boolean = false
)

@Serializable
data class LoginResponse(
    val success: Boolean,
    val token: String? = null,
    val user: UserModel? = null,
    val message: String? = null
)

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val user: UserModel? = null,
    val message: String? = null
)

@Serializable
data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val isVerified: Boolean = false,
    val createdAt: String,
    val lastLoginAt: String? = null
)

@Serializable
data class ForgotPasswordRequest(
    val email: String
)

@Serializable
data class ResetPasswordRequest(
    val token: String,
    val newPassword: String,
    val confirmPassword: String
)
