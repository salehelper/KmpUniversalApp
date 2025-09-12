/*
 * Android 平台环境变量工具实现
 */

package com.example.kmpuniversalapp.core.utils

actual fun getEnvironmentVariable(name: String): String? = System.getenv(name)

actual fun getTempDirectory(): String = System.getenv("TEMP") ?: "/tmp"

actual fun getDocumentsDirectory(): String = (System.getenv("HOME") ?: "/home") + "/Documents"

actual fun getAppDataDirectory(): String = (System.getenv("HOME") ?: "/home") + "/.local/share/KMPUniversalApp"
