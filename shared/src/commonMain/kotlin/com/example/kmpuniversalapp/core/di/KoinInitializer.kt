package com.example.kmpuniversalapp.core.di

import com.example.kmpuniversalapp.features.home.homeModule
import com.example.kmpuniversalapp.features.search.searchModule
import com.example.kmpuniversalapp.features.account.accountModule
import com.example.kmpuniversalapp.features.message.messageModule
import com.example.kmpuniversalapp.features.profile.profileModule
import com.example.kmpuniversalapp.infrastructure.network.networkModule
import com.example.kmpuniversalapp.infrastructure.storage.storageModule
// import com.example.kmpuniversalapp.infrastructure.database.databaseModule
import com.example.kmpuniversalapp.core.di.coreInterfacesModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

/**
 * Koin依赖注入初始化器
 * 统一管理依赖注入的启动和停止
 */
object KoinInitializer {
    
    private var isInitialized = false
    
    /**
     * 初始化Koin
     */
    fun init(modules: List<Module> = emptyList()) {
        if (!isInitialized) {
            startKoin {
                modules(
                    coreInterfacesModule,  // 核心接口模块
                    // databaseModule,        // 数据库模块 - 暂时注释
                    appModule,
                    networkModule,
                    storageModule,
                    homeModule,
                    searchModule,
                    accountModule,
                    messageModule,
                    profileModule
                )
                modules(modules) // 添加额外的模块（包括平台特定模块）
            }
            isInitialized = true
        }
    }
    
    /**
     * 停止Koin
     */
    fun stop() {
        if (isInitialized) {
            stopKoin()
            isInitialized = false
        }
    }
    
    /**
     * 检查是否已初始化
     */
    fun isInitialized(): Boolean = isInitialized
}
