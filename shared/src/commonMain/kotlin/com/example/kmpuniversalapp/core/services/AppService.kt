package com.example.kmpuniversalapp.core.services

import com.example.kmpuniversalapp.core.*

/**
 * 应用服务
 * 展示如何使用核心接口
 */
class AppService(
    private val logger: ILogger = DI.getLogger(),
    private val storage: IStorage = DI.getStorage(),
    private val networkClient: INetworkClient = DI.getNetworkClient(),
    private val timeProvider: ITimeProvider = DI.getTimeProvider(),
    private val deviceInfo: IDeviceInfo = DI.getDeviceInfo()
) {
    
    /**
     * 初始化应用
     */
    suspend fun initialize() {
        logger.i("AppService", "开始初始化应用")
        
        try {
            // 记录设备信息
            val platform = deviceInfo.getPlatformName()
            val version = deviceInfo.getAppVersion()
            val isDebug = deviceInfo.isDebugMode()
            
            logger.i("AppService", "平台: $platform, 版本: $version, 调试模式: $isDebug")
            
            // 保存应用启动时间
            val startTime = timeProvider.getCurrentTimeMillis()
            storage.putString("app_start_time", startTime.toString())
            
            // 测试网络连接
            testNetworkConnection()
            
            logger.i("AppService", "应用初始化完成")
        } catch (e: Exception) {
            logger.e("AppService", "应用初始化失败", e)
            throw e
        }
    }
    
    /**
     * 测试网络连接
     */
    private suspend fun testNetworkConnection() {
        try {
            logger.d("AppService", "测试网络连接")
            val response = networkClient.get("https://api.example.com/health")
            logger.d("AppService", "网络测试响应: $response")
        } catch (e: Exception) {
            logger.w("AppService", "网络测试失败: ${e.message}")
        }
    }
    
    /**
     * 获取应用统计信息
     */
    suspend fun getAppStats(): Map<String, Any> {
        val startTime = storage.getString("app_start_time", "0").toLongOrNull() ?: 0
        val currentTime = timeProvider.getCurrentTimeMillis()
        val uptime = currentTime - startTime
        
        return mapOf(
            "platform" to deviceInfo.getPlatformName(),
            "version" to deviceInfo.getAppVersion(),
            "debug_mode" to deviceInfo.isDebugMode(),
            "uptime_ms" to uptime,
            "start_time" to startTime,
            "current_time" to currentTime
        )
    }
    
    /**
     * 保存用户偏好设置
     */
    suspend fun saveUserPreference(key: String, value: String) {
        logger.d("AppService", "保存用户偏好: $key = $value")
        storage.putString("user_pref_$key", value)
    }
    
    /**
     * 获取用户偏好设置
     */
    suspend fun getUserPreference(key: String, defaultValue: String = ""): String {
        val value = storage.getString("user_pref_$key", defaultValue)
        logger.d("AppService", "获取用户偏好: $key = $value")
        return value
    }
    
    /**
     * 清理应用数据
     */
    suspend fun cleanup() {
        logger.i("AppService", "开始清理应用数据")
        
        try {
            // 清理临时数据
            storage.remove("app_start_time")
            
            logger.i("AppService", "应用数据清理完成")
        } catch (e: Exception) {
            logger.e("AppService", "应用数据清理失败", e)
        }
    }
}
