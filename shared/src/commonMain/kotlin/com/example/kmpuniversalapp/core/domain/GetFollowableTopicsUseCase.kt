/*
 * 获取可关注主题用例
 * 参考 NowInAndroid 的 GetFollowableTopicsUseCase 设计
 */

package com.example.kmpuniversalapp.core.domain

import com.example.kmpuniversalapp.core.data.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * 可关注的主题数据类
 */
data class FollowableTopic(
    val topicId: String,
    val topicName: String,
    val isFollowed: Boolean
)

/**
 * 主题排序字段枚举
 */
enum class TopicSortField {
    NONE,
    NAME,
    FOLLOW_COUNT
}

/**
 * 获取可关注主题用例
 * 结合用户数据和主题数据，提供可关注的主题列表
 */
class GetFollowableTopicsUseCase(
    private val userDataRepository: UserDataRepository
) {
    /**
     * 获取可关注的主题列表
     * @param userId 用户ID
     * @param sortBy 排序字段
     * @return 可关注主题列表 Flow
     */
    operator fun invoke(
        userId: String,
        sortBy: TopicSortField = TopicSortField.NONE
    ): Flow<List<FollowableTopic>> {
        return combine(
            userDataRepository.getFollowedTopics(userId),
            // 这里应该从 TopicsRepository 获取主题列表
            // 暂时返回空列表作为占位符
            kotlinx.coroutines.flow.flowOf(emptyList<String>())
        ) { followedTopics, allTopics ->
            val followedTopicsSet = followedTopics
            val topics = allTopics.map { topicId ->
                FollowableTopic(
                    topicId = topicId,
                    topicName = topicId, // 简化处理，实际应该从主题数据获取名称
                    isFollowed = topicId in followedTopicsSet
                )
            }
            
            when (sortBy) {
                TopicSortField.NAME -> topics.sortedBy { it.topicName }
                TopicSortField.FOLLOW_COUNT -> topics.sortedByDescending { if (it.isFollowed) 1 else 0 }
                else -> topics
            }
        }
    }

    /**
     * 关注主题
     * @param userId 用户ID
     * @param topicId 主题ID
     * @return 关注结果
     */
    suspend fun followTopic(userId: String, topicId: String): Result<Unit> {
        return userDataRepository.followTopic(userId, topicId)
    }

    /**
     * 取消关注主题
     * @param userId 用户ID
     * @param topicId 主题ID
     * @return 取消关注结果
     */
    suspend fun unfollowTopic(userId: String, topicId: String): Result<Unit> {
        return userDataRepository.unfollowTopic(userId, topicId)
    }
}
