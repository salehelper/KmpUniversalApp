package com.example.kmpuniversalapp.libs.utils.debug

actual object PlatformDebugUtils {
    actual fun isDebugMode(): Boolean {
        return try {
            System.getProperty("debug", "false").toBoolean()
        } catch (e: Exception) {
            true
        }
    }
}

