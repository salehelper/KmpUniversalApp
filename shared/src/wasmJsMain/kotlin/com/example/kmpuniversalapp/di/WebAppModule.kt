package com.example.kmpuniversalapp.di

import org.koin.dsl.module

/**
 * Web平台特定的Koin模块
 * 注册Web平台的ViewModel Factory
 */
val webAppModule = module {
    // 注册Web ViewModel Factory
    single<ViewModelFactory> { WebViewModelFactory }
}







