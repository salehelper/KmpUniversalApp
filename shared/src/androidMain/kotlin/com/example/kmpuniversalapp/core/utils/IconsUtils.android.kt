/*
 * Android 平台图标工具类
 * 支持完整的 Material Icons Extended
 */

package com.example.kmpuniversalapp.core.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Android 平台完整的图标集合
 * 支持所有 Material Icons Extended 功能
 */
actual object IconsUtils {
    // 基础图标
    actual val Home = Icons.Filled.Home
    actual val Search = Icons.Filled.Search
    actual val Settings = Icons.Filled.Settings
    actual val Person = Icons.Filled.Person
    actual val Email = Icons.Filled.Email
    actual val Phone = Icons.Filled.Phone
    actual val LocationOn = Icons.Filled.LocationOn
    actual val Favorite = Icons.Filled.Favorite
    actual val Share = Icons.Filled.Share
    actual val MoreVert = Icons.Filled.MoreVert
    actual val MoreHoriz = Icons.Filled.MoreHoriz
    
    // 导航图标
    actual val ArrowBack = Icons.AutoMirrored.Filled.ArrowBack
    actual val ArrowForward = Icons.AutoMirrored.Filled.ArrowForward
    actual val Close = Icons.Filled.Close
    actual val Check = Icons.Filled.Check
    actual val Add = Icons.Filled.Add
    actual val Remove = Icons.Filled.Remove
    
    // 媒体图标
    actual val PlayArrow = Icons.Filled.PlayArrow
    actual val Pause = Icons.Filled.Pause
    actual val Stop = Icons.Filled.Stop
    actual val VolumeUp = Icons.AutoMirrored.Filled.VolumeUp
    actual val VolumeOff = Icons.AutoMirrored.Filled.VolumeOff
    
    // 文件图标
    actual val Folder = Icons.Filled.Folder
    actual val FileDownload = Icons.Filled.FileDownload
    actual val FileUpload = Icons.Filled.FileUpload
    actual val AttachFile = Icons.Filled.AttachFile
    
    // 安全图标
    actual val Lock = Icons.Filled.Lock
    actual val LockOpen = Icons.Filled.LockOpen
    actual val Security = Icons.Filled.Security
    actual val Visibility = Icons.Filled.Visibility
    actual val VisibilityOff = Icons.Filled.VisibilityOff
    
    // 通知图标
    actual val Notifications = Icons.Filled.Notifications
    actual val NotificationsOff = Icons.Filled.NotificationsOff
    actual val NotificationsActive = Icons.Filled.NotificationsActive
    
    // 评论图标 - 使用 AutoMirrored 版本
    actual val Comment = Icons.AutoMirrored.Filled.Comment
    
    // 获取图标的安全方法
    actual fun getIcon(name: String): ImageVector? {
        return when (name.lowercase()) {
            "home" -> Home
            "search" -> Search
            "settings" -> Settings
            "person" -> Person
            "email" -> Email
            "phone" -> Phone
            "location" -> LocationOn
            "favorite" -> Favorite
            "share" -> Share
            "more_vert" -> MoreVert
            "more_horiz" -> MoreHoriz
            "arrow_back" -> ArrowBack
            "arrow_forward" -> ArrowForward
            "close" -> Close
            "check" -> Check
            "add" -> Add
            "remove" -> Remove
            "play" -> PlayArrow
            "pause" -> Pause
            "stop" -> Stop
            "volume_up" -> VolumeUp
            "volume_off" -> VolumeOff
            "folder" -> Folder
            "download" -> FileDownload
            "upload" -> FileUpload
            "attach" -> AttachFile
            "lock" -> Lock
            "lock_open" -> LockOpen
            "security" -> Security
            "visibility" -> Visibility
            "visibility_off" -> VisibilityOff
            "notifications" -> Notifications
            "notifications_off" -> NotificationsOff
            "notifications_active" -> NotificationsActive
            "comment" -> Comment
            else -> null
        }
    }
}
