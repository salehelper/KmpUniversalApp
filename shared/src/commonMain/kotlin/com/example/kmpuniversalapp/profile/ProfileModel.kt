package com.example.kmpuniversalapp.profile

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val id: String,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val phone: String? = null,
    val bio: String? = null,
    val location: String? = null,
    val website: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val isVerified: Boolean = false,
    val extra: Map<String, String> = emptyMap()
)

@Serializable
data class ProfileSettings(
    val notifications: NotificationSettings,
    val privacy: PrivacySettings,
    val appearance: AppearanceSettings
)

@Serializable
data class NotificationSettings(
    val pushEnabled: Boolean = true,
    val emailEnabled: Boolean = true,
    val smsEnabled: Boolean = false
)

@Serializable
data class PrivacySettings(
    val profileVisible: Boolean = true,
    val emailVisible: Boolean = false,
    val phoneVisible: Boolean = false
)

@Serializable
data class AppearanceSettings(
    val theme: String = "system", // system, light, dark
    val language: String = "zh-CN",
    val fontSize: String = "medium" // small, medium, large
)
