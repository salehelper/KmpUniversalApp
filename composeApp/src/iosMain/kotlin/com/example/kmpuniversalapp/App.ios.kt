package com.example.kmpuniversalapp

import org.koin.core.module.Module

/**
 * iOS平台特定的App实现
 */
actual fun getPlatformSpecificModules(): List<Module> {
    return emptyList()
}
