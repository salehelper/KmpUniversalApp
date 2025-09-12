package com.example.kmpuniversalapp.core.models

import kotlinx.serialization.Serializable
import com.example.kmpuniversalapp.core.utils.getCurrentTimeMillis

/**
 * 新闻数据模型
 */
@Serializable
data class NewsItem(
    val id: String,
    val title: String,
    val content: String,
    val summary: String? = null,
    val author: String? = null,
    val source: String? = null,
    val category: String? = null,
    val tags: List<String> = emptyList(),
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val publishedAt: Long,
    val updatedAt: Long,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isTop: Boolean = false,
    val isHot: Boolean = false,
    val isRecommended: Boolean = false
)

/**
 * 天气数据模型
 */
@Serializable
data class WeatherData(
    val city: String,
    val country: String? = null,
    val temperature: Double,
    val temperatureUnit: String = "°C",
    val description: String,
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val windDirection: String? = null,
    val pressure: Double? = null,
    val visibility: Double? = null,
    val uvIndex: Int? = null,
    val feelsLike: Double? = null,
    val minTemperature: Double? = null,
    val maxTemperature: Double? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val icon: String? = null,
    val forecast: List<WeatherForecast> = emptyList()
)

/**
 * 天气预报数据模型
 */
@Serializable
data class WeatherForecast(
    val date: Long,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val description: String,
    val icon: String? = null,
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val precipitation: Double? = null
)

/**
 * API 响应数据模型
 */
@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String? = null,
    val data: T,
    val code: Int = 200,
    val timestamp: Long = getCurrentTimeMillis()
)
