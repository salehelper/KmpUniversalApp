package com.example.kmpuniversalapp.data.network

import com.example.kmpuniversalapp.core.base.Result
import com.example.kmpuniversalapp.core.base.AppError
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.INetworkClient
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.models.User
import com.example.kmpuniversalapp.core.models.NewsItem
import com.example.kmpuniversalapp.core.models.WeatherData
import com.example.kmpuniversalapp.core.models.ApiResponse
import com.example.kmpuniversalapp.core.utils.getSimpleClassName
import kotlinx.serialization.json.Json

/**
 * 网络仓库
 * 处理所有网络请求和数据获取
 */
class NetworkRepository(
    private val networkClient: INetworkClient,
    private val logger: ILogger
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    /**
     * 处理网络响应的辅助函数
     */
    private inline fun <reified T> handleResponse(response: String): Result<T> {
        return try {
            val data = json.decodeFromString<T>(response)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Error(AppError(
                code = Constants.ERROR_CODE_SERIALIZATION,
                message = "解析响应失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(userId: String): Result<User> {
        return try {
            logger.i("NetworkRepository", "获取用户信息: $userId")
            
            // 模拟网络请求
            val response = networkClient.get("/api/users/$userId")
            handleResponse<User>(response)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "获取用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "获取用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 更新用户信息
     */
    suspend fun updateUserInfo(user: User): Result<User> {
        return try {
            logger.i("NetworkRepository", "更新用户信息: ${user.id}")
            
            val userJson = json.encodeToString(user)
            val response = networkClient.put("/api/users/${user.id}", userJson)
            handleResponse<User>(response)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "更新用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "更新用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取新闻列表
     */
    suspend fun getNewsList(page: Int = 1, pageSize: Int = 20): Result<List<NewsItem>> {
        return try {
            logger.i("NetworkRepository", "获取新闻列表: page=$page, pageSize=$pageSize")
            
            val response = networkClient.get("/api/news?page=$page&pageSize=$pageSize")
            val apiResponse = handleResponse<ApiResponse<List<NewsItem>>>(response)
            when (apiResponse) {
                is Result.Success -> Result.Success(apiResponse.data.data)
                is Result.Error -> apiResponse
            }
        } catch (e: Exception) {
            logger.e("NetworkRepository", "获取新闻列表失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "获取新闻列表失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取新闻详情
     */
    suspend fun getNewsDetail(newsId: String): Result<NewsItem> {
        return try {
            logger.i("NetworkRepository", "获取新闻详情: $newsId")
            
            val response = networkClient.get("/api/news/$newsId")
            handleResponse<NewsItem>(response)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "获取新闻详情失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "获取新闻详情失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取天气信息
     */
    suspend fun getWeather(city: String): Result<WeatherData> {
        return try {
            logger.i("NetworkRepository", "获取天气信息: $city")
            
            val response = networkClient.get("/api/weather?city=$city")
            handleResponse<WeatherData>(response)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "获取天气信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "获取天气信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 搜索新闻
     */
    suspend fun searchNews(query: String, page: Int = 1, pageSize: Int = 20): Result<List<NewsItem>> {
        return try {
            logger.i("NetworkRepository", "搜索新闻: $query")
            
            val response = networkClient.get("/api/news/search?q=$query&page=$page&pageSize=$pageSize")
            val apiResponse = handleResponse<ApiResponse<List<NewsItem>>>(response)
            when (apiResponse) {
                is Result.Success -> Result.Success(apiResponse.data.data)
                is Result.Error -> apiResponse
            }
        } catch (e: Exception) {
            logger.e("NetworkRepository", "搜索新闻失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "搜索新闻失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 上传文件
     */
    suspend fun uploadFile(filePath: String, fileName: String): Result<String> {
        return try {
            logger.i("NetworkRepository", "上传文件: $fileName")
            
            val response = networkClient.post("/api/upload", json.encodeToString(mapOf(
                "filePath" to filePath,
                "fileName" to fileName
            )))
            val result = handleResponse<Map<String, String>>(response)
            when (result) {
                is Result.Success -> Result.Success(result.data["url"] ?: "")
                is Result.Error -> result
            }
        } catch (e: Exception) {
            logger.e("NetworkRepository", "上传文件失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "上传文件失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 下载文件
     */
    suspend fun downloadFile(url: String, savePath: String): Result<Unit> {
        return try {
            logger.i("NetworkRepository", "下载文件: $url")
            
            // 模拟下载文件
            val response = networkClient.get(url)
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "下载文件失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "下载文件失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 发送反馈
     */
    suspend fun sendFeedback(feedback: String, contact: String? = null): Result<Unit> {
        return try {
            logger.i("NetworkRepository", "发送反馈")
            
            val data = mapOf(
                "feedback" to feedback,
                "contact" to (contact ?: "")
            )
            val response = networkClient.post("/api/feedback", json.encodeToString(data))
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "发送反馈失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_NETWORK,
                message = "发送反馈失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 检查网络连接状态
     */
    suspend fun checkConnection(): Result<Boolean> {
        return try {
            logger.d("NetworkRepository", "检查网络连接状态")
            
            val response = networkClient.get("/api/health")
            Result.Success(true)
        } catch (e: Exception) {
            logger.e("NetworkRepository", "检查网络连接失败", e)
            Result.Success(false)
        }
    }
}
