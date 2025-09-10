package com.example.kmpuniversalapp

import com.example.kmpuniversalapp.di.webAppModule
import org.koin.core.module.Module

/**
 * Web平台特定的App实现
 * 使用Web模块配置
 */
actual fun getPlatformSpecificModules(): List<Module> {
    return listOf(webAppModule)
}
