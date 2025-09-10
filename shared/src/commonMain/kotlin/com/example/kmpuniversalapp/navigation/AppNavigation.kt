package com.example.kmpuniversalapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import com.example.kmpuniversalapp.ui.MainTabView
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition

/**
 * 导航状态管理
 */
val LocalNavigator = staticCompositionLocalOf<Navigator> {
    error("Navigator not provided")
}

val LocalSnackbarState = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarState has not been initialized!")
}

/**
 * 主应用导航组件
 * 参考 Translation-KMP 的 AppNavigation 设计
 */
@Composable
fun AppNavigation(
    navigator: Navigator = rememberNavigator(),
    onExitApp: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val canGoBack by navigator.canGoBack.collectAsState(false)
    
    // 处理返回键
    BackHandler(enabled = true) {
        if (!canGoBack) {
            AppLogger.d("AppNavigation", "No more screens to go back, exiting app")
            onExitApp()
        } else {
            AppLogger.d("AppNavigation", "Going back to previous screen")
            navigator.popBackStack()
        }
    }
    
    CompositionLocalProvider(
        LocalNavigator provides navigator,
        LocalSnackbarState provides snackbarHostState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { scaffoldPadding ->
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = AppScreen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                // 主页面 - 使用底部导航
                scene(route = AppScreen.Home.route) {
                    MainTabView()
                }
                
                // 搜索页面
                scene(route = AppScreen.Search.route) {
                    // TODO: 实现搜索页面
                    MainTabView() // 临时使用主页面
                }
                
                // 消息页面
                scene(route = AppScreen.Message.route) {
                    // TODO: 实现消息页面
                    MainTabView() // 临时使用主页面
                }
                
                // 个人页面
                scene(route = AppScreen.Profile.route) {
                    // TODO: 实现个人页面
                    MainTabView() // 临时使用主页面
                }
                
                // 其他页面暂时使用主页面
                scene(route = AppScreen.Settings.route) {
                    MainTabView()
                }
                
                scene(route = AppScreen.Login.route) {
                    MainTabView()
                }
                
                scene(route = AppScreen.Register.route) {
                    MainTabView()
                }
            }
        }
    }
}

/**
 * 导航扩展函数
 */
fun Navigator.navigateTo(screen: AppScreen, params: Map<String, Any> = emptyMap()) {
    val route = if (params.isEmpty()) {
        screen.route
    } else {
        RouteParser.buildUrl(screen.route, params)
    }
    
    AppLogger.d("AppNavigation", "Navigating to: $route")
    navigate(route)
}
