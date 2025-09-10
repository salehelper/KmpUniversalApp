package com.example.kmpuniversalapp.libs.utils.time

import android.os.SystemClock

actual object PlatformTimeUtils {
    actual fun getCurrentTimeMillis(): Long {
        return SystemClock.elapsedRealtime()
    }
}

