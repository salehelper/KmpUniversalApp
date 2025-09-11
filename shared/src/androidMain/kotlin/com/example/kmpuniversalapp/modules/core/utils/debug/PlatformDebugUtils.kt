package com.example.kmpuniversalapp.core.utils.debug

import android.util.Log

/**
 * Android平台的调试工具实现
 */
actual object PlatformDebugUtils {
    actual fun isDebugMode(): Boolean {
        return true // 在开发环境中总是返回true
    }
    
    actual fun logToPlatform(tag: String, message: String, level: String) {
        when (level.uppercase()) {
            "VERBOSE" -> Log.v(tag, message)
            "DEBUG" -> Log.d(tag, message)
            "INFO" -> Log.i(tag, message)
            "WARN" -> Log.w(tag, message)
            "ERROR" -> Log.e(tag, message)
            else -> Log.d(tag, message)
        }
    }
}
