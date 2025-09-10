package com.example.kmpuniversalapp.home

import com.example.kmpuniversalapp.home.BannerModel
import com.example.kmpuniversalapp.home.DynamicModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class HomeApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    
    suspend fun getBanners(): List<BannerModel> {
        return try {
            // 模拟API调用，实际项目中应该调用真实API
            getMockBanners()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getDynamics(page: Int = 1, size: Int = 20): List<DynamicModel> {
        return try {
            // 模拟API调用
            getMockDynamics()
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getHomeStatistics(): Map<String, Any> {
        return try {
            // 模拟统计数据
            mapOf(
                "totalUsers" to 1234,
                "totalPosts" to 5678,
                "totalViews" to 9012,
                "onlineUsers" to 56
            )
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    private fun getMockBanners(): List<BannerModel> {
        return listOf(
            BannerModel(
                id = "banner_001",
                title = "Kotlin Multiplatform 开发指南",
                image = "https://picsum.photos/400/200?random=1",
                url = "https://example.com/kmp-guide",
                sort = 1,
                isActive = true,
                createdAt = "2024-01-15T10:00:00Z",
                extra = mapOf("type" to "article", "description" to "学习跨平台开发的最佳实践")
            ),
            BannerModel(
                id = "banner_002",
                title = "Compose Multiplatform UI 设计",
                image = "https://picsum.photos/400/200?random=2",
                url = "https://example.com/compose-ui",
                sort = 2,
                isActive = true,
                createdAt = "2024-01-14T15:30:00Z",
                extra = mapOf("type" to "video", "description" to "打造现代化的用户界面")
            ),
            BannerModel(
                id = "banner_003",
                title = "KMP 性能优化技巧",
                image = "https://picsum.photos/400/200?random=3",
                url = "https://example.com/kmp-performance",
                sort = 3,
                isActive = true,
                createdAt = "2024-01-13T09:15:00Z",
                extra = mapOf("type" to "document", "description" to "提升应用性能的实用方法")
            )
        )
    }
    
    private fun getMockDynamics(): List<DynamicModel> {
        return listOf(
            DynamicModel(
                id = "dynamic_001",
                title = "Kotlin Multiplatform 1.9.20 发布",
                content = "新版本带来了性能提升和bug修复，建议开发者及时更新...",
                type = "news",
                image = "https://picsum.photos/300/200?random=4",
                url = "https://example.com/kmp-1.9.20",
                viewCount = 1250,
                likeCount = 89,
                commentCount = 23,
                isTop = true,
                createdAt = "2024-01-15T08:00:00Z",
                extra = mapOf("category" to "技术新闻", "author" to "Kotlin团队")
            ),
            DynamicModel(
                id = "dynamic_002",
                title = "Compose Multiplatform 1.8.2 更新",
                content = "Compose框架发布了新版本，包含多项改进和新功能...",
                type = "update",
                image = "https://picsum.photos/300/200?random=5",
                url = "https://example.com/compose-1.8.2",
                viewCount = 980,
                likeCount = 67,
                commentCount = 18,
                isTop = false,
                createdAt = "2024-01-14T14:20:00Z",
                extra = mapOf("category" to "框架更新", "author" to "JetBrains团队")
            ),
            DynamicModel(
                id = "dynamic_003",
                title = "KMP 跨平台开发实践",
                content = "分享一些Kotlin Multiplatform开发的实用技巧和最佳实践...",
                type = "tutorial",
                image = "https://picsum.photos/300/200?random=6",
                url = "https://example.com/kmp-practices",
                viewCount = 2100,
                likeCount = 156,
                commentCount = 45,
                isTop = false,
                createdAt = "2024-01-13T16:45:00Z",
                extra = mapOf("category" to "技术教程", "author" to "KMP专家")
            )
        )
    }
}
