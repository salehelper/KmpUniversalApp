package com.example.kmpuniversalapp.libs.notification

import com.example.kmpuniversalapp.libs.utils.log.AppLogger

/**
 * 推送通知服务接口
 * 定义跨平台推送通知的通用接口
 */
interface NotificationService {
    
    /**
     * 初始化推送服务
     */
    suspend fun initialize()
    
    /**
     * 注册设备Token
     */
    suspend fun registerToken(): String?
    
    /**
     * 发送推送通知
     */
    suspend fun sendNotification(
        title: String,
        content: String,
        data: Map<String, String> = emptyMap()
    )
    
    /**
     * 设置用户标签
     */
    suspend fun setUserTags(tags: Map<String, String>)
    
    /**
     * 清除用户标签
     */
    suspend fun clearUserTags()
    
    /**
     * 检查推送权限
     */
    suspend fun checkPermission(): NotificationPermission
    
    /**
     * 请求推送权限
     */
    suspend fun requestPermission(): Boolean
}

/**
 * 推送权限状态
 */
enum class NotificationPermission {
    GRANTED,        // 已授权
    DENIED,         // 已拒绝
    NOT_DETERMINED  // 未确定
}

/**
 * 推送通知数据模型
 */
data class NotificationData(
    val title: String,
    val content: String,
    val data: Map<String, String> = emptyMap(),
    val timestamp: Long = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
)

/**
 * 推送服务管理器
 * 统一管理不同平台的推送服务
 */
// 暂时注释，避免编译错误
/*
class NotificationManager(
    private val jpushService: JPushService,
    private val fcmService: FCMService,
    private val apnsService: APNSService
) : NotificationService {
    
    private var currentService: NotificationService? = null
    
    override suspend fun initialize() {
        AppLogger.d("NotificationManager", "Initializing notification services")
        
        // 根据平台选择合适的推送服务
        currentService = when (getCurrentPlatform()) {
            Platform.ANDROID -> jpushService
            Platform.iOS -> apnsService
            Platform.WEB -> fcmService
            else -> null
        }
        
        currentService?.initialize()
    }
    
    override suspend fun registerToken(): String? {
        return currentService?.registerToken()
    }
    
    override suspend fun sendNotification(
        title: String,
        content: String,
        data: Map<String, String>
    ) {
        currentService?.sendNotification(title, content, data)
    }
    
    override suspend fun setUserTags(tags: Map<String, String>) {
        currentService?.setUserTags(tags)
    }
    
    override suspend fun clearUserTags() {
        currentService?.clearUserTags()
    }
    
    override suspend fun checkPermission(): NotificationPermission {
        return currentService?.checkPermission() ?: NotificationPermission.DENIED
    }
    
    override suspend fun requestPermission(): Boolean {
        return currentService?.requestPermission() ?: false
    }
    
    private fun getCurrentPlatform(): Platform {
        return Platform.ANDROID // 这里需要根据实际平台返回
    }
}
*/

/**
 * 平台枚举
 */
enum class Platform {
    ANDROID, iOS, WEB, DESKTOP
}
