package com.example.kmpuniversalapp.libs.utils.time

actual object PlatformTimeUtils {
    actual fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}

