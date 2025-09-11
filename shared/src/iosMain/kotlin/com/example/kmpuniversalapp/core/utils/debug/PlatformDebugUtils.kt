package com.example.kmpuniversalapp.core.utils.debug

/**
 * iOS 平台的调试工具实现
 */
actual object PlatformDebugUtils {
    actual fun isDebugMode(): Boolean {
        // iOS 平台调试模式检测
        return true // 简化实现
    }
    
    actual fun logToPlatform(tag: String, message: String, level: String) {
        // iOS 平台日志输出
        println("[$level] [$tag]: $message")
    }
}
