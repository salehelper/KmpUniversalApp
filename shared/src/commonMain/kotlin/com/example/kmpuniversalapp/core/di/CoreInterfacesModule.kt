package com.example.kmpuniversalapp.core.di

import com.example.kmpuniversalapp.core.*
import com.example.kmpuniversalapp.core.implementations.*
// import com.example.kmpuniversalapp.core.utils.storage.DataStoreManager // 暂时注释
import com.example.kmpuniversalapp.core.services.AppService
// import com.example.kmpuniversalapp.core.examples.InterfaceUsageExample
import org.koin.dsl.module

/**
 * 核心接口模块
 * 注册所有核心接口的实现类
 */
val coreInterfacesModule = module {
    
    // 核心接口实现
    single<ILogger> { LoggerImpl() }
    single<ITimeProvider> { TimeProviderImpl() }
    single<IDeviceInfo> { DeviceInfoImpl() }
    
    // 存储接口实现 - 使用内存存储
    single<IStorage> { 
        StorageImpl()
    }
    
    // 网络客户端实现
    single<INetworkClient> { 
        NetworkClientImpl(get<ILogger>())
    }
    
    // 应用服务
    single { 
        AppService(
            logger = get<ILogger>(),
            storage = get<IStorage>(),
            networkClient = get<INetworkClient>(),
            timeProvider = get<ITimeProvider>(),
            deviceInfo = get<IDeviceInfo>()
        )
    }
    
    // 接口使用示例
    // single { InterfaceUsageExample() }
}
