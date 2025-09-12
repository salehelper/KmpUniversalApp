/*
 * 获取用户用例
 * 参考 NowInAndroid 的 UseCase 设计模式
 */

package com.example.kmpuniversalapp.core.domain

import com.example.kmpuniversalapp.core.data.repository.UserDataRepository
import com.example.kmpuniversalapp.core.models.User
import kotlinx.coroutines.flow.Flow

/**
 * 获取用户用例
 * 封装用户相关的业务逻辑
 */
class GetUserUseCase(
    private val userDataRepository: UserDataRepository
) {
    /**
     * 获取当前用户信息
     * @return 用户信息 Flow
     */
    operator fun invoke(): Flow<User?> {
        return userDataRepository.userData
    }

    /**
     * 获取指定用户信息
     * @param userId 用户ID
     * @return 用户信息 Flow
     */
    fun getUser(userId: String): Flow<User?> {
        return userDataRepository.getUser(userId)
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    suspend fun updateUser(user: User): Result<Unit> {
        return userDataRepository.updateUser(user)
    }

    /**
     * 检查用户是否存在
     * @param userId 用户ID
     * @return 是否存在
     */
    suspend fun isUserExists(userId: String): Boolean {
        return userDataRepository.isUserExists(userId)
    }
}
