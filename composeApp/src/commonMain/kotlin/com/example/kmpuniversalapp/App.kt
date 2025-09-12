package com.example.kmpuniversalapp

import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.kmpuniversalapp.core.di.KoinInitializer
import com.example.kmpuniversalapp.presentation.navigation.AppNavigation
import com.example.kmpuniversalapp.presentation.ui.components.Material3Example
import com.example.kmpuniversalapp.core.utils.log.AppLogger
import com.example.kmpuniversalapp.core.*
import com.example.kmpuniversalapp.core.services.AppService
// import com.example.kmpuniversalapp.core.examples.InterfaceUsageExample
// 移除PreCompose依赖
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.module.Module

@Composable
@Preview
fun App() {
    var showKuiklyUIExample by remember { mutableStateOf(false) }
    
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
    
    // 应用启动时执行示例
    LaunchedEffect(Unit) {
        try {
            // 使用DI获取服务
            val logger = DI.getLogger()
            val storage = DI.getStorage()
            val networkClient = DI.getNetworkClient()
            val timeProvider = DI.getTimeProvider()
            val deviceInfo = DI.getDeviceInfo()
            
            // 创建应用服务
            val appService = AppService(logger, storage, networkClient, timeProvider, deviceInfo)
            
            // 初始化应用服务
            appService.initialize()
            
                   // 创建接口使用示例
                   // val interfaceExample = InterfaceUsageExample()

                   // 执行接口使用示例
                   // interfaceExample.exampleFeatureUsage()
            
            // 记录应用统计信息
            val stats = appService.getAppStats()
            logger.i("App", "应用统计信息: $stats")
            
        } catch (e: Exception) {
            AppLogger.e("App", "应用初始化过程中发生错误", e)
        }
    }
    
    MaterialTheme {
        if (showKuiklyUIExample) {
                Material3Example()
        } else {
            AppNavigation()
        }
    }
}

/**
 * 获取平台特定的Koin模块
 */
expect fun getPlatformSpecificModules(): List<Module>