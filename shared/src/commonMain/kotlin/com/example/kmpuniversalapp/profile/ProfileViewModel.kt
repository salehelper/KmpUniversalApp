package com.example.kmpuniversalapp.profile

import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import com.example.kmpuniversalapp.core.BaseViewModel
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 个人中心ViewModel
 */
class ProfileViewModel(
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 用户信息
    private val _profile = MutableStateFlow<ProfileModel?>(null)
    val profile: StateFlow<ProfileModel?> = _profile.asStateFlow()
    
    // 设置信息
    private val _settings = MutableStateFlow<ProfileSettings?>(null)
    val settings: StateFlow<ProfileSettings?> = _settings.asStateFlow()
    
    // 加载状态和错误信息继承自BaseViewModel
    
    /**
     * 加载用户信息
     */
    suspend fun loadProfile() {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            // 模拟加载用户数据
            val mockProfile = getMockProfile()
            _profile.value = mockProfile
            
        } catch (e: Exception) {
            _errorMessage.value = "加载用户信息失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 加载设置信息
     */
    suspend fun loadSettings() {
        try {
            val mockSettings = getMockSettings()
            _settings.value = mockSettings
        } catch (e: Exception) {
            _errorMessage.value = "加载设置失败: ${e.message}"
        }
    }
    
    /**
     * 更新用户信息
     */
    suspend fun updateProfile(updatedProfile: ProfileModel) {
        try {
            _isLoading.value = true
            
            // 模拟更新用户信息
            _profile.value = updatedProfile
            
        } catch (e: Exception) {
            _errorMessage.value = "更新用户信息失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 更新设置
     */
    suspend fun updateSettings(updatedSettings: ProfileSettings) {
        try {
            _settings.value = updatedSettings
        } catch (e: Exception) {
            _errorMessage.value = "更新设置失败: ${e.message}"
        }
    }
    
    /**
     * 退出登录
     */
    suspend fun logout() {
        try {
            _profile.value = null
            _settings.value = null
        } catch (e: Exception) {
            _errorMessage.value = "退出登录失败: ${e.message}"
        }
    }
    
    private fun getMockProfile(): ProfileModel {
        return ProfileModel(
            id = "user_001",
            name = "KMP开发者",
            email = "developer@kmp.com",
            avatar = "https://via.placeholder.com/150",
            phone = "+86 138****8888",
            bio = "专注于Kotlin Multiplatform开发",
            location = "北京",
            website = "https://kmp.example.com",
            createdAt = "2024-01-01T00:00:00Z",
            updatedAt = "2024-01-15T10:00:00Z",
            isVerified = true
        )
    }
    
    private fun getMockSettings(): ProfileSettings {
        return ProfileSettings(
            notifications = NotificationSettings(
                pushEnabled = true,
                emailEnabled = true,
                smsEnabled = false
            ),
            privacy = PrivacySettings(
                profileVisible = true,
                emailVisible = false,
                phoneVisible = false
            ),
            appearance = AppearanceSettings(
                theme = "system",
                language = "zh-CN",
                fontSize = "medium"
            )
        )
    }
}
