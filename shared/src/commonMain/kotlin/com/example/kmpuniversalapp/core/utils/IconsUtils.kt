/*
 * 跨平台图标工具类
 * 解决 iOS 和 Android 平台的图标兼容性问题
 */

package com.example.kmpuniversalapp.core.utils

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 跨平台图标工具类
 * 提供统一的图标访问接口
 */
expect object IconsUtils {
    // 基础图标
    val Home: ImageVector
    val Search: ImageVector
    val Settings: ImageVector
    val Person: ImageVector
    val Email: ImageVector
    val Phone: ImageVector
    val LocationOn: ImageVector
    val Favorite: ImageVector
    val Share: ImageVector
    val MoreVert: ImageVector
    val MoreHoriz: ImageVector
    
    // 导航图标
    val ArrowBack: ImageVector
    val ArrowForward: ImageVector
    val Close: ImageVector
    val Check: ImageVector
    val Add: ImageVector
    val Remove: ImageVector
    
    // 媒体图标
    val PlayArrow: ImageVector
    val Pause: ImageVector
    val Stop: ImageVector
    val VolumeUp: ImageVector
    val VolumeOff: ImageVector
    
    // 文件图标
    val Folder: ImageVector
    val FileDownload: ImageVector
    val FileUpload: ImageVector
    val AttachFile: ImageVector
    
    // 安全图标
    val Lock: ImageVector
    val LockOpen: ImageVector
    val Security: ImageVector
    val Visibility: ImageVector
    val VisibilityOff: ImageVector
    
    // 通知图标
    val Notifications: ImageVector
    val NotificationsOff: ImageVector
    val NotificationsActive: ImageVector
    
    // 评论图标
    val Comment: ImageVector
    
    // 获取图标的安全方法
    fun getIcon(name: String): ImageVector?
}
