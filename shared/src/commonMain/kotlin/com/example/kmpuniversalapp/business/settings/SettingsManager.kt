package com.example.kmpuniversalapp.business.settings

import com.example.kmpuniversalapp.core.base.Result
import com.example.kmpuniversalapp.core.base.AppError
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.utils.getSimpleClassName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 设置管理器
 * 处理应用设置的管理和持久化
 */
class SettingsManager(
    private val storage: IStorage,
    private val logger: ILogger
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    /**
     * 获取语言设置
     */
    suspend fun getLanguage(): Result<String> {
        return try {
            val language = storage.getString(Constants.StorageKeys.LANGUAGE, Constants.DEFAULT_LANGUAGE)
            Result.Success(language)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取语言设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取语言设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置语言
     */
    suspend fun setLanguage(language: String): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.LANGUAGE, language)
            logger.i("SettingsManager", "语言设置已更新: $language")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置语言失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置语言失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取主题设置
     */
    suspend fun getTheme(): Result<String> {
        return try {
            val theme = storage.getString(Constants.StorageKeys.THEME, Constants.DEFAULT_THEME)
            Result.Success(theme)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取主题设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取主题设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置主题
     */
    suspend fun setTheme(theme: String): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.THEME, theme)
            logger.i("SettingsManager", "主题设置已更新: $theme")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置主题失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置主题失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取通知设置
     */
    suspend fun getNotificationsEnabled(): Result<Boolean> {
        return try {
            val enabled = storage.getString(Constants.StorageKeys.NOTIFICATIONS, Constants.DEFAULT_NOTIFICATIONS_ENABLED.toString()).toBoolean()
            Result.Success(enabled)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取通知设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取通知设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置通知
     */
    suspend fun setNotificationsEnabled(enabled: Boolean): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.NOTIFICATIONS, enabled.toString())
            logger.i("SettingsManager", "通知设置已更新: $enabled")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置通知失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置通知失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取自动更新设置
     */
    suspend fun getAutoUpdateEnabled(): Result<Boolean> {
        return try {
            val enabled = storage.getString(Constants.StorageKeys.AUTO_UPDATE, Constants.DEFAULT_AUTO_UPDATE_ENABLED.toString()).toBoolean()
            Result.Success(enabled)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取自动更新设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取自动更新设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置自动更新
     */
    suspend fun setAutoUpdateEnabled(enabled: Boolean): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.AUTO_UPDATE, enabled.toString())
            logger.i("SettingsManager", "自动更新设置已更新: $enabled")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置自动更新失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置自动更新失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取缓存大小设置
     */
    suspend fun getCacheSize(): Result<Int> {
        return try {
            val size = storage.getString(Constants.StorageKeys.CACHE_SIZE)?.toIntOrNull() 
                ?: Constants.DEFAULT_CACHE_SIZE_MB
            Result.Success(size)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取缓存大小设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取缓存大小设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置缓存大小
     */
    suspend fun setCacheSize(size: Int): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.CACHE_SIZE, size.toString())
            logger.i("SettingsManager", "缓存大小设置已更新: ${size}MB")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置缓存大小失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置缓存大小失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取最后同步时间
     */
    suspend fun getLastSyncTime(): Result<Long> {
        return try {
            val time = storage.getString(Constants.StorageKeys.LAST_SYNC_TIME)?.toLongOrNull() 
                ?: Constants.DEFAULT_LAST_SYNC_TIME_MILLIS
            Result.Success(time)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取最后同步时间失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取最后同步时间失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 设置最后同步时间
     */
    suspend fun setLastSyncTime(time: Long): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.LAST_SYNC_TIME, time.toString())
            logger.i("SettingsManager", "最后同步时间已更新: $time")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "设置最后同步时间失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "设置最后同步时间失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取所有设置
     */
    suspend fun getAllSettings(): Result<Map<String, Any>> {
        return try {
            val settings = mapOf(
                "language" to (storage.getString(Constants.StorageKeys.LANGUAGE) ?: Constants.DEFAULT_LANGUAGE),
                "theme" to (storage.getString(Constants.StorageKeys.THEME) ?: Constants.DEFAULT_THEME),
                "notifications" to (storage.getString(Constants.StorageKeys.NOTIFICATIONS)?.toBoolean() ?: Constants.DEFAULT_NOTIFICATIONS_ENABLED),
                "autoUpdate" to (storage.getString(Constants.StorageKeys.AUTO_UPDATE)?.toBoolean() ?: Constants.DEFAULT_AUTO_UPDATE_ENABLED),
                "cacheSize" to (storage.getString(Constants.StorageKeys.CACHE_SIZE)?.toIntOrNull() ?: Constants.DEFAULT_CACHE_SIZE_MB),
                "lastSyncTime" to (storage.getString(Constants.StorageKeys.LAST_SYNC_TIME)?.toLongOrNull() ?: Constants.DEFAULT_LAST_SYNC_TIME_MILLIS)
            )
            Result.Success(settings)
        } catch (e: Exception) {
            logger.e("SettingsManager", "获取所有设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取所有设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 重置所有设置为默认值
     */
    suspend fun resetToDefaults(): Result<Unit> {
        return try {
            storage.putString(Constants.StorageKeys.LANGUAGE, Constants.DEFAULT_LANGUAGE)
            storage.putString(Constants.StorageKeys.THEME, Constants.DEFAULT_THEME)
            storage.putString(Constants.StorageKeys.NOTIFICATIONS, Constants.DEFAULT_NOTIFICATIONS_ENABLED.toString())
            storage.putString(Constants.StorageKeys.AUTO_UPDATE, Constants.DEFAULT_AUTO_UPDATE_ENABLED.toString())
            storage.putString(Constants.StorageKeys.CACHE_SIZE, Constants.DEFAULT_CACHE_SIZE_MB.toString())
            storage.putString(Constants.StorageKeys.LAST_SYNC_TIME, Constants.DEFAULT_LAST_SYNC_TIME_MILLIS.toString())
            
            logger.i("SettingsManager", "所有设置已重置为默认值")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("SettingsManager", "重置设置失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "重置设置失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
}
