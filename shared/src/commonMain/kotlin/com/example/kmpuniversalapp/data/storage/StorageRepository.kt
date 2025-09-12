package com.example.kmpuniversalapp.data.storage

import com.example.kmpuniversalapp.core.base.Result
import com.example.kmpuniversalapp.core.base.AppError
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.models.User
import com.example.kmpuniversalapp.core.models.NewsItem
import com.example.kmpuniversalapp.core.models.WeatherData
import com.example.kmpuniversalapp.core.utils.getSimpleClassName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 存储仓库
 * 处理所有本地数据存储和检索
 */
class StorageRepository(
    private val storage: IStorage,
    private val logger: ILogger
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    /**
     * 保存用户信息
     */
    suspend fun saveUser(user: User): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存用户信息: ${user.id}")
            
            val userJson = json.encodeToString(user)
            storage.putString(Constants.StorageKeys.USER_INFO, userJson)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取用户信息
     */
    suspend fun getUser(): Result<User?> {
        return try {
            val userJson = storage.getString(Constants.StorageKeys.USER_INFO, "")
            if (userJson.isNotEmpty()) {
                val user = json.decodeFromString<User>(userJson)
                Result.Success(user)
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 删除用户信息
     */
    suspend fun deleteUser(): Result<Unit> {
        return try {
            logger.i("StorageRepository", "删除用户信息")
            
            storage.remove(Constants.StorageKeys.USER_INFO)
            storage.remove(Constants.StorageKeys.USER_TOKEN)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "删除用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "删除用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存新闻列表
     */
    suspend fun saveNewsList(newsList: List<NewsItem>, category: String = "all"): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存新闻列表: ${newsList.size}条, 分类: $category")
            
            val newsJson = json.encodeToString(newsList)
            storage.putString("news_list_$category", newsJson)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存新闻列表失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存新闻列表失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取新闻列表
     */
    suspend fun getNewsList(category: String = "all"): Result<List<NewsItem>> {
        return try {
            val newsJson = storage.getString("news_list_$category", "")
            if (newsJson.isNotEmpty()) {
                val newsList = json.decodeFromString<List<NewsItem>>(newsJson)
                Result.Success(newsList)
            } else {
                Result.Success(emptyList())
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取新闻列表失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取新闻列表失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存新闻详情
     */
    suspend fun saveNewsDetail(news: NewsItem): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存新闻详情: ${news.id}")
            
            val newsJson = json.encodeToString(news)
            storage.putString("news_detail_${news.id}", newsJson)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存新闻详情失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存新闻详情失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取新闻详情
     */
    suspend fun getNewsDetail(newsId: String): Result<NewsItem?> {
        return try {
            val newsJson = storage.getString("news_detail_$newsId")
            if (newsJson.isNotEmpty()) {
                val news = json.decodeFromString<NewsItem>(newsJson)
                Result.Success(news)
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取新闻详情失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取新闻详情失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存天气信息
     */
    suspend fun saveWeather(weather: WeatherData, city: String): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存天气信息: $city")
            
            val weatherJson = json.encodeToString(weather)
            storage.putString("weather_$city", weatherJson)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存天气信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存天气信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取天气信息
     */
    suspend fun getWeather(city: String): Result<WeatherData?> {
        return try {
            val weatherJson = storage.getString("weather_$city")
            if (weatherJson.isNotEmpty()) {
                val weather = json.decodeFromString<WeatherData>(weatherJson)
                Result.Success(weather)
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取天气信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取天气信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存搜索历史
     */
    suspend fun saveSearchHistory(query: String): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存搜索历史: $query")
            
            val historyJson = storage.getString("search_history")
            val history = historyJson?.let { 
                json.decodeFromString<MutableList<String>>(it)
            } ?: mutableListOf()
            
            // 避免重复
            if (!history.contains(query)) {
                history.add(0, query) // 添加到开头
                // 限制历史记录数量
                if (history.size > 50) {
                    history.removeAt(history.size - 1)
                }
                
                val updatedHistoryJson = json.encodeToString(history)
                storage.putString("search_history", updatedHistoryJson)
            }
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存搜索历史失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存搜索历史失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取搜索历史
     */
    suspend fun getSearchHistory(): Result<List<String>> {
        return try {
            val historyJson = storage.getString("search_history")
            if (historyJson.isNotEmpty()) {
                val history = json.decodeFromString<List<String>>(historyJson)
                Result.Success(history)
            } else {
                Result.Success(emptyList())
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取搜索历史失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取搜索历史失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 清空搜索历史
     */
    suspend fun clearSearchHistory(): Result<Unit> {
        return try {
            logger.i("StorageRepository", "清空搜索历史")
            
            storage.remove("search_history")
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "清空搜索历史失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "清空搜索历史失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存收藏的新闻
     */
    suspend fun saveFavoriteNews(newsId: String): Result<Unit> {
        return try {
            logger.i("StorageRepository", "保存收藏新闻: $newsId")
            
            val favoritesJson = storage.getString("favorite_news")
            val favorites = favoritesJson?.let { 
                json.decodeFromString<MutableSet<String>>(it)
            } ?: mutableSetOf()
            
            favorites.add(newsId)
            val updatedFavoritesJson = json.encodeToString(favorites)
            storage.putString("favorite_news", updatedFavoritesJson)
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "保存收藏新闻失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存收藏新闻失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 删除收藏的新闻
     */
    suspend fun removeFavoriteNews(newsId: String): Result<Unit> {
        return try {
            logger.i("StorageRepository", "删除收藏新闻: $newsId")
            
            val favoritesJson = storage.getString("favorite_news")
            if (favoritesJson.isNotEmpty()) {
                val favorites = json.decodeFromString<MutableSet<String>>(favoritesJson)
                favorites.remove(newsId)
                val updatedFavoritesJson = json.encodeToString(favorites)
                storage.putString("favorite_news", updatedFavoritesJson)
            }
            
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("StorageRepository", "删除收藏新闻失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "删除收藏新闻失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取收藏的新闻列表
     */
    suspend fun getFavoriteNews(): Result<Set<String>> {
        return try {
            val favoritesJson = storage.getString("favorite_news")
            if (favoritesJson.isNotEmpty()) {
                val favorites = json.decodeFromString<Set<String>>(favoritesJson)
                Result.Success(favorites)
            } else {
                Result.Success(emptySet())
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "获取收藏新闻失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取收藏新闻失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 检查新闻是否已收藏
     */
    suspend fun isNewsFavorite(newsId: String): Result<Boolean> {
        return try {
            val favoritesJson = storage.getString("favorite_news")
            if (favoritesJson.isNotEmpty()) {
                val favorites = json.decodeFromString<Set<String>>(favoritesJson)
                Result.Success(favorites.contains(newsId))
            } else {
                Result.Success(false)
            }
        } catch (e: Exception) {
            logger.e("StorageRepository", "检查收藏状态失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "检查收藏状态失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
}
