package com.example.kmpuniversalapp.features.message

import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val id: String,
    val title: String,
    val content: String,
    val type: MessageType,
    val senderId: String,
    val senderName: String,
    val senderAvatar: String? = null,
    val isRead: Boolean = false,
    val createdAt: String,
    val extra: Map<String, String> = emptyMap()
)

@Serializable
enum class MessageType {
    SYSTEM,     // 系统通知
    PRIVATE,    // 私信
    COMMENT,    // 评论回复
    LIKE,       // 点赞提醒
    FOLLOW      // 关注动态
}
