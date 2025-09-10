package com.example.kmpuniversalapp.libs.utils.time

import kotlinx.datetime.Clock

actual object PlatformTimeUtils {
    actual fun getCurrentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }
}
