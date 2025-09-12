/*
 * iOS平台特定的平台信息实现
 */

package com.example.kmpuniversalapp.presentation.ui.components

import platform.UIKit.UIDevice

actual fun getPlatformName(): String {
    val device = UIDevice.currentDevice
    return "iOS ${device.systemVersion} (${device.model})"
}
