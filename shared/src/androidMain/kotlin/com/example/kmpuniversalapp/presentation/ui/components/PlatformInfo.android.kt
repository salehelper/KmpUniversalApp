/*
 * Android平台特定的平台信息实现
 */

package com.example.kmpuniversalapp.presentation.ui.components

import android.os.Build

actual fun getPlatformName(): String {
    return "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
}
