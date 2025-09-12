package com.example.kmpuniversalapp

interface Platform {
    val name: String
    val isAndroid: Boolean
    val isIOS: Boolean
    val isDesktop: Boolean
    val isWeb: Boolean
    val isWindows: Boolean
    val isMacOS: Boolean
}

expect fun getPlatform(): Platform

object PlatformInfo {
    val name: String get() = getPlatform().name
    val isAndroid: Boolean get() = getPlatform().isAndroid
    val isIOS: Boolean get() = getPlatform().isIOS
    val isDesktop: Boolean get() = getPlatform().isDesktop
    val isWeb: Boolean get() = getPlatform().isWeb
    val isWindows: Boolean get() = getPlatform().isWindows
    val isMacOS: Boolean get() = getPlatform().isMacOS
}