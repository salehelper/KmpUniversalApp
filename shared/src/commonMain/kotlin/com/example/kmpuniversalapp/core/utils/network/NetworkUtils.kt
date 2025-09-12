package com.example.kmpuniversalapp.core.utils.network

import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.utils.validation.ValidationUtils
import com.example.kmpuniversalapp.core.utils.formatBytes
import com.example.kmpuniversalapp.core.utils.formatPercentage

/**
 * 网络工具类
 * 提供网络相关的工具方法
 */
object NetworkUtils {
    
    /**
     * 构建完整的URL
     */
    fun buildUrl(baseUrl: String, path: String, params: Map<String, String> = emptyMap()): String {
        val cleanBaseUrl = baseUrl.trimEnd('/')
        val cleanPath = path.trimStart('/')
        val url = "$cleanBaseUrl/$cleanPath"
        
        return if (params.isNotEmpty()) {
            val queryString = params.entries.joinToString("&") { "${it.key}=${it.value}" }
            "$url?$queryString"
        } else {
            url
        }
    }
    
    /**
     * 解析URL参数
     */
    fun parseUrlParams(url: String): Map<String, String> {
        val params = mutableMapOf<String, String>()
        val queryIndex = url.indexOf('?')
        
        if (queryIndex == -1) return params
        
        val queryString = url.substring(queryIndex + 1)
        val pairs = queryString.split('&')
        
        for (pair in pairs) {
            val keyValue = pair.split('=', limit = 2)
            if (keyValue.size == 2) {
                params[keyValue[0]] = keyValue[1]
            }
        }
        
        return params
    }
    
    /**
     * 验证URL格式
     */
    fun isValidUrl(url: String?): Boolean {
        return url?.let { ValidationUtils.isValidUrl(it) } ?: false
    }
    
    /**
     * 获取URL的域名
     */
    fun getDomain(url: String): String? {
        if (!isValidUrl(url)) return null
        
        return try {
            val startIndex = url.indexOf("://") + 3
            val endIndex = url.indexOf('/', startIndex)
            if (endIndex == -1) {
                url.substring(startIndex)
            } else {
                url.substring(startIndex, endIndex)
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * 构建请求头
     */
    fun buildHeaders(
        contentType: String = "application/json",
        userAgent: String = "KMP-Universal-App/1.0.0",
        additionalHeaders: Map<String, String> = emptyMap()
    ): Map<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Content-Type"] = contentType
        headers["User-Agent"] = userAgent
        headers.putAll(additionalHeaders)
        return headers
    }
    
    /**
     * 构建认证头
     */
    fun buildAuthHeader(token: String, type: String = "Bearer"): Map<String, String> {
        return mapOf("Authorization" to "$type $token")
    }
    
    /**
     * 检查网络状态（需要平台特定实现）
     */
    suspend fun isNetworkAvailable(): Boolean {
        // 这里需要平台特定的实现
        // Android: 使用 ConnectivityManager
        // iOS: 使用 Network framework
        return true // 临时返回 true
    }
    
    /**
     * 获取网络类型（需要平台特定实现）
     */
    suspend fun getNetworkType(): NetworkType {
        // 这里需要平台特定的实现
        return NetworkType.UNKNOWN
    }
    
    /**
     * 格式化文件大小
     */
    fun formatFileSize(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var size = bytes.toDouble()
        var unitIndex = 0
        
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        
        return formatBytes(bytes)
    }
    
    /**
     * 格式化下载速度
     */
    fun formatDownloadSpeed(bytesPerSecond: Long): String {
        return "${formatFileSize(bytesPerSecond)}/s"
    }
    
    /**
     * 计算下载进度
     */
    fun calculateProgress(current: Long, total: Long): Float {
        return if (total <= 0) 0f else (current.toFloat() / total.toFloat()).coerceIn(0f, 1f)
    }
    
    /**
     * 格式化下载进度百分比
     */
    fun formatProgress(current: Long, total: Long): String {
        val progress = calculateProgress(current, total)
        return formatPercentage(progress.toDouble())
    }
}

/**
 * 网络类型枚举
 */
enum class NetworkType {
    WIFI,
    MOBILE,
    ETHERNET,
    UNKNOWN
}
