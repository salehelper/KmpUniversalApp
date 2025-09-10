package com.example.kmpuniversalapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.ComposeUIViewController
import com.example.kmpuniversalapp.di.KoinInitializer
import com.example.kmpuniversalapp.di.webAppModule
import org.koin.core.module.Module

/**
 * Web平台入口点
 */
fun main() {
    // 初始化Koin
    KoinInitializer.init(listOf(webAppModule))
    
    // 启动Compose应用
    ComposeUIViewController { App() }
}

/**
 * Web平台特定的App实现
 * 使用Web模块配置
 */
actual fun getPlatformSpecificModules(): List<Module> {
    return listOf(webAppModule)
}
