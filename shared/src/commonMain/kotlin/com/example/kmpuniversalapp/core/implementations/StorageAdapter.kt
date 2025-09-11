package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.utils.storage.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * 存储适配器
 * 让DataStoreManager实现IStorage接口
 */
class StorageAdapter(
    private val dataStoreManager: DataStoreManager
) : IStorage {
    
    override suspend fun putString(key: String, value: String) {
        when (key) {
            "user_token" -> dataStoreManager.saveUserToken(value)
            "user_info" -> dataStoreManager.saveUserInfo(value)
            "app_settings" -> dataStoreManager.saveAppSettings(value)
            "theme_mode" -> dataStoreManager.saveThemeMode(value)
            "language" -> dataStoreManager.saveLanguage(value)
            "search_history" -> dataStoreManager.saveSearchHistory(value)
            else -> {
                // 对于其他键，使用通用的存储方式
                // 这里可以扩展DataStoreManager来支持任意键值对
                throw UnsupportedOperationException("Key '$key' is not supported by DataStoreManager")
            }
        }
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return when (key) {
            "user_token" -> dataStoreManager.getUserToken().first() ?: defaultValue
            "user_info" -> dataStoreManager.getUserInfo().first() ?: defaultValue
            "app_settings" -> dataStoreManager.getAppSettings().first() ?: defaultValue
            "theme_mode" -> dataStoreManager.getThemeMode().first() ?: defaultValue
            "language" -> dataStoreManager.getLanguage().first() ?: defaultValue
            "search_history" -> dataStoreManager.getSearchHistory().first() ?: defaultValue
            else -> {
                // 对于其他键，返回默认值
                defaultValue
            }
        }
    }

    override suspend fun remove(key: String) {
        when (key) {
            "user_token" -> dataStoreManager.clearUserToken()
            else -> {
                // 对于其他键，使用通用的清除方式
                // 这里可以扩展DataStoreManager来支持任意键值对的删除
                throw UnsupportedOperationException("Key '$key' removal is not supported by DataStoreManager")
            }
        }
    }

    override suspend fun contains(key: String): Boolean {
        return when (key) {
            "user_token" -> dataStoreManager.getUserToken().first() != null
            "user_info" -> dataStoreManager.getUserInfo().first() != null
            "app_settings" -> dataStoreManager.getAppSettings().first() != null
            "theme_mode" -> dataStoreManager.getThemeMode().first() != null
            "language" -> dataStoreManager.getLanguage().first() != null
            "search_history" -> dataStoreManager.getSearchHistory().first() != null
            else -> false
        }
    }

    override fun observeString(key: String, defaultValue: String): Flow<String> {
        return when (key) {
            "user_token" -> dataStoreManager.getUserToken().map { it ?: defaultValue }
            "user_info" -> dataStoreManager.getUserInfo().map { it ?: defaultValue }
            "app_settings" -> dataStoreManager.getAppSettings().map { it ?: defaultValue }
            "theme_mode" -> dataStoreManager.getThemeMode().map { it ?: defaultValue }
            "language" -> dataStoreManager.getLanguage().map { it ?: defaultValue }
            "search_history" -> dataStoreManager.getSearchHistory().map { it ?: defaultValue }
            else -> {
                // 对于其他键，返回默认值的Flow
                kotlinx.coroutines.flow.flowOf(defaultValue)
            }
        }
    }
}
