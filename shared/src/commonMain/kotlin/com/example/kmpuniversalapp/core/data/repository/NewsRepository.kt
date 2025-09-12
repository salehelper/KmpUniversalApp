/*
 * 新闻数据仓库接口
 * 参考 NowInAndroid 的 Repository 设计模式
 */

package com.example.kmpuniversalapp.core.data.repository

import com.example.kmpuniversalapp.core.data.Syncable
import com.example.kmpuniversalapp.core.models.NewsItem
import kotlinx.coroutines.flow.Flow

/**
 * 新闻资源查询参数
 */
data class NewsResourceQuery(
    /**
     * 主题ID过滤，null表示匹配任何主题
     */
    val filterTopicIds: Set<String>? = null,
    /**
     * 新闻ID过滤，null表示匹配任何新闻
     */
    val filterNewsIds: Set<String>? = null,
    /**
     * 分类过滤
     */
    val category: String? = null,
    /**
     * 搜索关键词
     */
    val searchQuery: String? = null,
    /**
     * 分页参数
     */
    val page: Int = 1,
    /**
     * 每页大小
     */
    val pageSize: Int = 20
)

/**
 * 新闻数据仓库接口
 * 实现 Syncable 接口以支持数据同步
 */
interface NewsRepository : Syncable {
    /**
     * 获取新闻列表
     * @param query 查询参数
     * @return 新闻列表的 Flow
     */
    fun getNewsResources(
        query: NewsResourceQuery = NewsResourceQuery()
    ): Flow<List<NewsItem>>

    /**
     * 获取新闻详情
     * @param newsId 新闻ID
     * @return 新闻详情的 Flow
     */
    fun getNewsDetail(newsId: String): Flow<NewsItem?>

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
    ): Flow<List<NewsItem>>

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
    ): Flow<List<NewsItem>>

    /**
     * 刷新新闻数据
     * @return 刷新是否成功
     */
    suspend fun refreshNews(): Result<Unit>

    /**
     * 清除所有新闻数据
     */
    suspend fun clearAllNews(): Result<Unit>
}
