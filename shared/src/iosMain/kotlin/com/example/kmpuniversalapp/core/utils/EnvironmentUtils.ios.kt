/*
 * iOS 平台环境变量工具实现
 */

package com.example.kmpuniversalapp.core.utils

import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import platform.Foundation.NSTemporaryDirectory

actual fun getEnvironmentVariable(name: String): String? = null // iOS 不支持环境变量

actual fun getTempDirectory(): String = NSTemporaryDirectory()

actual fun getDocumentsDirectory(): String = "/Documents"

actual fun getAppDataDirectory(): String = "/Documents/KMPUniversalApp"
