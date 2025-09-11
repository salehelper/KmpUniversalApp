package com.example.kmpuniversalapp.features.message

import org.koin.dsl.module

/**
 * 消息模块的Koin配置
 */
val messageModule = module {
    single<MessageViewModel> { MessageViewModel(get(), get()) }
}
