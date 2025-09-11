package com.example.kmpuniversalapp.data.repository

import com.example.kmpuniversalapp.domain.model.News
import com.example.kmpuniversalapp.domain.model.NewsCategory
import com.example.kmpuniversalapp.domain.repository.NewsRepository
import com.example.kmpuniversalapp.domain.repository.NewsPage
import com.example.kmpuniversalapp.domain.repository.NewsStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 资讯仓储实现
 * 按照DDD原则，实现仓储接口
 */
class NewsRepositoryImpl : NewsRepository {
    
    // 模拟数据存储
    private val news = mutableListOf<News>()
    
    init {
        // 初始化一些模拟数据
        news.addAll(
            listOf(
                News(
                    id = "1",
                    title = "Kotlin Multiplatform 1.9.0 正式发布",
                    content = "Kotlin Multiplatform 1.9.0 带来了许多新特性和改进，包括更好的性能优化、增强的跨平台支持以及更稳定的API。这个版本特别关注了Compose Multiplatform的集成，为开发者提供了更流畅的跨平台开发体验。",
                    summary = "Kotlin Multiplatform 1.9.0 正式发布，带来性能优化和新特性",
                    author = "JetBrains团队",
                    category = NewsCategory.TECHNOLOGY,
                    tags = listOf("Kotlin", "Multiplatform", "Compose"),
                    imageUrl = "https://example.com/kmp-1.9.0.jpg",
                    source = "JetBrains官方博客",
                    publishedAt = "2024-01-01T10:00:00Z",
                    updatedAt = "2024-01-01T10:00:00Z",
                    viewCount = 1250,
                    likeCount = 89,
                    commentCount = 23,
                    isTop = true,
                    isHot = true,
                    isRecommended = true
                ),
                News(
                    id = "2",
                    title = "Compose Multiplatform 1.7.0 新功能详解",
                    content = "Compose Multiplatform 1.7.0 引入了许多令人兴奋的新功能，包括改进的动画系统、更好的状态管理以及增强的UI组件。这些改进使得跨平台UI开发变得更加简单和高效。",
                    summary = "Compose Multiplatform 1.7.0 带来新功能和改进",
                    author = "Compose团队",
                    category = NewsCategory.TECHNOLOGY,
                    tags = listOf("Compose", "Multiplatform", "UI"),
                    imageUrl = "https://example.com/compose-1.7.0.jpg",
                    source = "Compose官方文档",
                    publishedAt = "2024-01-02T14:30:00Z",
                    updatedAt = "2024-01-02T14:30:00Z",
                    viewCount = 890,
                    likeCount = 67,
                    commentCount = 15,
                    isTop = false,
                    isHot = true,
                    isRecommended = true
                ),
                News(
                    id = "3",
                    title = "移动应用开发趋势分析",
                    content = "2024年移动应用开发呈现出新的趋势，跨平台开发框架越来越受到开发者的青睐。Kotlin Multiplatform、Flutter和React Native等框架在性能和开发效率方面都有显著提升。",
                    summary = "2024年移动应用开发趋势分析",
                    author = "技术分析师",
                    category = NewsCategory.TECHNOLOGY,
                    tags = listOf("移动开发", "趋势", "跨平台"),
                    imageUrl = "https://example.com/mobile-trends.jpg",
                    source = "技术资讯网",
                    publishedAt = "2024-01-03T09:15:00Z",
                    updatedAt = "2024-01-03T09:15:00Z",
                    viewCount = 567,
                    likeCount = 34,
                    commentCount = 8,
                    isTop = false,
                    isHot = false,
                    isRecommended = false
                ),
                News(
                    id = "4",
                    title = "企业级应用架构设计最佳实践",
                    content = "在企业级应用开发中，良好的架构设计是成功的关键。本文介绍了领域驱动设计(DDD)、清洁架构(Clean Architecture)等设计模式在移动应用开发中的应用。",
                    summary = "企业级应用架构设计最佳实践指南",
                    author = "架构师",
                    category = NewsCategory.TECHNOLOGY,
                    tags = listOf("架构", "DDD", "最佳实践"),
                    imageUrl = "https://example.com/architecture.jpg",
                    source = "架构师杂志",
                    publishedAt = "2024-01-04T16:45:00Z",
                    updatedAt = "2024-01-04T16:45:00Z",
                    viewCount = 432,
                    likeCount = 28,
                    commentCount = 12,
                    isTop = false,
                    isHot = false,
                    isRecommended = true
                ),
                News(
                    id = "5",
                    title = "Kotlin协程在移动开发中的应用",
                    content = "Kotlin协程为异步编程提供了优雅的解决方案，在移动应用开发中有着广泛的应用。本文深入探讨了协程的使用场景、最佳实践以及常见陷阱。",
                    summary = "Kotlin协程在移动开发中的应用和最佳实践",
                    author = "Kotlin专家",
                    category = NewsCategory.TECHNOLOGY,
                    tags = listOf("Kotlin", "协程", "异步编程"),
                    imageUrl = "https://example.com/coroutines.jpg",
                    source = "Kotlin官方博客",
                    publishedAt = "2024-01-05T11:20:00Z",
                    updatedAt = "2024-01-05T11:20:00Z",
                    viewCount = 678,
                    likeCount = 45,
                    commentCount = 19,
                    isTop = false,
                    isHot = true,
                    isRecommended = false
                )
            )
        )
    }
    
