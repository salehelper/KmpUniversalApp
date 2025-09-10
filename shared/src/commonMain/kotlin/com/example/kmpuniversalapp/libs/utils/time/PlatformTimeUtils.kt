package com.example.kmpuniversalapp.libs.utils.time

/**
 * 平台特定的时间工具类
 */
expect object PlatformTimeUtils {
    fun getCurrentTimeMillis(): Long
}

