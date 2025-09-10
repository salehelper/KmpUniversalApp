package com.example.kmpuniversalapp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.kmpuniversalapp.di.KoinInitializer
import com.example.kmpuniversalapp.navigation.AppNavigation
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.module.Module

@Composable
@Preview
fun App() {
    // 添加测试日志
    LaunchedEffect(Unit) {
        AppLogger.w("App", "应用启动中...")
        AppLogger.w("App", "开始初始化依赖注入")
    }
    
    // 初始化依赖注入
    LaunchedEffect(Unit) {
        KoinInitializer.init(getPlatformSpecificModules())
        AppLogger.w("App", "依赖注入初始化完成")
    }
    
    PreComposeApp {
        MaterialTheme {
            AppNavigation(
                navigator = rememberNavigator()
            )
        }
    }
}

/**
 * 获取平台特定的Koin模块
 */
expect fun getPlatformSpecificModules(): List<Module>