    override suspend fun getAllNews(): Flow<List<News>> = flow {
        emit(news.toList())
    }
    
    override suspend fun getNewsById(id: String): News? {
        return news.find { it.id == id }
    }
    
    override suspend fun getNewsByCategory(category: NewsCategory): Flow<List<News>> = flow {
        emit(news.filter { it.category == category })
    }
    
    override suspend fun getHotNews(): Flow<List<News>> = flow {
        emit(news.filter { it.isHot })
    }
    
    override suspend fun getRecommendedNews(): Flow<List<News>> = flow {
        emit(news.filter { it.isRecommended })
    }
    
    override suspend fun getTopNews(): Flow<List<News>> = flow {
        emit(news.filter { it.isTop })
    }
    
    override suspend fun searchNews(query: String): Flow<List<News>> = flow {
        val searchQuery = query.lowercase()
        emit(news.filter { 
            it.title.lowercase().contains(searchQuery) ||
            it.content.lowercase().contains(searchQuery) ||
            it.summary?.lowercase()?.contains(searchQuery) == true ||
            it.tags.any { tag -> tag.lowercase().contains(searchQuery) }
        })
    }
    
    override suspend fun getNewsPage(page: Int, pageSize: Int): Flow<NewsPage> = flow {
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, news.size)
        val pageNews = news.subList(startIndex, endIndex)
        val totalPages = (news.size + pageSize - 1) / pageSize
        
        emit(
            NewsPage(
                news = pageNews,
                currentPage = page,
                totalPages = totalPages,
                hasNextPage = page < totalPages,
                totalCount = news.size
            )
        )
    }
    
    override suspend fun incrementViewCount(id: String): Result<Unit> {
        return try {
            val newsItem = news.find { it.id == id }
            if (newsItem != null) {
                val index = news.indexOfFirst { it.id == id }
                val updatedNews = newsItem.copy(viewCount = newsItem.viewCount + 1)
                news[index] = updatedNews
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("资讯不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun likeNews(id: String): Result<Unit> {
        return try {
            val newsItem = news.find { it.id == id }
            if (newsItem != null) {
                val index = news.indexOfFirst { it.id == id }
                val updatedNews = newsItem.copy(likeCount = newsItem.likeCount + 1)
                news[index] = updatedNews
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("资讯不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun unlikeNews(id: String): Result<Unit> {
        return try {
            val newsItem = news.find { it.id == id }
            if (newsItem != null) {
                val index = news.indexOfFirst { it.id == id }
                val updatedNews = newsItem.copy(likeCount = maxOf(0, newsItem.likeCount - 1))
                news[index] = updatedNews
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("资讯不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getNewsStatistics(): Flow<NewsStatistics> = flow {
        val totalCount = news.size
        val hotNewsCount = news.count { it.isHot }
        val recommendedCount = news.count { it.isRecommended }
        val topNewsCount = news.count { it.isTop }
        val totalViews = news.sumOf { it.viewCount.toLong() }
        val totalLikes = news.sumOf { it.likeCount.toLong() }
        
        emit(
            NewsStatistics(
                totalCount = totalCount,
                hotNewsCount = hotNewsCount,
                recommendedCount = recommendedCount,
                topNewsCount = topNewsCount,
                totalViews = totalViews,
                totalLikes = totalLikes
            )
        )
    }
}
