package com.example.kmpuniversalapp.domain.repository

import com.example.kmpuniversalapp.domain.model.News
import com.example.kmpuniversalapp.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow

/**
 * 资讯仓储接口
 * 按照DDD原则，定义仓储契约
 */
interface NewsRepository {
    /**
     * 获取所有资讯
     */
    suspend fun getAllNews(): Flow<List<News>>
    
    /**
     * 根据ID获取资讯
     */
    suspend fun getNewsById(id: String): News?
    
    /**
     * 根据分类获取资讯
     */
    suspend fun getNewsByCategory(category: NewsCategory): Flow<List<News>>
    
    /**
     * 获取热门资讯
     */
    suspend fun getHotNews(): Flow<List<News>>
    
    /**
     * 获取推荐资讯
     */
    suspend fun getRecommendedNews(): Flow<List<News>>
    
    /**
     * 获取置顶资讯
     */
    suspend fun getTopNews(): Flow<List<News>>
    
    /**
     * 搜索资讯
     */
    suspend fun searchNews(query: String): Flow<List<News>>
    
    /**
     * 分页获取资讯
     */
    suspend fun getNewsPage(page: Int, pageSize: Int): Flow<NewsPage>
    
    /**
     * 增加浏览量
     */
    suspend fun incrementViewCount(id: String): Result<Unit>
    
    /**
     * 点赞资讯
     */
    suspend fun likeNews(id: String): Result<Unit>
    
    /**
     * 取消点赞
     */
    suspend fun unlikeNews(id: String): Result<Unit>
    
    /**
     * 获取资讯统计
     */
    suspend fun getNewsStatistics(): Flow<NewsStatistics>
}

/**
 * 资讯分页信息
 */
data class NewsPage(
    val news: List<News>,
    val currentPage: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val totalCount: Int
)

/**
 * 资讯统计信息
 */
data class NewsStatistics(
    val totalCount: Int,
    val hotNewsCount: Int,
    val recommendedCount: Int,
    val topNewsCount: Int,
    val totalViews: Long,
    val totalLikes: Long
)
