/*
 * 用户数据仓库接口
 * 参考 NowInAndroid 的 Repository 设计模式
 */

package com.example.kmpuniversalapp.core.data.repository

import com.example.kmpuniversalapp.core.data.Syncable
import com.example.kmpuniversalapp.core.models.User
import kotlinx.coroutines.flow.Flow

/**
 * 用户数据仓库接口
 * 实现 Syncable 接口以支持数据同步
 */
interface UserDataRepository : Syncable {
    /**
     * 获取当前用户信息
     * @return 用户信息的 Flow
     */
    val userData: Flow<User?>

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息 Flow
     */
    fun getUser(userId: String): Flow<User?>

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    suspend fun updateUser(user: User): Result<Unit>

    /**
     * 删除用户
     * @param userId 用户ID
     * @return 删除结果
     */
    suspend fun deleteUser(userId: String): Result<Unit>

    /**
     * 清除所有用户数据
     * @return 清除结果
     */
    suspend fun clearAllUsers(): Result<Unit>

    /**
     * 检查用户是否存在
     * @param userId 用户ID
     * @return 是否存在
     */
    suspend fun isUserExists(userId: String): Boolean

    /**
     * 获取用户收藏的主题
     * @param userId 用户ID
     * @return 收藏主题列表
     */
    fun getFollowedTopics(userId: String): Flow<Set<String>>

    /**
     * 关注主题
     * @param userId 用户ID
     * @param topicId 主题ID
     * @return 关注结果
     */
    suspend fun followTopic(userId: String, topicId: String): Result<Unit>

    /**
     * 取消关注主题
     * @param userId 用户ID
     * @param topicId 主题ID
     * @return 取消关注结果
     */
    suspend fun unfollowTopic(userId: String, topicId: String): Result<Unit>
}
