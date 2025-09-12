/*
 * 获取新闻用例
 * 参考 NowInAndroid 的 UseCase 设计模式
 */

package com.example.kmpuniversalapp.core.domain

import com.example.kmpuniversalapp.core.data.repository.NewsRepository
import com.example.kmpuniversalapp.core.data.repository.NewsResourceQuery
import com.example.kmpuniversalapp.core.models.NewsItem
import kotlinx.coroutines.flow.Flow

/**
 * 获取新闻用例
 * 封装获取新闻列表的业务逻辑
 */
class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    /**
     * 获取新闻列表
     * @param category 分类过滤
     * @param searchQuery 搜索关键词
     * @param page 页码
     * @param pageSize 每页大小
     * @return 新闻列表 Flow
     */
    operator fun invoke(
        category: String? = null,
        searchQuery: String? = null,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<List<NewsItem>> {
        val query = NewsResourceQuery(
            category = category,
            searchQuery = searchQuery,
            page = page,
            pageSize = pageSize
        )
        return newsRepository.getNewsResources(query)
    }

    /**
     * 获取分类新闻
     * @param category 分类
     * @param page 页码
     * @param pageSize 每页大小
     * @return 分类新闻 Flow
     */
    fun getNewsByCategory(
        category: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<List<NewsItem>> {
        return newsRepository.getNewsByCategory(category, page, pageSize)
    }

    /**
     * 搜索新闻
     * @param query 搜索关键词
     * @param page 页码
     * @param pageSize 每页大小
     * @return 搜索结果 Flow
     */
    fun searchNews(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<List<NewsItem>> {
        return newsRepository.searchNews(query, page, pageSize)
    }
}
