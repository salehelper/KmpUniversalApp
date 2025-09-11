package com.example.kmpuniversalapp.core.di

import com.example.kmpuniversalapp.infrastructure.network.HttpClient
import com.example.kmpuniversalapp.infrastructure.storage.DataStoreManager
import com.example.kmpuniversalapp.core.utils.log.AppLogger
import com.example.kmpuniversalapp.features.home.HomeApiService
import com.example.kmpuniversalapp.features.search.SearchApiService
// import com.example.kmpuniversalapp.database.AppDatabase
// import com.example.kmpuniversalapp.database.UserDao
// import com.example.kmpuniversalapp.database.SearchHistoryDao
// import com.example.kmpuniversalapp.database.MessageDao
// import com.example.kmpuniversalapp.database.BannerDao
// 暂时注释导航相关导入，避免编译错误
// import com.example.kmpuniversalapp.navigation.AppComponent
// import com.example.kmpuniversalapp.navigation.DefaultAppComponent
// import com.example.kmpuniversalapp.navigation.GetXStyleRouter
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

/**
 * 应用依赖注入模块
 * 使用Koin管理所有组件的依赖关系，降低耦合度
 */
val appModule = module {
    
    // 核心服务
    single { HttpClient.create() }
    single { DataStoreManager(get()) }
    single { AppLogger }
    
    // 数据库 - 暂时注释
    // single { AppDatabase(get()) }
    // single { get<AppDatabase>().getUserDao() }
    // single { get<AppDatabase>().getSearchHistoryDao() }
    // single { get<AppDatabase>().getMessageDao() }
    // single { get<AppDatabase>().getBannerDao() }
    
    // API服务
    singleOf(::HomeApiService)
    singleOf(::SearchApiService)
    
    // 导航组件 - 暂时注释，避免编译错误
    // single<AppComponent> { DefaultAppComponent(get()) }
    // single<GetXStyleRouter> { GetXStyleRouter(get()) }
    
    // ViewModel - 使用viewModel语法
    // 注意：这些需要平台特定的lifecycle参数
    // 在平台特定模块中会重新定义
}

/**
 * 网络模块
 */
val networkModule = module {
    single { HttpClient.create() }
    single { HttpClient.createWithAuth("") } // 带认证的客户端
}

/**
 * 存储模块
 */
val storageModule = module {
    single { DataStoreManager(get()) }
}

/**
 * 业务模块
 */
val homeModule = module {
    // singleOf(::HomeApiService)
    // factory { HomeViewModel(get(), get()) }
}

val searchModule = module {
    // singleOf(::SearchApiService)
    // factory { SearchViewModel(get(), get()) }
}

val accountModule = module {
    // factory { AccountViewModel(get(), get()) }
}

val messageModule = module {
    // factory { MessageViewModel(get(), get()) }
}

val profileModule = module {
    // factory { ProfileViewModel(get(), get()) }
}
