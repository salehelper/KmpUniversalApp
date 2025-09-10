package com.example.kmpuniversalapp.libs.utils.debug

import android.util.Log

/**
 * Android平台特定的调试工具
 */
actual object PlatformDebugUtils {
    
    /**
     * 检测是否为调试模式
     */
    actual fun isDebugMode(): Boolean {
        return try {
            System.getProperty("java.vm.name")?.contains("debug") == true ||
            System.getProperty("kotlin.native.cacheKind") == "none" ||
            true // 暂时固定为调试模式
        } catch (e: Exception) {
            true
        }
    }
    
    /**
     * 输出日志到Android平台
     */
    actual fun logToPlatform(tag: String, message: String, level: String) {
        val androidLevel = when (level) {
            "Verbose" -> Log.VERBOSE
            "Debug" -> Log.DEBUG
            "Info" -> Log.INFO
            "Warn" -> Log.WARN
            "Error" -> Log.ERROR
            else -> Log.DEBUG
        }
        
        // 输出到Logcat
        when (androidLevel) {
            Log.VERBOSE -> Log.v(tag, message)
            Log.DEBUG -> Log.d(tag, message)
            Log.INFO -> Log.i(tag, message)
            Log.WARN -> Log.w(tag, message)
            Log.ERROR -> Log.e(tag, message)
            else -> Log.d(tag, message)
        }

        // 同时输出到控制台
        println("[$level] [$tag] $message")
        System.out.println("[$level] [$tag] $message")
        System.err.println("[$level] [$tag] $message")
    }
}

