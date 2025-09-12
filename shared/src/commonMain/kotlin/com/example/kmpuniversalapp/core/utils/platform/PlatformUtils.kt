package com.example.kmpuniversalapp.core.utils.platform

import com.example.kmpuniversalapp.PlatformInfo
import com.example.kmpuniversalapp.core.utils.getCurrentTimeMillis
import com.example.kmpuniversalapp.core.utils.getTempDirectory
import com.example.kmpuniversalapp.core.utils.getDocumentsDirectory
import com.example.kmpuniversalapp.core.utils.getAppDataDirectory

/**
 * 平台工具类
 * 提供跨平台的平台特定功能
 */
object PlatformUtils {
    
    /**
     * 获取当前平台名称
     */
    fun getPlatformName(): String {
        return PlatformInfo.name
    }
    
    /**
     * 检查是否为Android平台
     */
    fun isAndroid(): Boolean {
        return PlatformInfo.isAndroid
    }
    
    /**
     * 检查是否为iOS平台
     */
    fun isIOS(): Boolean {
        return PlatformInfo.isIOS
    }
    
    /**
     * 检查是否为桌面平台
     */
    fun isDesktop(): Boolean {
        return PlatformInfo.isDesktop
    }
    
    /**
     * 检查是否为Web平台
     */
    fun isWeb(): Boolean {
        return PlatformInfo.isWeb
    }
    
    /**
     * 获取平台特定的文件分隔符
     */
    fun getFileSeparator(): String {
        return when {
            isAndroid() || isIOS() -> "/"
            isDesktop() -> if (PlatformInfo.isWindows) "\\" else "/"
            else -> "/"
        }
    }
    
    /**
     * 获取平台特定的换行符
     */
    fun getLineSeparator(): String {
        return when {
            PlatformInfo.isWindows -> "\r\n"
            else -> "\n"
        }
    }
    
    /**
     * 获取平台特定的临时目录
     */
    fun getTempDirectory(): String {
        return when {
            isAndroid() -> "/data/data/com.example.kmpuniversalapp/cache"
            isIOS() -> "/tmp"
            isDesktop() -> {
                when {
                    PlatformInfo.isWindows -> getTempDirectory()
                    PlatformInfo.isMacOS -> "/tmp"
                    else -> "/tmp"
                }
            }
            isWeb() -> "/tmp"
            else -> "/tmp"
        }
    }
    
    /**
     * 获取平台特定的文档目录
     */
    fun getDocumentsDirectory(): String {
        return when {
            isAndroid() -> "/storage/emulated/0/Documents"
            isIOS() -> "/Documents"
            isDesktop() -> {
                when {
                    PlatformInfo.isWindows -> getDocumentsDirectory()
                    PlatformInfo.isMacOS -> getDocumentsDirectory()
                    else -> getDocumentsDirectory()
                }
            }
            isWeb() -> "/Documents"
            else -> "/Documents"
        }
    }
    
    /**
     * 获取平台特定的应用数据目录
     */
    fun getAppDataDirectory(): String {
        return when {
            isAndroid() -> "/data/data/com.example.kmpuniversalapp"
            isIOS() -> "/var/mobile/Containers/Data/Application"
            isDesktop() -> {
                when {
                    PlatformInfo.isWindows -> getAppDataDirectory()
                    PlatformInfo.isMacOS -> getAppDataDirectory()
                    else -> getAppDataDirectory()
                }
            }
            isWeb() -> "/appdata"
            else -> "/appdata"
        }
    }
    
    /**
     * 获取平台特定的用户代理字符串
     */
    fun getUserAgent(): String {
        return when {
            isAndroid() -> "KMPUniversalApp/1.0.0 (Android)"
            isIOS() -> "KMPUniversalApp/1.0.0 (iOS)"
            isDesktop() -> "KMPUniversalApp/1.0.0 (Desktop)"
            isWeb() -> "KMPUniversalApp/1.0.0 (Web)"
            else -> "KMPUniversalApp/1.0.0 (Unknown)"
        }
    }
    
    /**
     * 获取平台特定的默认编码
     */
    fun getDefaultEncoding(): String {
        return when {
            PlatformInfo.isWindows -> "GBK"
            else -> "UTF-8"
        }
    }
    
    /**
     * 检查平台是否支持特定功能
     */
    fun supportsFeature(feature: PlatformFeature): Boolean {
        return when (feature) {
            PlatformFeature.FILE_SYSTEM -> !isWeb()
            PlatformFeature.NOTIFICATIONS -> isAndroid() || isIOS()
            PlatformFeature.CAMERA -> isAndroid() || isIOS()
            PlatformFeature.LOCATION -> isAndroid() || isIOS()
            PlatformFeature.BLUETOOTH -> isAndroid() || isIOS()
            PlatformFeature.WIFI -> isAndroid() || isIOS()
            PlatformFeature.CELLULAR -> isAndroid() || isIOS()
            PlatformFeature.SENSORS -> isAndroid() || isIOS()
            PlatformFeature.VIBRATION -> isAndroid() || isIOS()
            PlatformFeature.HAPTIC_FEEDBACK -> isAndroid() || isIOS()
            PlatformFeature.SHARE -> true
            PlatformFeature.CLIPBOARD -> true
            PlatformFeature.WEB_VIEW -> isAndroid() || isIOS()
            PlatformFeature.MEDIA_PLAYER -> true
            PlatformFeature.IMAGE_PICKER -> isAndroid() || isIOS()
            PlatformFeature.DOCUMENT_PICKER -> isAndroid() || isIOS()
        }
    }
}

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
