/*
 * 数据同步工具类
 * 参考 NowInAndroid 的同步机制设计
 */

package com.example.kmpuniversalapp.core.data

import com.example.kmpuniversalapp.core.ILogger
import kotlin.coroutines.cancellation.CancellationException

/**
 * 网络变更列表数据类
 */
data class NetworkChangeList(
    val id: String,
    val changeListVersion: Int,
    val isDelete: Boolean = false
)

/**
 * 尝试执行代码块，返回成功或失败的 Result
 * 确保不会破坏结构化并发
 */
private suspend fun <T> suspendRunCatching(
    logger: ILogger,
    block: suspend () -> T
): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    logger.e("SyncUtilities", "同步操作失败", exception)
    Result.failure(exception)
}

/**
 * 变更列表同步工具函数
 * 参考 NowInAndroid 的 changeListSync 实现
 * 
 * @param versionReader 读取当前版本信息
 * @param changeListFetcher 获取变更列表
 * @param versionUpdater 更新版本信息
 * @param modelDeleter 删除模型
 * @param modelUpdater 更新模型
 */
suspend fun Synchronizer.changeListSync(
    logger: ILogger,
    versionReader: (ChangeListVersions) -> Int,
    changeListFetcher: suspend (Int) -> List<NetworkChangeList>,
    versionUpdater: ChangeListVersions.(Int) -> ChangeListVersions,
    modelDeleter: suspend (List<String>) -> Unit,
    modelUpdater: suspend (List<String>) -> Unit,
) = suspendRunCatching(logger) {
    // 获取自上次同步以来的变更列表（类似 git fetch）
    val currentVersion = versionReader(getChangeListVersions())
    val changeList = changeListFetcher(currentVersion)
    if (changeList.isEmpty()) return@suspendRunCatching true

    val (deleted, updated) = changeList.partition(NetworkChangeList::isDelete)

    // 删除服务端已删除的模型
    modelDeleter(deleted.map(NetworkChangeList::id))

    // 使用变更列表，拉取并保存变更（类似 git pull）
    modelUpdater(updated.map(NetworkChangeList::id))

    // 更新最后同步版本（类似更新本地 git HEAD）
    val latestVersion = changeList.last().changeListVersion
    updateChangeListVersions {
        versionUpdater(latestVersion)
    }
}.isSuccess

/**
 * 批量同步大小常量
 * 用于优化客户端和服务端的序列化/反序列化成本
 */
const val SYNC_BATCH_SIZE = 40
