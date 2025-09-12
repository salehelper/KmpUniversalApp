package com.example.kmpuniversalapp

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val isAndroid: Boolean = true
    override val isIOS: Boolean = false
    override val isDesktop: Boolean = false
    override val isWeb: Boolean = false
    override val isWindows: Boolean = false
    override val isMacOS: Boolean = false
}

actual fun getPlatform(): Platform = AndroidPlatform()