package com.example.kmpuniversalapp.core.base

import com.example.kmpuniversalapp.core.utils.getCurrentTimeMillis

/**
 * 缓存数据类
 */
data class Cache(
    val key: String,
    val value: String,
    val timestamp: Long = getCurrentTimeMillis(),
    val ttl: Long = 0L // Time to live in milliseconds, 0 means no expiration
) {
    fun isExpired(): Boolean {
        return ttl > 0 && (getCurrentTimeMillis() - timestamp) > ttl
    }
}
