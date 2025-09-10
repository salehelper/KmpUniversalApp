package com.example.kmpuniversalapp.main

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 主页面标签控制器
 * 管理底部导航栏的切换和页面状态
 */
class MainTabController {
    // 当前选中的标签索引
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()
    
    // 标签页配置
    val tabs = listOf(
        TabItem(0, "首页", "home"),
        TabItem(1, "搜索", "search"),
        TabItem(2, "消息", "message"),
        TabItem(3, "我的", "profile")
    )
    
    val tabCount: Int
        get() = tabs.size
    
    /**
     * 切换到指定标签页
     */
    fun switchToTab(index: Int) {
        if (index >= 0 && index < tabCount) {
            _currentIndex.value = index
        }
    }
    
    /**
     * 获取当前标签页
     */
    fun getCurrentTab(): TabItem? {
        return tabs.getOrNull(_currentIndex.value)
    }
    
    /**
     * 检查是否为指定标签页
     */
    fun isCurrentTab(index: Int): Boolean {
        return _currentIndex.value == index
    }
}

data class TabItem(
    val index: Int,
    val title: String,
    val route: String
)
