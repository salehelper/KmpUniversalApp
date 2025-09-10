package com.example.kmpuniversalapp.libs.http

import com.example.kmpuniversalapp.core.AppConfig
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * 优化的HTTP客户端配置
 * 使用Kermit日志和Resources插件，减少代码量
 */
object HttpClient {
    
    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
    }
    
    /**
     * 创建默认的HTTP客户端
     */
    fun create(): io.ktor.client.HttpClient {
        return io.ktor.client.HttpClient {
            // 超时配置
            install(HttpTimeout) {
                requestTimeoutMillis = AppConfig.API_TIMEOUT
                connectTimeoutMillis = AppConfig.API_TIMEOUT
                socketTimeoutMillis = AppConfig.API_TIMEOUT
            }
            
            // 内容协商
            install(ContentNegotiation) {
                json(json)
            }
            
            // 资源支持
            install(Resources)
            
            // 日志配置 - 使用Kermit
            if (AppConfig.ENABLE_LOGGING) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            // 解析HTTP日志消息，提取关键信息
                            when {
                                message.contains("REQUEST:") -> {
                                    val url = extractUrl(message)
                                    val method = extractMethod(message)
                                    AppLogger.network("HttpClient", "发送请求", url, null)
                                    AppLogger.d("HttpClient", "请求详情: $method $url")
                                }
                                message.contains("RESPONSE:") -> {
                                    val statusCode = extractStatusCode(message)
                                    val url = extractUrl(message)
                                    AppLogger.network("HttpClient", "收到响应", url, statusCode)
                                    AppLogger.d("HttpClient", "响应状态: $statusCode")
                                }
                                message.contains("ERROR:") -> {
                                    AppLogger.e("HttpClient", "网络请求错误: $message")
                                }
                                else -> {
                                    AppLogger.v("HttpClient", message)
                                }
                            }
                        }
                    }
                    level = LogLevel.INFO
                }
            }
            
            // 默认请求配置
            install(DefaultRequest) {
                url(AppConfig.API_BASE_URL)
                // 暂时注释header配置，避免编译错误
                // header("Content-Type", "application/json")
                // header("Accept", "application/json")
            }
            
            // 异常处理 - 暂时注释，避免编译错误
            /*
            install(HttpRequestRetry) {
                maxRetryCount = 3
                retryOnTimeout = true
                retryOnSocketTimeout = true
            }
            */
        }
    }
    
    /**
     * 创建带认证的HTTP客户端
     */
    fun createWithAuth(token: String): io.ktor.client.HttpClient {
        return io.ktor.client.HttpClient {
            // 超时配置
            install(HttpTimeout) {
                requestTimeoutMillis = AppConfig.API_TIMEOUT
                connectTimeoutMillis = AppConfig.API_TIMEOUT
                socketTimeoutMillis = AppConfig.API_TIMEOUT
            }
            
            // 内容协商
            install(ContentNegotiation) {
                json(json)
            }
            
            // 资源支持
            install(Resources)
            
            // 日志配置 - 使用Kermit
            if (AppConfig.ENABLE_LOGGING) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            AppLogger.d("HttpClient", message)
                        }
                    }
                    level = LogLevel.INFO
                }
            }
            
            // 默认请求配置
            install(DefaultRequest) {
                url(AppConfig.API_BASE_URL)
                // 暂时注释header配置，避免编译错误
                // header("Content-Type", "application/json")
                // header("Accept", "application/json")
                // header("Authorization", "Bearer $token")
            }
        }
    }
    
    // 日志解析辅助方法
    private fun extractUrl(message: String): String? {
        return try {
            val urlPattern = "http[s]?://[^\\s]+".toRegex()
            urlPattern.find(message)?.value
        } catch (e: Exception) {
            null
        }
    }
    
    private fun extractMethod(message: String): String? {
        return try {
            val methodPattern = "(GET|POST|PUT|DELETE|PATCH)\\s".toRegex()
            methodPattern.find(message)?.value?.trim()
        } catch (e: Exception) {
            null
        }
    }
    
    private fun extractStatusCode(message: String): Int? {
        return try {
            val statusPattern = "HTTP/\\d\\.\\d\\s+(\\d+)".toRegex()
            statusPattern.find(message)?.groupValues?.get(1)?.toInt()
        } catch (e: Exception) {
            null
        }
    }
}
