package com.example.kmpuniversalapp.core.utils.time

import kotlinx.datetime.Clock

/**
 * iOS 平台的时间工具实现
 */
actual object PlatformTimeUtils {
    actual fun getCurrentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}
