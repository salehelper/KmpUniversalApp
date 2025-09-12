package com.example.kmpuniversalapp

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val isAndroid: Boolean = false
    override val isIOS: Boolean = true
    override val isDesktop: Boolean = false
    override val isWeb: Boolean = false
    override val isWindows: Boolean = false
    override val isMacOS: Boolean = false
}

actual fun getPlatform(): Platform = IOSPlatform()