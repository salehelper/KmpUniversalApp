package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.INetworkClient
import kotlinx.coroutines.delay

/**
 * 网络客户端实现类
 * 提供模拟的网络请求功能（实际项目中应该使用真实的网络库）
 */
class NetworkClientImpl(
    private val logger: ILogger
) : INetworkClient {
    
    override suspend fun get(url: String, headers: Map<String, String>): String {
        logger.d("NetworkClient", "GET request to: $url")
        logger.d("NetworkClient", "Headers: $headers")
        
        // 模拟网络延迟
        delay(1000)
        
        // 模拟响应
        return """
        {
            "success": true,
            "data": {
                "url": "$url",
                "method": "GET",
                "headers": ${headers.entries.joinToString(",") { "\"${it.key}\":\"${it.value}\"" }}
            }
        }
        """.trimIndent()
    }

    override suspend fun post(url: String, body: String, headers: Map<String, String>): String {
        logger.d("NetworkClient", "POST request to: $url")
        logger.d("NetworkClient", "Body: $body")
        logger.d("NetworkClient", "Headers: $headers")
        
        // 模拟网络延迟
        delay(1500)
        
        // 模拟响应
        return """
        {
            "success": true,
            "data": {
                "url": "$url",
                "method": "POST",
                "body": "$body",
                "headers": ${headers.entries.joinToString(",") { "\"${it.key}\":\"${it.value}\"" }}
            }
        }
        """.trimIndent()
    }

    override suspend fun put(url: String, body: String, headers: Map<String, String>): String {
        logger.d("NetworkClient", "PUT request to: $url")
        logger.d("NetworkClient", "Body: $body")
        logger.d("NetworkClient", "Headers: $headers")
        
        // 模拟网络延迟
        delay(1200)
        
        // 模拟响应
        return """
        {
            "success": true,
            "data": {
                "url": "$url",
                "method": "PUT",
                "body": "$body",
                "headers": ${headers.entries.joinToString(",") { "\"${it.key}\":\"${it.value}\"" }}
            }
        }
        """.trimIndent()
    }

    override suspend fun delete(url: String, headers: Map<String, String>): String {
        logger.d("NetworkClient", "DELETE request to: $url")
        logger.d("NetworkClient", "Headers: $headers")
        
        // 模拟网络延迟
        delay(800)
        
        // 模拟响应
        return """
        {
            "success": true,
            "data": {
                "url": "$url",
                "method": "DELETE",
                "headers": ${headers.entries.joinToString(",") { "\"${it.key}\":\"${it.value}\"" }}
            }
        }
        """.trimIndent()
    }
}
