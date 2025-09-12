/*
 * 同步管理器接口
 * 参考 NowInAndroid 的同步机制设计
 */

package com.example.kmpuniversalapp.core.data.sync

import com.example.kmpuniversalapp.core.data.ChangeListVersions
import com.example.kmpuniversalapp.core.data.Synchronizer

/**
 * 同步管理器接口
 * 负责管理所有数据源的同步
 */
interface SyncManager : Synchronizer {
    /**
     * 同步所有数据源
     * @return 同步是否成功
     */
    suspend fun syncAll(): Boolean

    /**
     * 同步新闻数据
     * @return 同步是否成功
     */
    suspend fun syncNews(): Boolean

    /**
     * 同步用户数据
     * @return 同步是否成功
     */
    suspend fun syncUserData(): Boolean

    /**
     * 检查是否需要同步
     * @return 是否需要同步
     */
    suspend fun shouldSync(): Boolean

    /**
     * 获取最后同步时间
     * @return 最后同步时间戳
     */
    suspend fun getLastSyncTime(): Long
}
