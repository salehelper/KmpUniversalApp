package com.example.kmpuniversalapp.core.utils.time

/**
 * Android平台的时间工具实现
 */
actual object PlatformTimeUtils {
    actual fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}
