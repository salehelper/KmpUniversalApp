/*
 * 跨平台字符串格式化工具类
 * 解决 iOS 和 Android 平台的字符串格式化问题
 */

package com.example.kmpuniversalapp.core.utils

/**
 * 格式化字节大小
 * 跨平台兼容的实现
 */
fun formatBytes(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = bytes.toDouble()
    var unitIndex = 0
    
    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }
    
    val formattedSize = (size * 100).toInt() / 100.0
    return "$formattedSize ${units[unitIndex]}"
}

/**
 * 格式化百分比
 * 跨平台兼容的实现
 */
fun formatPercentage(progress: Double): String {
    val percentage = (progress * 100 * 10).toInt() / 10.0
    return "${percentage}%"
}

/**
 * 格式化十六进制字节数组
 * 跨平台兼容的实现
 */
fun formatHexBytes(bytes: ByteArray): String {
    return bytes.joinToString("") { 
        val hex = it.toInt() and 0xFF
        if (hex < 16) "0${hex.toString(16)}" else hex.toString(16)
    }
}
