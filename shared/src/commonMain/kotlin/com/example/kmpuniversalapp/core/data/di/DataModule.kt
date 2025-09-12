/*
 * 数据层依赖注入模块
 * 参考 NowInAndroid 的 DataModule 设计
 */

package com.example.kmpuniversalapp.core.data.di

// import com.example.kmpuniversalapp.core.data.repository.NewsRepository
// import com.example.kmpuniversalapp.core.data.repository.UserDataRepository
// import com.example.kmpuniversalapp.core.data.storage.StorageRepository
// import com.example.kmpuniversalapp.core.data.network.NetworkRepository
// import com.example.kmpuniversalapp.core.data.repository.OfflineFirstNewsRepository
// import com.example.kmpuniversalapp.core.data.repository.OfflineFirstUserDataRepository
// import com.example.kmpuniversalapp.core.data.sync.SyncManager
// import com.example.kmpuniversalapp.core.data.sync.SyncManagerImpl
// import org.koin.dsl.bind
// import org.koin.dsl.module

/**
 * 数据层 Koin 模块
 * 提供数据仓库和同步服务的依赖注入
 * 暂时注释掉，等待完善
 */
/*
val dataModule = module {
    // 底层数据源
    single<StorageRepository> { StorageRepository(get(), get()) }
    single<NetworkRepository> { NetworkRepository(get(), get()) }
    
    // 数据仓库实现
    single<NewsRepository> { OfflineFirstNewsRepository(get(), get(), get()) }
    single<UserDataRepository> { OfflineFirstUserDataRepository(get(), get(), get()) }
    
    // 同步服务
    single<SyncManager> { SyncManagerImpl(get(), get(), get(), get(), get()) }
}
*/
