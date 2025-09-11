package com.example.kmpuniversalapp.features.profile

import org.koin.dsl.module

/**
 * 个人中心模块的Koin配置
 */
val profileModule = module {
    single<ProfileViewModel> { ProfileViewModel(get(), get()) }
}
