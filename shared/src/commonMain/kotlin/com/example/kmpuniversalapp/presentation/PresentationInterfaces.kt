/**
 * 表现层模块接口定义
 * 遵循接口隔离原则，定义表现层模块的公共接口
 */
package com.example.kmpuniversalapp.presentation

import androidx.compose.runtime.State

/**
 * 导航接口
 * 隔离导航实现，支持不同导航库
 */
interface INavigation {
    fun navigateTo(route: String, params: Map<String, Any> = emptyMap())
    fun navigateBack()
    fun canNavigateBack(): Boolean
    fun clearBackStack()
}

/**
 * 状态管理接口
 * 隔离状态管理，支持不同状态管理方案
 */
interface IStateManager<T> {
    val state: State<T>
    fun updateState(updater: (T) -> T)
    fun resetState()
}

/**
 * 主题管理接口
 * 隔离主题管理，支持不同主题系统
 */
interface IThemeManager {
    fun isDarkMode(): Boolean
    fun toggleTheme()
    fun setTheme(isDark: Boolean)
    fun getCurrentTheme(): ThemeType
}

/**
 * 对话框管理接口
 * 隔离对话框管理，支持不同对话框系统
 */
interface IDialogManager {
    fun showDialog(
        title: String,
        message: String,
        positiveText: String = "确定",
        negativeText: String? = null,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null
    )
    fun showLoadingDialog(message: String = "加载中...")
    fun hideLoadingDialog()
    fun showToast(message: String, duration: ToastDuration = ToastDuration.SHORT)
}

/**
 * 权限管理接口
 * 隔离权限管理，支持不同平台权限系统
 */
interface IPermissionManager {
    suspend fun requestPermission(permission: String): Boolean
    suspend fun requestPermissions(permissions: List<String>): Map<String, Boolean>
    fun hasPermission(permission: String): Boolean
    fun shouldShowRationale(permission: String): Boolean
}

/**
 * 生命周期管理接口
 * 隔离生命周期管理，支持不同平台
 */
interface ILifecycleManager {
    fun addObserver(observer: LifecycleObserver)
    fun removeObserver(observer: LifecycleObserver)
    fun isActive(): Boolean
}

enum class ThemeType {
    LIGHT, DARK, SYSTEM
}

enum class ToastDuration {
    SHORT, LONG
}

interface LifecycleObserver {
    fun onStart()
    fun onStop()
    fun onResume()
    fun onPause()
}
