/*
 * Android 平台时间工具实现
 */

package com.example.kmpuniversalapp.core.utils

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual fun getCurrentTimeSeconds(): Long = System.currentTimeMillis() / 1000
