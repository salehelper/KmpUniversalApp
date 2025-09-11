package com.example.kmpuniversalapp

import com.example.kmpuniversalapp.core.di.androidAppModule
import org.koin.core.module.Module

/**
 * Android平台特定的App实现
 */
actual fun getPlatformSpecificModules(): List<Module> {
    return listOf(androidAppModule)
}
