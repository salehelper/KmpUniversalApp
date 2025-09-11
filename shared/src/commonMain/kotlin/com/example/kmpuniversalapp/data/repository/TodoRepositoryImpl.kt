package com.example.kmpuniversalapp.data.repository

import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.model.Priority
import com.example.kmpuniversalapp.domain.repository.TodoRepository
import com.example.kmpuniversalapp.domain.repository.TodoStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 待办事项仓储实现
 * 按照DDD原则，实现仓储接口
 */
class TodoRepositoryImpl : TodoRepository {
    
    // 模拟数据存储
    private val todos = mutableListOf<Todo>()
    
    init {
        // 初始化一些模拟数据
        todos.addAll(
            listOf(
                Todo(
                    id = "1",
                    title = "完成KMP项目架构设计",
                    description = "按照DDD原则设计项目架构，包括领域模型、用例和仓储",
                    isCompleted = false,
                    priority = Priority.HIGH,
                    category = "工作",
                    createdAt = "2024-01-01T10:00:00Z",
                    updatedAt = "2024-01-01T10:00:00Z",
                    tags = listOf("KMP", "架构", "DDD")
                ),
                Todo(
                    id = "2",
                    title = "学习Compose Multiplatform",
                    description = "深入学习Compose Multiplatform的UI开发技术",
                    isCompleted = true,
                    priority = Priority.MEDIUM,
                    category = "学习",
                    createdAt = "2024-01-02T09:00:00Z",
                    updatedAt = "2024-01-02T15:30:00Z",
                    tags = listOf("Compose", "学习", "UI")
                ),
                Todo(
                    id = "3",
                    title = "优化应用性能",
                    description = "分析并优化应用的性能瓶颈",
                    isCompleted = false,
                    priority = Priority.URGENT,
                    dueDate = "2024-01-15",
                    category = "优化",
                    createdAt = "2024-01-03T14:00:00Z",
                    updatedAt = "2024-01-03T14:00:00Z",
                    tags = listOf("性能", "优化", "分析")
                ),
                Todo(
                    id = "4",
                    title = "编写单元测试",
                    description = "为关键业务逻辑编写单元测试",
                    isCompleted = false,
                    priority = Priority.MEDIUM,
                    category = "测试",
                    createdAt = "2024-01-04T11:00:00Z",
                    updatedAt = "2024-01-04T11:00:00Z",
                    tags = listOf("测试", "单元测试", "质量")
                ),
                Todo(
                    id = "5",
                    title = "更新项目文档",
                    description = "更新项目的README和API文档",
                    isCompleted = false,
                    priority = Priority.LOW,
                    category = "文档",
                    createdAt = "2024-01-05T16:00:00Z",
                    updatedAt = "2024-01-05T16:00:00Z",
                    tags = listOf("文档", "README", "API")
                )
            )
        )
    }
    
    override suspend fun getAllTodos(): Flow<List<Todo>> = flow {
        emit(todos.toList())
    }
    
    override suspend fun getTodoById(id: String): Todo? {
        return todos.find { it.id == id }
    }
    
    override suspend fun getTodosByPriority(priority: Priority): Flow<List<Todo>> = flow {
        emit(todos.filter { it.priority == priority })
    }
    
    override suspend fun getPendingTodos(): Flow<List<Todo>> = flow {
        emit(todos.filter { !it.isCompleted })
    }
    
    override suspend fun getCompletedTodos(): Flow<List<Todo>> = flow {
        emit(todos.filter { it.isCompleted })
    }
    
    override suspend fun getTodosByCategory(category: String): Flow<List<Todo>> = flow {
        emit(todos.filter { it.category == category })
    }
    
    override suspend fun searchTodos(query: String): Flow<List<Todo>> = flow {
        val searchQuery = query.lowercase()
        emit(todos.filter { 
            it.title.lowercase().contains(searchQuery) ||
            it.description?.lowercase()?.contains(searchQuery) == true ||
            it.tags.any { tag -> tag.lowercase().contains(searchQuery) }
        })
    }
    
    override suspend fun addTodo(todo: Todo): Result<Unit> {
        return try {
            todos.add(todo)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateTodo(todo: Todo): Result<Unit> {
        return try {
            val index = todos.indexOfFirst { it.id == todo.id }
            if (index != -1) {
                todos[index] = todo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("待办事项不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun deleteTodo(id: String): Result<Unit> {
        return try {
            val removed = todos.removeAll { it.id == id }
            if (removed) {
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("待办事项不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun markAsCompleted(id: String): Result<Unit> {
        return try {
            val todo = todos.find { it.id == id }
            if (todo != null) {
                val updatedTodo = todo.copy(isCompleted = true, updatedAt = getCurrentTime())
                val index = todos.indexOfFirst { it.id == id }
                todos[index] = updatedTodo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("待办事项不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun markAsPending(id: String): Result<Unit> {
        return try {
            val todo = todos.find { it.id == id }
            if (todo != null) {
                val updatedTodo = todo.copy(isCompleted = false, updatedAt = getCurrentTime())
                val index = todos.indexOfFirst { it.id == id }
                todos[index] = updatedTodo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("待办事项不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getTodoStatistics(): Flow<TodoStatistics> = flow {
        val totalCount = todos.size
        val completedCount = todos.count { it.isCompleted }
        val pendingCount = totalCount - completedCount
        val overdueCount = todos.count { it.isOverdue() }
        val highPriorityCount = todos.count { it.isHighPriority() }
        val completionRate = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
        
        emit(
            TodoStatistics(
                totalCount = totalCount,
                completedCount = completedCount,
                pendingCount = pendingCount,
                overdueCount = overdueCount,
                highPriorityCount = highPriorityCount,
                completionRate = completionRate
            )
        )
    }
    
    private fun getCurrentTime(): String {
        // 简化实现，实际应该返回当前时间
        return "2024-01-06T12:00:00Z"
    }
}
