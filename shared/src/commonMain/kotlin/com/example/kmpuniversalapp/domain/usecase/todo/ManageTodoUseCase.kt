package com.example.kmpuniversalapp.domain.usecase.todo

import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.repository.TodoRepository

/**
 * 管理待办事项用例
 * 按照DDD原则，封装业务逻辑
 */
class ManageTodoUseCase(
    private val todoRepository: TodoRepository
) {
    /**
     * 添加待办事项
     */
    suspend operator fun invoke(todo: Todo): Result<Unit> {
        // 业务规则验证
        if (todo.title.isBlank()) {
            return Result.failure(IllegalArgumentException("待办事项标题不能为空"))
        }
        
        if (todo.title.length > 100) {
            return Result.failure(IllegalArgumentException("待办事项标题不能超过100个字符"))
        }
        
        return todoRepository.addTodo(todo)
    }
    
    /**
     * 更新待办事项
     */
    suspend fun updateTodo(todo: Todo): Result<Unit> {
        // 业务规则验证
        if (todo.title.isBlank()) {
            return Result.failure(IllegalArgumentException("待办事项标题不能为空"))
        }
        
        return todoRepository.updateTodo(todo)
    }
    
    /**
     * 删除待办事项
     */
    suspend fun deleteTodo(id: String): Result<Unit> {
        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("待办事项ID不能为空"))
        }
        
        return todoRepository.deleteTodo(id)
    }
    
    /**
     * 标记为完成
     */
    suspend fun markAsCompleted(id: String): Result<Unit> {
        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("待办事项ID不能为空"))
        }
        
        return todoRepository.markAsCompleted(id)
    }
    
    /**
     * 标记为未完成
     */
    suspend fun markAsPending(id: String): Result<Unit> {
        if (id.isBlank()) {
            return Result.failure(IllegalArgumentException("待办事项ID不能为空"))
        }
        
        return todoRepository.markAsPending(id)
    }
}
