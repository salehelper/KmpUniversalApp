package com.example.kmpuniversalapp.features.search

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class SearchApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }
    
    suspend fun search(
        keyword: String,
        page: Int = 1,
        pageSize: Int = 10,
        type: String? = null,
        category: String? = null
    ): Map<String, Any> {
        return try {
            // 模拟搜索API调用
            val results = getMockSearchResults(keyword, page, pageSize)
            val total = 50 // 模拟总数
            val totalPages = (total + pageSize - 1) / pageSize
            val hasMore = page < totalPages
            
            mapOf(
                "results" to results,
                "total" to total,
                "totalPages" to totalPages,
                "hasMore" to hasMore,
                "currentPage" to page
            )
        } catch (e: Exception) {
            mapOf(
                "results" to emptyList<SearchResultModel>(),
                "total" to 0,
                "totalPages" to 0,
                "hasMore" to false,
                "currentPage" to page
            )
        }
    }
    
    suspend fun getSearchSuggestions(keyword: String): List<String> {
        return try {
            // 模拟搜索建议
            val suggestions = listOf(
                "$keyword 教程",
                "$keyword 最佳实践",
                "$keyword 开发指南",
                "$keyword 性能优化",
                "$keyword 跨平台"
            )
            suggestions.take(5)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getSearchHistory(): List<String> {
        return try {
            // 模拟搜索历史
            listOf(
                "Kotlin Multiplatform",
                "Compose UI",
                "跨平台开发",
                "Android开发",
                "iOS开发"
            )
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    suspend fun getHotSearches(): List<String> {
        return try {
            // 模拟热门搜索
            listOf(
                "KMP",
                "Compose Multiplatform",
                "Kotlin",
                "跨平台",
                "移动开发"
            )
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    private fun getMockSearchResults(keyword: String, page: Int, pageSize: Int): List<SearchResultModel> {
        val allResults = listOf(
            SearchResultModel(
                id = "search_001",
                title = "Kotlin Multiplatform 完整指南",
                summary = "深入学习Kotlin Multiplatform开发，从基础到高级应用...",
                type = "article",
                typeIcon = "📄",
                category = "技术文档",
                author = "KMP专家",
                viewCount = 1500,
                likeCount = 120,
                createdAt = "2024-01-15T10:00:00Z",
                tags = listOf("KMP", "Kotlin", "跨平台"),
                url = "https://example.com/kmp-guide"
            ),
            SearchResultModel(
                id = "search_002",
                title = "Compose Multiplatform UI 设计模式",
                summary = "探索Compose Multiplatform中的各种UI设计模式和最佳实践...",
                type = "video",
                typeIcon = "🎥",
                category = "视频教程",
                author = "UI设计师",
                viewCount = 2300,
                likeCount = 180,
                createdAt = "2024-01-14T15:30:00Z",
                tags = listOf("Compose", "UI", "设计"),
                url = "https://example.com/compose-ui-patterns"
            ),
            SearchResultModel(
                id = "search_003",
                title = "KMP 性能优化实战",
                summary = "分享Kotlin Multiplatform项目中的性能优化经验和技巧...",
                type = "document",
                typeIcon = "📋",
                category = "技术分享",
                author = "性能专家",
                viewCount = 980,
                likeCount = 75,
                createdAt = "2024-01-13T09:15:00Z",
                tags = listOf("性能", "优化", "KMP"),
                url = "https://example.com/kmp-performance"
            )
        )
        
        // 根据关键词过滤结果
        val filteredResults = allResults.filter { result ->
            result.title.contains(keyword, ignoreCase = true) || 
            result.summary.contains(keyword, ignoreCase = true) ||
            result.tags.any { tag -> tag.contains(keyword, ignoreCase = true) }
        }
        
        // 分页
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, filteredResults.size)
        
        return if (startIndex < filteredResults.size) {
            filteredResults.subList(startIndex, endIndex)
        } else {
            emptyList()
        }
    }
}
