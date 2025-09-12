/*
 * 跨平台时间工具类
 * 解决 iOS 和 Android 平台的时间获取问题
 */

package com.example.kmpuniversalapp.core.utils

/**
 * 获取当前时间戳（毫秒）
 * 跨平台兼容的实现
 */
expect fun getCurrentTimeMillis(): Long

/**
 * 获取当前时间戳（秒）
 * 跨平台兼容的实现
 */
expect fun getCurrentTimeSeconds(): Long
