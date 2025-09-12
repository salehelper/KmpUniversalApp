package com.example.kmpuniversalapp.core.models

import com.example.kmpuniversalapp.core.services.*

/**
 * 权限枚举
 */
enum class Permission(val feature: PlatformFeature) {
    CAMERA(PlatformFeature.CAMERA),
    LOCATION(PlatformFeature.LOCATION),
    STORAGE(PlatformFeature.FILE_SYSTEM),
    MICROPHONE(PlatformFeature.MEDIA_PLAYER),
    NOTIFICATIONS(PlatformFeature.NOTIFICATIONS),
    BLUETOOTH(PlatformFeature.BLUETOOTH),
    WIFI(PlatformFeature.WIFI),
    CELLULAR(PlatformFeature.CELLULAR),
    SENSORS(PlatformFeature.SENSORS),
    VIBRATION(PlatformFeature.VIBRATION),
    HAPTIC_FEEDBACK(PlatformFeature.HAPTIC_FEEDBACK),
    PHONE(PlatformFeature.CELLULAR),
    CONTACTS(PlatformFeature.FILE_SYSTEM),
    CALENDAR(PlatformFeature.FILE_SYSTEM),
    SMS(PlatformFeature.CELLULAR),
    CALL_PHONE(PlatformFeature.CELLULAR)
}

/**
 * 权限结果
 */
data class PermissionResult(
    val permission: Permission,
    val isGranted: Boolean,
    val shouldShowRationale: Boolean = false
)

/**
 * 通知数据类
 */
data class NotificationData(
    val id: Int,
    val title: String,
    val content: String,
    val channelId: String = "default",
    val priority: NotificationPriority = NotificationPriority.NORMAL,
    val category: NotificationCategory = NotificationCategory.OTHER,
    val actions: List<NotificationAction> = emptyList(),
    val largeIcon: String? = null,
    val smallIcon: String? = null,
    val sound: String? = null,
    val vibration: Boolean = true,
    val lights: Boolean = true,
    val autoCancel: Boolean = true,
    val ongoing: Boolean = false,
    val showWhen: Boolean = true,
    val timestamp: Long? = null
)

/**
 * 通知优先级
 */
enum class NotificationPriority {
    MIN,
    LOW,
    NORMAL,
    HIGH,
    MAX
}

/**
 * 通知类别
 */
enum class NotificationCategory {
    ALARM,
    CALL,
    EMAIL,
    ERROR,
    EVENT,
    MESSAGE,
    PROGRESS,
    PROMO,
    RECOMMENDATION,
    REMINDER,
    SERVICE,
    SOCIAL,
    STATUS,
    SYSTEM,
    TRANSPORT,
    OTHER
}

/**
 * 通知操作
 */
data class NotificationAction(
    val id: String,
    val title: String,
    val icon: String? = null,
    val intent: String? = null
)

/**
 * 文件信息数据类
 */
data class FileInfo(
    val name: String,
    val path: String,
    val size: Long,
    val isDirectory: Boolean,
    val isFile: Boolean,
    val lastModified: Long,
    val permissions: FilePermissions? = null
)

/**
 * 文件权限数据类
 */
data class FilePermissions(
    val readable: Boolean,
    val writable: Boolean,
    val executable: Boolean
)

/**
 * 平台功能枚举
 */
enum class PlatformFeature {
    FILE_SYSTEM,
    NOTIFICATIONS,
    CAMERA,
    LOCATION,
    BLUETOOTH,
    WIFI,
    CELLULAR,
    SENSORS,
    VIBRATION,
    HAPTIC_FEEDBACK,
    SHARE,
    CLIPBOARD,
    WEB_VIEW,
    MEDIA_PLAYER,
    IMAGE_PICKER,
    DOCUMENT_PICKER
}
