package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.ITimeProvider
import kotlinx.datetime.Clock

/**
 * 时间提供者实现类
 * 提供当前时间获取功能
 */
class TimeProviderImpl : ITimeProvider {
    
    override fun getCurrentTimeMillis(): Long {
        return Clock.System.now().toEpochMilliseconds()
    }

    override fun getCurrentTimeSeconds(): Long {
        return Clock.System.now().epochSeconds
    }
}
