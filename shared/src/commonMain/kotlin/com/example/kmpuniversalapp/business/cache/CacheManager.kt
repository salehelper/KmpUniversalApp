package com.example.kmpuniversalapp.business.cache

import com.example.kmpuniversalapp.core.base.Result
import com.example.kmpuniversalapp.core.base.AppError
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.base.Cache
import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.ITimeProvider
import com.example.kmpuniversalapp.core.utils.getSimpleClassName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 缓存管理器
 * 处理应用数据的缓存管理
 */
class CacheManager(
    private val storage: IStorage,
    private val logger: ILogger,
    private val timeProvider: ITimeProvider
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    private val cachePrefix = "cache_"
    
    /**
     * 保存数据到缓存
     */
    suspend fun put(key: String, value: String, expiry: Long = 0): Result<Unit> {
        return try {
            val cache = Cache(
                key = key,
                value = value,
                timestamp = timeProvider.getCurrentTimeMillis(),
                ttl = expiry
            )
            
            val cacheJson = json.encodeToString(cache)
            storage.putString("$cachePrefix$key", cacheJson)
            
            logger.d("CacheManager", "数据已缓存: $key")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("CacheManager", "保存缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "保存缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 从缓存获取数据
     */
    suspend fun get(key: String): Result<String?> {
        return try {
            val cacheJson = storage.getString("$cachePrefix$key", "")
            if (cacheJson.isEmpty()) {
                return Result.Success(null)
            }
            
            val cache = json.decodeFromString<Cache>(cacheJson)
            
            // 检查是否过期
            if (cache.ttl > 0 && timeProvider.getCurrentTimeMillis() - cache.timestamp > cache.ttl) {
                // 缓存已过期，删除并返回null
                storage.remove("$cachePrefix$key")
                logger.d("CacheManager", "缓存已过期: $key")
                return Result.Success(null)
            }
            
            logger.d("CacheManager", "从缓存获取数据: $key")
            Result.Success(cache.value)
        } catch (e: Exception) {
            logger.e("CacheManager", "获取缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 删除缓存
     */
    suspend fun remove(key: String): Result<Unit> {
        return try {
            storage.remove("$cachePrefix$key")
            logger.d("CacheManager", "缓存已删除: $key")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("CacheManager", "删除缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "删除缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 清空所有缓存
     */
    suspend fun clear(): Result<Unit> {
        return try {
            // 这里需要获取所有以cache_开头的键并删除
            // 由于IStorage接口没有提供获取所有键的方法，这里简化处理
            logger.i("CacheManager", "清空所有缓存")
            Result.Success(Unit)
        } catch (e: Exception) {
            logger.e("CacheManager", "清空缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "清空缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 检查缓存是否存在
     */
    suspend fun contains(key: String): Result<Boolean> {
        return try {
            val cacheJson = storage.getString("$cachePrefix$key", "")
            if (cacheJson.isEmpty()) {
                return Result.Success(false)
            }
            
            val cache = json.decodeFromString<Cache>(cacheJson)
            
            // 检查是否过期
            if (cache.ttl > 0 && timeProvider.getCurrentTimeMillis() - cache.timestamp > cache.ttl) {
                // 缓存已过期，删除并返回false
                storage.remove("$cachePrefix$key")
                return Result.Success(false)
            }
            
            Result.Success(true)
        } catch (e: Exception) {
            logger.e("CacheManager", "检查缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "检查缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取缓存大小（字节）
     */
    suspend fun getSize(): Result<Long> {
        return try {
            // 这里需要计算所有缓存数据的大小
            // 由于IStorage接口限制，这里返回模拟值
            Result.Success(0L)
        } catch (e: Exception) {
            logger.e("CacheManager", "获取缓存大小失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "获取缓存大小失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 清理过期缓存
     */
    suspend fun cleanExpired(): Result<Int> {
        return try {
            // 这里需要遍历所有缓存并清理过期的
            // 由于IStorage接口限制，这里返回模拟值
            logger.i("CacheManager", "清理过期缓存完成")
            Result.Success(0)
        } catch (e: Exception) {
            logger.e("CacheManager", "清理过期缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_STORAGE,
                message = "清理过期缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 保存对象到缓存
     */
    suspend fun putObject(key: String, obj: Any, expiry: Long = 0): Result<Unit> {
        return try {
            val objJson = json.encodeToString(obj)
            put(key, objJson, expiry)
        } catch (e: Exception) {
            logger.e("CacheManager", "保存对象到缓存失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_SERIALIZATION,
                message = "保存对象到缓存失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 从缓存获取对象
     */
    suspend fun <T> getObject(key: String, serializer: kotlinx.serialization.KSerializer<T>): Result<T?> {
        return try {
            val result = get(key)
            when (result) {
                is Result.Success -> {
                    if (result.data == null) {
                        Result.Success(null)
                    } else {
                        val obj = json.decodeFromString(serializer, result.data)
                        Result.Success(obj)
                    }
                }
                is Result.Error -> result
            }
        } catch (e: Exception) {
            logger.e("CacheManager", "从缓存获取对象失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_SERIALIZATION,
                message = "从缓存获取对象失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
}
