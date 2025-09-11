/**
 * 功能模块接口定义
 * 遵循接口隔离原则，定义功能模块的公共接口
 */
package com.example.kmpuniversalapp.features

/**
 * 首页功能接口
 */
interface IHomeFeature {
    suspend fun loadBanners(): Result<List<Any>>
    suspend fun loadDynamicContent(): Result<List<Any>>
    suspend fun refreshData(): Result<Unit>
}

/**
 * 搜索功能接口
 */
interface ISearchFeature {
    suspend fun search(keyword: String, page: Int, pageSize: Int): Result<SearchResult>
    suspend fun getSuggestions(keyword: String): Result<List<String>>
    suspend fun getSearchHistory(): Result<List<String>>
    suspend fun saveSearchHistory(keyword: String): Result<Unit>
}

/**
 * 账户功能接口
 */
interface IAccountFeature {
    suspend fun login(username: String, password: String): Result<LoginResult>
    suspend fun register(userInfo: UserInfo): Result<RegisterResult>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): Result<UserInfo?>
    suspend fun updateProfile(userInfo: UserInfo): Result<Unit>
}

/**
 * 消息功能接口
 */
interface IMessageFeature {
    suspend fun getMessages(page: Int, pageSize: Int): Result<List<Message>>
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun markAsRead(messageId: String): Result<Unit>
    suspend fun deleteMessage(messageId: String): Result<Unit>
}

/**
 * 个人资料功能接口
 */
interface IProfileFeature {
    suspend fun getProfile(): Result<ProfileInfo>
    suspend fun updateProfile(profileInfo: ProfileInfo): Result<Unit>
    suspend fun uploadAvatar(imageData: ByteArray): Result<String>
    suspend fun changePassword(oldPassword: String, newPassword: String): Result<Unit>
}

// 数据类定义
data class SearchResult(
    val results: List<Any>,
    val total: Int,
    val hasMore: Boolean
)

data class LoginResult(
    val token: String,
    val user: UserInfo
)

data class RegisterResult(
    val success: Boolean,
    val message: String
)

data class UserInfo(
    val id: String,
    val username: String,
    val email: String,
    val avatar: String?
)

data class Message(
    val id: String,
    val content: String,
    val timestamp: Long,
    val isRead: Boolean
)

data class ProfileInfo(
    val id: String,
    val username: String,
    val email: String,
    val avatar: String?,
    val bio: String?
)
