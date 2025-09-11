package com.example.kmpuniversalapp.features.message

import com.example.kmpuniversalapp.core.utils.log.AppLogger
import com.example.kmpuniversalapp.core.base.BaseViewModel
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 消息页面ViewModel
 */
class MessageViewModel(
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 消息列表
    private val _messages = MutableStateFlow<List<MessageModel>>(emptyList())
    val messages: StateFlow<List<MessageModel>> = _messages.asStateFlow()
    
    // 加载状态和错误信息继承自BaseViewModel
    
    /**
     * 加载消息列表
     */
    suspend fun loadMessages() {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            // 模拟加载消息数据
            val mockMessages = getMockMessages()
            _messages.value = mockMessages
            
        } catch (e: Exception) {
            _errorMessage.value = "加载消息失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 标记消息为已读
     */
    fun markAsRead(messageId: String) {
        val updatedMessages = _messages.value.map { message ->
            if (message.id == messageId) {
                message.copy(isRead = true)
            } else {
                message
            }
        }
        _messages.value = updatedMessages
    }
    
    /**
     * 删除消息
     */
    fun deleteMessage(messageId: String) {
        val updatedMessages = _messages.value.filter { it.id != messageId }
        _messages.value = updatedMessages
    }
    
    /**
     * 获取未读消息数量
     */
    val unreadCount: Int
        get() = _messages.value.count { !it.isRead }
    
    private fun getMockMessages(): List<MessageModel> {
        return listOf(
            MessageModel(
                id = "msg_001",
                title = "系统通知",
                content = "欢迎使用KMP Universal App！",
                type = MessageType.SYSTEM,
                senderId = "system",
                senderName = "系统",
                isRead = false,
                createdAt = "2024-01-15T10:00:00Z"
            ),
            MessageModel(
                id = "msg_002",
                title = "新评论",
                content = "您的文章收到了新的评论",
                type = MessageType.COMMENT,
                senderId = "user_001",
                senderName = "用户A",
                isRead = true,
                createdAt = "2024-01-14T15:30:00Z"
            ),
            MessageModel(
                id = "msg_003",
                title = "点赞提醒",
                content = "您的动态收到了点赞",
                type = MessageType.LIKE,
                senderId = "user_002",
                senderName = "用户B",
                isRead = false,
                createdAt = "2024-01-13T09:15:00Z"
            )
        )
    }
}
