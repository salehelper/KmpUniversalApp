/*
 * 同步管理器实现
 * 参考 NowInAndroid 的同步机制设计
 */

package com.example.kmpuniversalapp.core.data.sync

import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.ITimeProvider
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.data.ChangeListVersions
import com.example.kmpuniversalapp.core.data.Syncable
import com.example.kmpuniversalapp.core.data.repository.NewsRepository
import com.example.kmpuniversalapp.core.data.repository.UserDataRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 同步管理器实现类
 */
class SyncManagerImpl(
    private val storage: IStorage,
    private val timeProvider: ITimeProvider,
    private val logger: ILogger,
    private val newsRepository: NewsRepository,
    private val userDataRepository: UserDataRepository
) : SyncManager {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getChangeListVersions(): ChangeListVersions {
        val versionsJson = storage.getString(Constants.StorageKeys.SYNC_VERSIONS, "")
        return if (versionsJson.isNotEmpty()) {
            try {
                json.decodeFromString(versionsJson)
            } catch (e: Exception) {
                logger.e("SyncManager", "解析同步版本信息失败", e)
                ChangeListVersions()
            }
        } else {
            ChangeListVersions()
        }
    }

    override suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions) {
        val currentVersions = getChangeListVersions()
        val updatedVersions = currentVersions.update()
        val versionsJson = json.encodeToString(updatedVersions)
        storage.putString(Constants.StorageKeys.SYNC_VERSIONS, versionsJson)
    }

    override suspend fun syncAll(): Boolean {
        logger.i("SyncManager", "开始同步所有数据")
        
        val newsResult = syncNews()
        val userResult = syncUserData()
        
        val allSuccess = newsResult && userResult
        
        if (allSuccess) {
            val currentTime = timeProvider.getCurrentTimeMillis()
            updateChangeListVersions { updateLastSyncTime(currentTime) }
            logger.i("SyncManager", "所有数据同步完成")
        } else {
            logger.e("SyncManager", "数据同步部分失败")
        }
        
        return allSuccess
    }

    override suspend fun syncNews(): Boolean {
        logger.i("SyncManager", "开始同步新闻数据")
        return try {
            newsRepository.syncWith(this)
        } catch (e: Exception) {
            logger.e("SyncManager", "同步新闻数据失败", e)
            false
        }
    }

    override suspend fun syncUserData(): Boolean {
        logger.i("SyncManager", "开始同步用户数据")
        return try {
            userDataRepository.syncWith(this)
        } catch (e: Exception) {
            logger.e("SyncManager", "同步用户数据失败", e)
            false
        }
    }

    override suspend fun shouldSync(): Boolean {
        val lastSyncTime = getLastSyncTime()
        val currentTime = timeProvider.getCurrentTimeMillis()
        val syncInterval = Constants.SYNC_INTERVAL_MILLIS
        
        return (currentTime - lastSyncTime) > syncInterval
    }

    override suspend fun getLastSyncTime(): Long {
        val versions = getChangeListVersions()
        return versions.lastSyncTime
    }
}
