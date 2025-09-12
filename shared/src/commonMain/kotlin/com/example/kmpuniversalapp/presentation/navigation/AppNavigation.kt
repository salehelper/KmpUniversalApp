package com.example.kmpuniversalapp.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.example.kmpuniversalapp.core.utils.log.AppLogger
import com.example.kmpuniversalapp.core.utils.NavigationUtils
import com.example.kmpuniversalapp.presentation.ui.MainTabView
import com.example.kmpuniversalapp.presentation.ui.SearchScreen
import com.example.kmpuniversalapp.presentation.ui.MessageScreen
import com.example.kmpuniversalapp.presentation.ui.ProfileScreen

/**
 * 简化的导航状态管理
 */
val LocalSnackbarState = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarState has not been initialized!")
}

/**
 * 简化的主应用导航组件
 * 使用简单的状态管理，避免复杂的导航库
 */
@Composable
fun AppNavigation(
    onExitApp: () -> Unit = {}
) {
    val navigationState = NavigationUtils.rememberNavigationState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    CompositionLocalProvider(
        LocalSnackbarState provides snackbarHostState
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { scaffoldPadding ->
            // 简单的路由切换
            when (navigationState.currentRoute) {
                AppScreen.Home.route -> MainTabView()
                AppScreen.Search.route -> SearchScreen()
                AppScreen.Message.route -> MessageScreen()
                AppScreen.Profile.route -> ProfileScreen()
                AppScreen.Settings.route -> MainTabView()
                AppScreen.Login.route -> MainTabView()
                AppScreen.Register.route -> MainTabView()
                else -> MainTabView()
            }
        }
    }
}

/**
 * 简化的导航扩展函数
 */
fun navigateTo(screen: AppScreen, params: Map<String, Any> = emptyMap()) {
    val route = if (params.isEmpty()) {
        screen.route
    } else {
        RouteParser.buildUrl(screen.route, params)
    }
    
    AppLogger.d("AppNavigation", "Navigating to: $route")
    // 这里需要从某个地方获取navigationState实例
    // 暂时简化实现
}
