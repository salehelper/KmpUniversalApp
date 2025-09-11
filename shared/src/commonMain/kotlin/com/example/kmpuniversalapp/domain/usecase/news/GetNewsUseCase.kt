package com.example.kmpuniversalapp.domain.usecase.news

import com.example.kmpuniversalapp.domain.model.News
import com.example.kmpuniversalapp.domain.model.NewsCategory
import com.example.kmpuniversalapp.domain.repository.NewsRepository
import com.example.kmpuniversalapp.domain.repository.NewsPage
import kotlinx.coroutines.flow.Flow

/**
 * 获取资讯用例
 * 按照DDD原则，封装业务逻辑
 */
class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * 获取所有资讯
     */
    suspend operator fun invoke(): Flow<List<News>> {
        return newsRepository.getAllNews()
    }
    
    /**
     * 根据分类获取资讯
     */
    suspend operator fun invoke(category: NewsCategory): Flow<List<News>> {
        return newsRepository.getNewsByCategory(category)
    }
    
    /**
     * 获取热门资讯
     */
    suspend fun getHotNews(): Flow<List<News>> {
        return newsRepository.getHotNews()
    }
    
    /**
     * 获取推荐资讯
     */
    suspend fun getRecommendedNews(): Flow<List<News>> {
        return newsRepository.getRecommendedNews()
    }
    
    /**
     * 获取置顶资讯
     */
    suspend fun getTopNews(): Flow<List<News>> {
        return newsRepository.getTopNews()
    }
    
    /**
     * 搜索资讯
     */
    suspend fun searchNews(query: String): Flow<List<News>> {
        if (query.isBlank()) {
            return newsRepository.getAllNews()
        }
        return newsRepository.searchNews(query)
    }
    
    /**
     * 分页获取资讯
     */
    suspend fun getNewsPage(page: Int, pageSize: Int = 20): Flow<NewsPage> {
        if (page < 1) {
            throw IllegalArgumentException("页码必须大于0")
        }
        if (pageSize < 1 || pageSize > 100) {
            throw IllegalArgumentException("每页大小必须在1-100之间")
        }
        return newsRepository.getNewsPage(page, pageSize)
    }
}

