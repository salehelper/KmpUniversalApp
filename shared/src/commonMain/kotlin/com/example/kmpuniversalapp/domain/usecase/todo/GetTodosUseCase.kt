package com.example.kmpuniversalapp.domain.usecase.todo

import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.model.Priority
import com.example.kmpuniversalapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

/**
 * 获取待办事项用例
 * 按照DDD原则，封装业务逻辑
 */
class GetTodosUseCase(
    private val todoRepository: TodoRepository
) {
    /**
     * 获取所有待办事项
     */
    suspend operator fun invoke(): Flow<List<Todo>> {
        return todoRepository.getAllTodos()
    }
    
    /**
     * 根据优先级获取待办事项
     */
    suspend operator fun invoke(priority: Priority): Flow<List<Todo>> {
        return todoRepository.getTodosByPriority(priority)
    }
    
    /**
     * 获取待完成的待办事项
     */
    suspend fun getPendingTodos(): Flow<List<Todo>> {
        return todoRepository.getPendingTodos()
    }
    
    /**
     * 获取已完成的待办事项
     */
    suspend fun getCompletedTodos(): Flow<List<Todo>> {
        return todoRepository.getCompletedTodos()
    }
    
    /**
     * 根据分类获取待办事项
     */
    suspend fun getTodosByCategory(category: String): Flow<List<Todo>> {
        return todoRepository.getTodosByCategory(category)
    }
    
    /**
     * 搜索待办事项
     */
    suspend fun searchTodos(query: String): Flow<List<Todo>> {
        return todoRepository.searchTodos(query)
    }
}
