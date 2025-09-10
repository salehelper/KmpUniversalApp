package com.example.kmpuniversalapp.di

import org.koin.dsl.module

/**
 * Android平台特定的Koin模块
 * 注册Android平台的ViewModel Factory
 */
val androidAppModule = module {
    // 注册Android ViewModel Factory
    single<ViewModelFactory> { AndroidViewModelFactory }
}
