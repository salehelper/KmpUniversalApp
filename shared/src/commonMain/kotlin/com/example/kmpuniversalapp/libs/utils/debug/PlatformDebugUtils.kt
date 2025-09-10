package com.example.kmpuniversalapp.libs.utils.debug

/**
 * 平台特定的调试工具类
 */
expect object PlatformDebugUtils {
    fun isDebugMode(): Boolean
    fun logToPlatform(tag: String, message: String, level: String)
}

