package com.example.kmpuniversalapp.libs.base

import com.example.kmpuniversalapp.core.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 基础Repository
 * 提供通用的数据访问功能
 */
abstract class BaseRepository {
    
    /**
     * 执行网络请求并返回Result
     */
    protected suspend fun <T> executeRequest(
        request: suspend () -> T
    ): Result<T> {
        return try {
            val data = request()
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
    
    /**
     * 执行网络请求并返回Flow
     */
    protected fun <T> executeRequestFlow(
        request: suspend () -> T
    ): Flow<Result<T>> = flow {
        emit(Result.Loading)
        try {
            val data = request()
            emit(Result.Success(data))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
    
    /**
     * 处理分页数据
     */
    protected fun <T> createPagedData(
        data: List<T>,
        page: Int,
        pageSize: Int
    ): PagedData<T> {
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, data.size)
        val pagedItems = data.subList(startIndex, endIndex)
        
        return PagedData(
            items = pagedItems,
            page = page,
            pageSize = pageSize,
            totalItems = data.size,
            totalPages = (data.size + pageSize - 1) / pageSize,
            hasNextPage = endIndex < data.size,
            hasPreviousPage = page > 1
        )
    }
}

/**
 * 分页数据封装
 */
data class PagedData<T>(
    val items: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean
)
