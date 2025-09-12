/**
 * 核心模块接口定义
 * 遵循接口隔离原则，定义清晰的模块边界
 */
package com.example.kmpuniversalapp.core

import kotlinx.coroutines.flow.Flow
import com.example.kmpuniversalapp.core.models.Permission
import com.example.kmpuniversalapp.core.models.PermissionResult
import com.example.kmpuniversalapp.core.models.NotificationData
import com.example.kmpuniversalapp.core.models.FileInfo

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

/**
 * 权限服务接口
 * 提供跨平台的权限管理功能
 */
interface IPermissionService {
    suspend fun isPermissionGranted(permission: Permission): Boolean
    suspend fun requestPermission(permission: Permission): PermissionResult
    suspend fun requestPermissions(permissions: List<Permission>): Map<Permission, PermissionResult>
    suspend fun shouldShowRationale(permission: Permission): Boolean
    suspend fun openAppSettings(): Boolean
}

/**
 * 通知服务接口
 * 提供跨平台的通知功能
 */
interface INotificationService {
    suspend fun isNotificationPermissionGranted(): Boolean
    suspend fun requestNotificationPermission(): Boolean
    suspend fun showNotification(notification: NotificationData): Boolean
    suspend fun cancelNotification(notificationId: Int): Boolean
    suspend fun cancelAllNotifications(): Boolean
    fun isNotificationSupported(): Boolean
}

/**
 * 文件服务接口
 * 提供跨平台的文件操作功能
 */
interface IFileService {
    suspend fun exists(path: String): Boolean
    suspend fun readFile(path: String): ByteArray?
    suspend fun readTextFile(path: String, encoding: String = "UTF-8"): String?
    suspend fun writeFile(path: String, content: ByteArray): Boolean
    suspend fun writeTextFile(path: String, content: String, encoding: String = "UTF-8"): Boolean
    suspend fun deleteFile(path: String): Boolean
    suspend fun createDirectory(path: String): Boolean
    suspend fun listDirectory(path: String): List<FileInfo>?
    suspend fun getFileInfo(path: String): FileInfo?
    suspend fun copyFile(sourcePath: String, destinationPath: String): Boolean
    suspend fun moveFile(sourcePath: String, destinationPath: String): Boolean
    suspend fun getFileSize(path: String): Long?
    suspend fun isDirectory(path: String): Boolean
    suspend fun isFile(path: String): Boolean
    suspend fun getTempFilePath(prefix: String = "temp", suffix: String = ".tmp"): String
    suspend fun cleanupTempFiles(): Boolean
}
