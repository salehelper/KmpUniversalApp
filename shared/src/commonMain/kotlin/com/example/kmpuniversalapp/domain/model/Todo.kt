package com.example.kmpuniversalapp.domain.model

import kotlinx.serialization.Serializable

/**
 * 待办事项领域模型
 * 按照DDD原则，这是核心业务实体
 */
@Serializable
data class Todo(
    val id: String,
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val dueDate: String? = null,
    val category: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val tags: List<String> = emptyList()
) {
    /**
     * 业务规则：检查是否过期
     */
    fun isOverdue(): Boolean {
        return dueDate?.let { due ->
            // 这里应该实现日期比较逻辑
            false // 简化实现
        } ?: false
    }
    
    /**
     * 业务规则：检查是否高优先级
     */
    fun isHighPriority(): Boolean = priority == Priority.HIGH
}

@Serializable
enum class Priority {
    LOW, MEDIUM, HIGH, URGENT
}

/**
 * 待办事项聚合根
 */
@Serializable
data class TodoList(
    val todos: List<Todo>,
    val totalCount: Int,
    val completedCount: Int,
    val pendingCount: Int
) {
    /**
     * 业务规则：计算完成率
     */
    fun getCompletionRate(): Float {
        return if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
    }
    
    /**
     * 业务规则：获取按优先级排序的待办事项
     */
    fun getTodosByPriority(): List<Todo> {
        return todos.sortedWith(compareByDescending<Todo> { it.priority.ordinal })
    }
}
