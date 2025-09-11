package com.example.kmpuniversalapp.domain.repository

import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.model.Priority
import kotlinx.coroutines.flow.Flow

/**
 * 待办事项仓储接口
 * 按照DDD原则，定义仓储契约
 */
interface TodoRepository {
    /**
     * 获取所有待办事项
     */
    suspend fun getAllTodos(): Flow<List<Todo>>
    
    /**
     * 根据ID获取待办事项
     */
    suspend fun getTodoById(id: String): Todo?
    
    /**
     * 根据优先级获取待办事项
     */
    suspend fun getTodosByPriority(priority: Priority): Flow<List<Todo>>
    
    /**
     * 获取待完成的待办事项
     */
    suspend fun getPendingTodos(): Flow<List<Todo>>
    
    /**
     * 获取已完成的待办事项
     */
    suspend fun getCompletedTodos(): Flow<List<Todo>>
    
    /**
     * 根据分类获取待办事项
     */
    suspend fun getTodosByCategory(category: String): Flow<List<Todo>>
    
    /**
     * 搜索待办事项
     */
    suspend fun searchTodos(query: String): Flow<List<Todo>>
    
    /**
     * 添加待办事项
     */
    suspend fun addTodo(todo: Todo): Result<Unit>
    
    /**
     * 更新待办事项
     */
    suspend fun updateTodo(todo: Todo): Result<Unit>
    
    /**
     * 删除待办事项
     */
    suspend fun deleteTodo(id: String): Result<Unit>
    
    /**
     * 标记待办事项为完成
     */
    suspend fun markAsCompleted(id: String): Result<Unit>
    
    /**
     * 标记待办事项为未完成
     */
    suspend fun markAsPending(id: String): Result<Unit>
    
    /**
     * 获取待办事项统计
     */
    suspend fun getTodoStatistics(): Flow<TodoStatistics>
}

/**
 * 待办事项统计信息
 */
data class TodoStatistics(
    val totalCount: Int,
    val completedCount: Int,
    val pendingCount: Int,
    val overdueCount: Int,
    val highPriorityCount: Int,
    val completionRate: Float
)
