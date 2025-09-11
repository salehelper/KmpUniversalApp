package com.example.kmpuniversalapp.domain.model

import kotlinx.serialization.Serializable

/**
 * 资讯领域模型
 * 按照DDD原则，这是核心业务实体
 */
@Serializable
data class News(
    val id: String,
    val title: String,
    val content: String,
    val summary: String? = null,
    val author: String,
    val category: NewsCategory,
    val tags: List<String> = emptyList(),
    val imageUrl: String? = null,
    val videoUrl: String? = null,
    val source: String,
    val publishedAt: String,
    val updatedAt: String,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isTop: Boolean = false,
    val isHot: Boolean = false,
    val isRecommended: Boolean = false
) {
    /**
     * 业务规则：检查是否为热门资讯
     */
    fun isHotNews(): Boolean = isHot || viewCount > 1000
    
    /**
     * 业务规则：检查是否为推荐资讯
     */
    fun isRecommendedNews(): Boolean = isRecommended || likeCount > 100
    
    /**
     * 业务规则：获取阅读时间估算（基于内容长度）
     */
    fun getEstimatedReadTime(): Int {
        val wordsPerMinute = 200
        val wordCount = content.split(" ").size
        return maxOf(1, wordCount / wordsPerMinute)
    }
}

@Serializable
enum class NewsCategory {
    TECHNOLOGY, BUSINESS, SPORTS, ENTERTAINMENT, HEALTH, EDUCATION, POLITICS, OTHER
}

/**
 * 资讯聚合根
 */
@Serializable
data class NewsList(
    val news: List<News>,
    val totalCount: Int,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean
) {
    /**
     * 业务规则：获取热门资讯
     */
    fun getHotNews(): List<News> = news.filter { it.isHotNews() }
    
    /**
     * 业务规则：获取推荐资讯
     */
    fun getRecommendedNews(): List<News> = news.filter { it.isRecommendedNews() }
    
    /**
     * 业务规则：按分类获取资讯
     */
    fun getNewsByCategory(category: NewsCategory): List<News> = 
        news.filter { it.category == category }
}
