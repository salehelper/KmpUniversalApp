package com.example.kmpuniversalapp.libs.utils.notification

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 通知类型
 */
enum class NotificationType {
    INFO,       // 信息通知
    SUCCESS,    // 成功通知
    WARNING,    // 警告通知
    ERROR       // 错误通知
}

/**
 * 通知数据
 */
data class NotificationData(
    val id: String,
    val title: String,
    val message: String,
    val type: NotificationType,
    val timestamp: Long = kotlinx.datetime.Clock.System.now().toEpochMilliseconds(),
    val duration: Long = 3000L, // 默认3秒
    val action: NotificationAction? = null
)

/**
 * 通知操作
 */
data class NotificationAction(
    val text: String,
    val onClick: () -> Unit
)

/**
 * 通知管理器
 */
object NotificationManager {
    
    private val _notifications = MutableStateFlow<List<NotificationData>>(emptyList())
    val notifications: StateFlow<List<NotificationData>> = _notifications.asStateFlow()
    
    private val _isEnabled = MutableStateFlow(true)
    val isEnabled: StateFlow<Boolean> = _isEnabled.asStateFlow()
    
    /**
     * 显示通知
     */
    fun show(
        title: String,
        message: String,
        type: NotificationType = NotificationType.INFO,
        duration: Long = 3000L,
        action: NotificationAction? = null
    ) {
        if (!_isEnabled.value) return
        
        val notification = NotificationData(
            id = generateId(),
            title = title,
            message = message,
            type = type,
            duration = duration,
            action = action
        )
        
        _notifications.value = _notifications.value + notification
        
        // 自动移除通知
        if (duration > 0) {
            // 实际项目中应该使用协程延迟
            // 这里只是示例
        }
    }
    
    /**
     * 显示信息通知
     */
    fun showInfo(title: String, message: String, duration: Long = 3000L) {
        show(title, message, NotificationType.INFO, duration)
    }
    
    /**
     * 显示成功通知
     */
    fun showSuccess(title: String, message: String, duration: Long = 3000L) {
        show(title, message, NotificationType.SUCCESS, duration)
    }
    
    /**
     * 显示警告通知
     */
    fun showWarning(title: String, message: String, duration: Long = 5000L) {
        show(title, message, NotificationType.WARNING, duration)
    }
    
    /**
     * 显示错误通知
     */
    fun showError(title: String, message: String, duration: Long = 0L) {
        show(title, message, NotificationType.ERROR, duration)
    }
    
    /**
     * 移除通知
     */
    fun remove(notificationId: String) {
        _notifications.value = _notifications.value.filter { it.id != notificationId }
    }
    
    /**
     * 清除所有通知
     */
    fun clear() {
        _notifications.value = emptyList()
    }
    
    /**
     * 启用/禁用通知
     */
    fun setEnabled(enabled: Boolean) {
        _isEnabled.value = enabled
        if (!enabled) {
            clear()
        }
    }
    
    private fun generateId(): String {
        return "notification_${kotlinx.datetime.Clock.System.now().toEpochMilliseconds()}_${(0..9999).random()}"
    }
}
