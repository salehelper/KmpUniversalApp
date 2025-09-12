/*
 * 数据同步接口定义
 * 参考 NowInAndroid 的设计思路
 */

package com.example.kmpuniversalapp.core.data

/**
 * 可同步数据源的标记接口
 * 表示该数据源可以与远程数据源进行同步
 */
interface Syncable {
    /**
     * 与远程数据源同步本地数据
     * @param synchronizer 同步器实例
     * @return 同步是否成功
     */
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

/**
 * 数据同步器接口
 * 负责管理本地数据与远程数据源之间的同步
 */
interface Synchronizer {
    /**
     * 获取变更列表版本信息
     */
    suspend fun getChangeListVersions(): ChangeListVersions

    /**
     * 更新变更列表版本信息
     */
    suspend fun updateChangeListVersions(update: ChangeListVersions.() -> ChangeListVersions)

    /**
     * 语法糖：为 Syncable 提供便捷的同步方法
     */
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

/**
 * 变更列表版本信息
 */
data class ChangeListVersions(
    val newsVersion: Int = 0,
    val userVersion: Int = 0,
    val weatherVersion: Int = 0,
    val lastSyncTime: Long = 0L
) {
    fun updateNewsVersion(version: Int) = copy(newsVersion = version)
    fun updateUserVersion(version: Int) = copy(userVersion = version)
    fun updateWeatherVersion(version: Int) = copy(weatherVersion = version)
    fun updateLastSyncTime(time: Long) = copy(lastSyncTime = time)
}
