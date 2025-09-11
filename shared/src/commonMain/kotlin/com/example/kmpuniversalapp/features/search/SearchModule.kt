package com.example.kmpuniversalapp.features.search

import org.koin.dsl.module

/**
 * 搜索模块的Koin配置
 */
val searchModule = module {
    single<SearchApiService> { SearchApiService() }
    single<SearchViewModel> { SearchViewModel(get(), get(), get()) }
}
