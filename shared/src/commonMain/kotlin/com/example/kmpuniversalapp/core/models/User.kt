package com.example.kmpuniversalapp.core.models

import kotlinx.serialization.Serializable

/**
 * 用户数据模型
 */
@Serializable
data class User(
    val id: String,
    val username: String,
    val email: String,
    val phone: String,
    val displayName: String,
    val avatar: String? = null,
    val isEmailVerified: Boolean = false,
    val isPhoneVerified: Boolean = false,
    val createdAt: Long,
    val lastLoginAt: Long,
    val preferences: Map<String, String> = emptyMap()
)

/**
 * 登录请求
 */
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

/**
 * 注册请求
 */
@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phone: String? = null,
    val displayName: String? = null
)

/**
 * 重置密码请求
 */
@Serializable
data class ResetPasswordRequest(
    val email: String
)

/**
 * 修改密码请求
 */
@Serializable
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
