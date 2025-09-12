/*
 * 跨平台反射工具类
 * 解决 iOS 和 Android 平台的反射问题
 */

package com.example.kmpuniversalapp.core.utils

/**
 * 获取异常类的简单名称
 * 跨平台兼容的实现
 */
fun Throwable.getSimpleClassName(): String {
    return this::class.simpleName ?: "Unknown"
}

/**
 * 获取对象的简单类名
 * 跨平台兼容的实现
 */
fun Any.getSimpleClassName(): String {
    return this::class.simpleName ?: "Unknown"
}
