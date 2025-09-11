package com.example.kmpuniversalapp.infrastructure.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.kmpuniversalapp.core.utils.log.AppLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 使用DataStore的存储管理器
 * DataStore是Google推荐的现代存储解决方案，比SharedPreferences更安全、更高效
 */
class DataStoreManager(
    private val dataStore: DataStore<Preferences>
) {
    
    companion object {
        // 存储键
        private val USER_TOKEN = stringPreferencesKey("user_token")
        private val USER_INFO = stringPreferencesKey("user_info")
        private val APP_SETTINGS = stringPreferencesKey("app_settings")
        private val THEME_MODE = stringPreferencesKey("theme_mode")
        private val LANGUAGE = stringPreferencesKey("language")
        private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
        private val SEARCH_HISTORY = stringPreferencesKey("search_history")
        private val LAST_LOGIN_TIME = longPreferencesKey("last_login_time")
    }
    
    // 用户Token
    suspend fun saveUserToken(token: String) {
        try {
            AppLogger.d("DataStoreManager", "保存用户Token")
            dataStore.edit { preferences ->
                preferences[USER_TOKEN] = token
            }
            AppLogger.i("DataStoreManager", "用户Token保存成功")
        } catch (e: Exception) {
            AppLogger.error("DataStoreManager", "保存用户Token", e)
            throw e
        }
    }
    
    fun getUserToken(): Flow<String?> {
        AppLogger.d("DataStoreManager", "获取用户Token")
        return dataStore.data.map { preferences ->
            val token = preferences[USER_TOKEN]
            AppLogger.d("DataStoreManager", "用户Token获取结果: ${if (token != null) "已存在" else "不存在"}")
            token
        }
    }
    
    suspend fun clearUserToken() {
        try {
            AppLogger.d("DataStoreManager", "清除用户Token")
            dataStore.edit { preferences ->
                preferences.remove(USER_TOKEN)
            }
            AppLogger.i("DataStoreManager", "用户Token清除成功")
        } catch (e: Exception) {
            AppLogger.error("DataStoreManager", "清除用户Token", e)
            throw e
        }
    }
    
    // 用户信息
    suspend fun saveUserInfo(userInfo: String) {
        dataStore.edit { preferences ->
            preferences[USER_INFO] = userInfo
        }
    }
    
    fun getUserInfo(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_INFO]
        }
    }
    
    // 应用设置
    suspend fun saveAppSettings(settings: String) {
        dataStore.edit { preferences ->
            preferences[APP_SETTINGS] = settings
        }
    }
    
    fun getAppSettings(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[APP_SETTINGS]
        }
    }
    
    // 主题模式
    suspend fun saveThemeMode(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = theme
        }
    }
    
    fun getThemeMode(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[THEME_MODE]
        }
    }
    
    // 语言设置
    suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }
    
    fun getLanguage(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[LANGUAGE]
        }
    }
    
    // 首次启动
    suspend fun setFirstLaunch(isFirst: Boolean) {
        dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH] = isFirst
        }
    }
    
    fun isFirstLaunch(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[FIRST_LAUNCH] ?: true
        }
    }
    
    // 搜索历史
    suspend fun saveSearchHistory(history: String) {
        dataStore.edit { preferences ->
            preferences[SEARCH_HISTORY] = history
        }
    }
    
    fun getSearchHistory(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[SEARCH_HISTORY]
        }
    }
    
    // 最后登录时间
    suspend fun saveLastLoginTime(time: Long) {
        dataStore.edit { preferences ->
            preferences[LAST_LOGIN_TIME] = time
        }
    }
    
    fun getLastLoginTime(): Flow<Long?> {
        return dataStore.data.map { preferences ->
            preferences[LAST_LOGIN_TIME]
        }
    }
    
    // 清除所有数据
    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
