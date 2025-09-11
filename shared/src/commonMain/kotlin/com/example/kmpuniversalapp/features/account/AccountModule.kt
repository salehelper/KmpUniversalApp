package com.example.kmpuniversalapp.features.account

import org.koin.dsl.module

/**
 * 账户模块的Koin配置
 */
val accountModule = module {
    single<AccountViewModel> { AccountViewModel(get(), get()) }
}
