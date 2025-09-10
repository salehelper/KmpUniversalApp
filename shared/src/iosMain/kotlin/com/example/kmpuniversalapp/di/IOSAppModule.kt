package com.example.kmpuniversalapp.di

import org.koin.dsl.module

/**
 * iOS平台特定的Koin模块
 * 注册iOS平台的ViewModel Factory
 */
val iosAppModule = module {
    // 注册iOS ViewModel Factory
    single<ViewModelFactory> { IOSViewModelFactory }
}
