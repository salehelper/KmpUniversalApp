package com.example.kmpuniversalapp.libs.utils.debug

/**
 * iOS平台特定的调试工具
 */
actual object PlatformDebugUtils {
    
    /**
     * 检测是否为调试模式
     */
    actual fun isDebugMode(): Boolean {
        return true // iOS调试模式检测
    }
    
    /**
     * 输出日志到iOS平台
     */
    actual fun logToPlatform(tag: String, message: String, level: String) {
        println("[$level] [$tag] $message")
    }
}

