/*
 * 简化的跨平台导航工具类
 * 使用简单的状态管理，避免复杂的导航库依赖
 */

package com.example.kmpuniversalapp.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * 简化的导航状态
 * 使用简单的状态管理
 */
class SimpleNavigationState {
    private var _currentRoute by mutableStateOf("home")
    
    val currentRoute: String get() = _currentRoute
    
    fun navigateTo(route: String) {
        _currentRoute = route
    }
    
    fun canGoBack(): Boolean = _currentRoute != "home"
    
    fun goBack() {
        if (canGoBack()) {
            _currentRoute = "home"
        }
    }
}

/**
 * 简化的导航工具类
 * 使用简单的状态管理，避免复杂的导航库
 */
object NavigationUtils {
    @Composable
    fun rememberNavigationState(): SimpleNavigationState {
        return SimpleNavigationState()
    }
    
    @Composable
    fun NavigationContainer(
        modifier: Modifier = Modifier,
        navigationState: SimpleNavigationState,
        content: @Composable () -> Unit
    ) {
        // 简单的导航容器实现
        content()
    }
}
