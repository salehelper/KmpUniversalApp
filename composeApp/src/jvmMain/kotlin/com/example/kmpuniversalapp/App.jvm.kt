package com.example.kmpuniversalapp

import com.example.kmpuniversalapp.di.jvmAppModule
import org.koin.core.module.Module

/**
 * JVM平台特定的App实现
 * 使用JVM模块配置
 */
actual fun getPlatformSpecificModules(): List<Module> {
    return listOf(jvmAppModule)
}
