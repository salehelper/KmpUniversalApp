package com.example.kmpuniversalapp.core.base

/**
 * 应用常量定义
 */
object Constants {
    // 默认语言设置
    const val DEFAULT_LANGUAGE = "zh-CN"
    
    // 默认主题设置
    const val DEFAULT_THEME = "light"
    
    // 默认通知设置
    const val DEFAULT_NOTIFICATIONS_ENABLED = true
    
    // 默认自动更新设置
    const val DEFAULT_AUTO_UPDATE_ENABLED = true
    
    // 默认缓存大小（MB）
    const val DEFAULT_CACHE_SIZE_MB = 50
    
    // 默认最后同步时间
    const val DEFAULT_LAST_SYNC_TIME_MILLIS = 0L
    
    // 同步相关常量
    const val SYNC_INTERVAL_MILLIS = 30 * 60 * 1000L // 30分钟
    const val SYNC_BATCH_SIZE = 40
    const val SYNC_RETRY_COUNT = 3
    const val SYNC_TIMEOUT_MILLIS = 60 * 1000L // 60秒
    
    // 缓存相关常量
    const val CACHE_SIZE_LIMIT = 50 * 1024 * 1024L // 50MB
    const val CACHE_EXPIRE_TIME = 24 * 60 * 60 * 1000L // 24小时
    
    // 网络相关常量
    const val DEFAULT_TIMEOUT = 30000L // 30秒
    const val MAX_RETRY_COUNT = 3
    
    // 存储相关常量
    const val PREF_NAME = "kmp_universal_app_prefs"
    const val ENCRYPTION_KEY_ALIAS = "kmp_universal_app_key"
    
    // 权限相关常量
    const val PERMISSION_REQUEST_CODE = 1000
    
    // 通知相关常量
    const val DEFAULT_NOTIFICATION_CHANNEL_ID = "default_channel"
    const val DEFAULT_NOTIFICATION_CHANNEL_NAME = "默认通知"
    
    // 文件相关常量
    const val MAX_FILE_SIZE = 100 * 1024 * 1024L // 100MB
    const val TEMP_DIR_PREFIX = "kmp_temp_"
    
    // 验证相关常量
    const val PASSWORD_MIN_LENGTH = 6
    const val PASSWORD_MAX_LENGTH = 20
    const val PHONE_LENGTH = 11
    const val EMAIL_MAX_LENGTH = 100
    const val VERIFICATION_CODE_LENGTH = 6
    
    // 错误码
    const val ERROR_CODE_NETWORK = 1001
    const val ERROR_CODE_PERMISSION = 1002
    const val ERROR_CODE_STORAGE = 1003
    const val ERROR_CODE_SERIALIZATION = 1004
    const val ERROR_CODE_VALIDATION = 1005
    const val ERROR_CODE_UNKNOWN = 9999
    
    // 存储键
    object StorageKeys {
        const val USER_TOKEN = "user_token"
        const val USER_INFO = "user_info"
        const val LANGUAGE = "language"
        const val THEME = "theme"
        const val NOTIFICATIONS = "notifications"
        const val AUTO_UPDATE = "auto_update"
        const val CACHE_SIZE = "cache_size"
        const val LAST_SYNC_TIME = "last_sync_time"
        const val SYNC_VERSIONS = "sync_versions"
    }
}