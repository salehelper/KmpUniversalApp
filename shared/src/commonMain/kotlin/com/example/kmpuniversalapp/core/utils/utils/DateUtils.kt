package com.example.kmpuniversalapp.core.utils.utils

import kotlinx.datetime.*
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.days

/**
 * 优化的日期时间工具类
 * 使用Kotlinx DateTime的现代API，代码更简洁
 */
object DateUtils {
    
    private val systemTimeZone = TimeZone.currentSystemDefault()
    
    /**
     * 获取当前时间戳（毫秒）
     */
    fun currentTimestamp(): Long = Clock.System.now().toEpochMilliseconds()
    
    /**
     * 获取当前时间戳（秒）
     */
    fun currentTimestampSeconds(): Long = Clock.System.now().epochSeconds
    
    /**
     * 获取当前LocalDateTime
     */
    fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(systemTimeZone)
    
    /**
     * 格式化时间戳为字符串
     */
    fun formatTimestamp(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val instant = Instant.fromEpochMilliseconds(timestamp)
        val localDateTime = instant.toLocalDateTime(systemTimeZone)
        return formatLocalDateTime(localDateTime, pattern)
    }
    
    /**
     * 格式化LocalDateTime为字符串
     */
    fun formatLocalDateTime(localDateTime: LocalDateTime, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        return when (pattern) {
            "yyyy-MM-dd" -> "${localDateTime.year}-${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')}"
            "HH:mm:ss" -> "${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}:${localDateTime.second.toString().padStart(2, '0')}"
            "yyyy-MM-dd HH:mm:ss" -> "${formatLocalDateTime(localDateTime, "yyyy-MM-dd")} ${formatLocalDateTime(localDateTime, "HH:mm:ss")}"
            "MM-dd HH:mm" -> "${localDateTime.monthNumber.toString().padStart(2, '0')}-${localDateTime.dayOfMonth.toString().padStart(2, '0')} ${localDateTime.hour.toString().padStart(2, '0')}:${localDateTime.minute.toString().padStart(2, '0')}"
            else -> localDateTime.toString()
        }
    }
    
    /**
     * 获取相对时间描述 - 使用Duration API
     */
    fun getRelativeTime(timestamp: Long): String {
        val now = Clock.System.now()
        val target = Instant.fromEpochMilliseconds(timestamp)
        val duration = now - target
        
        return when {
            duration < 1.minutes -> "刚刚"
            duration < 1.hours -> "${duration.inWholeMinutes}分钟前"
            duration < 1.days -> "${duration.inWholeHours}小时前"
            duration < 7.days -> "${duration.inWholeDays}天前"
            else -> formatTimestamp(timestamp, "yyyy-MM-dd")
        }
    }
    
    /**
     * 检查是否为今天
     */
    fun isToday(timestamp: Long): Boolean {
        val target = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(systemTimeZone)
        val today = now()
        
        return target.date == today.date
    }
    
    /**
     * 检查是否为昨天
     */
    fun isYesterday(timestamp: Long): Boolean {
        val target = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(systemTimeZone)
        val yesterday = now().date.minus(1, DateTimeUnit.DAY)
        
        return target.date == yesterday
    }
    
    /**
     * 检查是否为本周
     */
    fun isThisWeek(timestamp: Long): Boolean {
        val target = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(systemTimeZone)
        val now = now()
        val weekStart = now.date.minus(now.dayOfWeek.ordinal, DateTimeUnit.DAY)
        
        return target.date >= weekStart
    }
    
    /**
     * 获取友好的时间显示
     */
    fun getFriendlyTime(timestamp: Long): String {
        return when {
            isToday(timestamp) -> "今天 ${formatTimestamp(timestamp, "HH:mm")}"
            isYesterday(timestamp) -> "昨天 ${formatTimestamp(timestamp, "HH:mm")}"
            isThisWeek(timestamp) -> formatTimestamp(timestamp, "EEEE HH:mm")
            else -> formatTimestamp(timestamp, "MM-dd HH:mm")
        }
    }
    
    /**
     * 解析ISO 8601时间字符串
     */
    fun parseIso8601(isoString: String): LocalDateTime? {
        return try {
            Instant.parse(isoString).toLocalDateTime(systemTimeZone)
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * 格式化为ISO 8601字符串
     */
    fun toIso8601(localDateTime: LocalDateTime): String {
        return localDateTime.toInstant(systemTimeZone).toString()
    }
}
