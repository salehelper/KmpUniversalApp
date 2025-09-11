package com.example.kmpuniversalapp.features.home

import org.koin.dsl.module

/**
 * 首页模块的Koin配置
 */
val homeModule = module {
    single<HomeApiService> { HomeApiService() }
    single<HomeViewModel> { HomeViewModel(get(), get(), get()) }
}
