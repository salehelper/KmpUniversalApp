package com.example.kmpuniversalapp.core.base

/**
 * 应用配置
 */
object AppConfig {
    // 应用信息
    const val APP_NAME = "KMP Universal App"
    const val APP_VERSION = "1.0.0"
    const val APP_BUILD = 1
    
    // API配置
    const val API_BASE_URL = "https://api.kmp.example.com"
    const val API_TIMEOUT = 30000L // 30秒
    
    // 分页配置
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
    
    // 缓存配置
    const val CACHE_SIZE = 50 * 1024 * 1024L // 50MB
    const val CACHE_EXPIRE_TIME = 24 * 60 * 60 * 1000L // 24小时
    
    // 日志配置
    const val LOG_LEVEL = "DEBUG"
    const val LOG_RETENTION_DAYS = 7
    
    // 主题配置
    const val DEFAULT_THEME = "system"
    const val DEFAULT_LANGUAGE = "zh-CN"
    
    // 功能开关
    const val ENABLE_ANALYTICS = true
    const val ENABLE_CRASH_REPORTING = true
    const val ENABLE_LOGGING = true
}
