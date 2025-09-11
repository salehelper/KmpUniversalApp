/**
 * 核心模块接口定义
 * 遵循接口隔离原则，定义清晰的模块边界
 */
package com.example.kmpuniversalapp.core

import kotlinx.coroutines.flow.Flow

/**
 * 日志接口
 * 隔离日志实现，支持不同平台的日志系统
 */
interface ILogger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}

/**
 * 存储接口
 * 隔离存储实现，支持不同存储方式
 */
interface IStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String = ""): String
    suspend fun remove(key: String)
    suspend fun contains(key: String): Boolean
    fun observeString(key: String, defaultValue: String): Flow<String>
}

/**
 * 网络接口
 * 隔离网络实现，支持不同网络库
 */
interface INetworkClient {
    suspend fun get(url: String, headers: Map<String, String> = emptyMap()): String
    suspend fun post(url: String, body: String, headers: Map<String, String> = emptyMap()): String
    suspend fun put(url: String, body: String, headers: Map<String, String> = emptyMap()): String
    suspend fun delete(url: String, headers: Map<String, String> = emptyMap()): String
}

/**
 * 时间接口
 * 隔离时间实现，支持不同平台的时间获取
 */
interface ITimeProvider {
    fun getCurrentTimeMillis(): Long
    fun getCurrentTimeSeconds(): Long
}

/**
 * 设备接口
 * 隔离设备信息获取，支持不同平台
 */
interface IDeviceInfo {
    fun getPlatformName(): String
    fun getAppVersion(): String
    fun isDebugMode(): Boolean
}
