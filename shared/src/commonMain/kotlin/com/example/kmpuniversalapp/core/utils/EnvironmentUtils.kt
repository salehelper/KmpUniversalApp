/*
 * 跨平台环境变量工具类
 * 解决 iOS 和 Android 平台的环境变量访问问题
 */

package com.example.kmpuniversalapp.core.utils

/**
 * 获取环境变量
 * 跨平台兼容的实现
 */
expect fun getEnvironmentVariable(name: String): String?

/**
 * 获取临时目录路径
 * 跨平台兼容的实现
 */
expect fun getTempDirectory(): String

/**
 * 获取文档目录路径
 * 跨平台兼容的实现
 */
expect fun getDocumentsDirectory(): String

/**
 * 获取应用数据目录路径
 * 跨平台兼容的实现
 */
expect fun getAppDataDirectory(): String
