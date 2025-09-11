package com.example.kmpuniversalapp.core.utils.log

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * 统一日志管理器
 * 
 * 特性：
 * - 跨平台支持（Android/iOS）
 * - 彩色日志输出
 * - 时间戳格式化
 * - 多级别日志控制
 * - 性能优化
 */
object AppLogger {
    
    private val logger = Logger(
        config = StaticConfig(
            logWriterList = listOf(platformLogWriter()),
            minSeverity = Severity.Verbose
        ),
        tag = "KMPUniversalApp"
    )
    
    init {
        logger.d("AppLogger 初始化完成")
        logger.i("AppLogger 日志系统已启动，当前级别: ${logger.config.minSeverity}")
    }
    
    // ==================== 基础日志方法 ====================
    
    fun v(message: String, throwable: Throwable? = null) {
        logger.v(formatMessage("Verbose", message, throwable))
    }
    
    fun d(message: String, throwable: Throwable? = null) {
        logger.d(formatMessage("Debug", message, throwable))
    }
    
    fun i(message: String, throwable: Throwable? = null) {
        logger.i(formatMessage("Info", message, throwable))
    }
    
    fun w(message: String, throwable: Throwable? = null) {
        logger.w(formatMessage("Warn", message, throwable))
    }
    
    fun e(message: String, throwable: Throwable? = null) {
        logger.e(formatMessage("Error", message, throwable))
    }
    
    // ==================== 带标签的日志方法 ====================
    
    fun v(tag: String, message: String, throwable: Throwable? = null) {
        logger.withTag(tag).v(formatMessage("Verbose", message, throwable, tag))
    }
    
    fun d(tag: String, message: String, throwable: Throwable? = null) {
        logger.withTag(tag).d(formatMessage("Debug", message, throwable, tag))
    }
    
    fun i(tag: String, message: String, throwable: Throwable? = null) {
        logger.withTag(tag).i(formatMessage("Info", message, throwable, tag))
    }
    
    fun w(tag: String, message: String, throwable: Throwable? = null) {
        logger.withTag(tag).w(formatMessage("Warn", message, throwable, tag))
    }
    
    fun e(tag: String, message: String, throwable: Throwable? = null) {
        logger.withTag(tag).e(formatMessage("Error", message, throwable, tag))
    }
    
    // ==================== 便捷方法 ====================
    
    /**
     * 网络请求日志
     */
    fun network(tag: String, message: String, url: String? = null, statusCode: Int? = null) {
        val networkMessage = buildString {
            append(message)
            url?.let { append(" | URL: $it") }
            statusCode?.let { append(" | Status: $it") }
        }
        d(tag, networkMessage)
    }
    
    /**
     * 用户操作日志
     */
    fun userAction(action: String, details: String? = null) {
        val message = buildString {
            append("用户操作: $action")
            details?.let { append(" | 详情: $it") }
        }
        i("UserAction", message)
    }
    
    /**
     * 性能日志
     */
    fun performance(operation: String, duration: Long, details: String? = null) {
        val message = buildString {
            append("性能监控: $operation 耗时 ${duration}ms")
            details?.let { append(" | $it") }
        }
        d("Performance", message)
    }
    
    /**
     * 错误日志
     */
    fun error(tag: String, operation: String, error: Throwable, context: String? = null) {
        val message = buildString {
            append("操作失败: $operation")
            context?.let { append(" | 上下文: $it") }
            append(" | 错误: ${error.message}")
        }
        e(tag, message, error)
    }
    
    // ==================== 私有工具方法 ====================
    
    /**
     * 格式化日志消息
     */
    private fun formatMessage(level: String, message: String, throwable: Throwable? = null, tag: String? = null): String {
        val timestamp = getFormattedTimestamp()
        val levelInfo = getLogLevelInfo(Severity.valueOf(level))
        val icon = levelInfo.first
        val levelName = levelInfo.second
        
        val tagPart = if (tag != null) "[$tag] " else ""
        val throwablePart = if (throwable != null) "\n${throwable.stackTraceToString()}" else ""
        
        return "$icon [$timestamp] [$levelName] $tagPart$message$throwablePart"
    }
    
    /**
     * 获取格式化的时间戳
     */
    private fun getFormattedTimestamp(): String {
        val now = Clock.System.now()
        val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')} " +
                "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"
    }
    
    /**
     * 获取日志级别对应的颜色和图标
     */
    private fun getLogLevelInfo(severity: Severity): Pair<String, String> {
        return when (severity) {
            Severity.Verbose -> "🔍" to "Verbose"
            Severity.Debug -> "🐛" to "Debug"
            Severity.Info -> "ℹ️" to "Info"
            Severity.Warn -> "⚠️" to "Warn"
            Severity.Error -> "❌" to "Error"
            Severity.Assert -> "💥" to "Assert"
        }
    }
}
