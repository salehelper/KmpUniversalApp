package com.example.kmpuniversalapp.di

import org.koin.dsl.module

/**
 * JVM平台特定的Koin模块
 * 注册JVM平台的ViewModel Factory
 */
val jvmAppModule = module {
    // 注册JVM ViewModel Factory
    single<ViewModelFactory> { JvmViewModelFactory }
}
