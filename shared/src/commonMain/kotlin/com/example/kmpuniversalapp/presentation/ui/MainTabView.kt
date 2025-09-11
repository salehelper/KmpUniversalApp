package com.example.kmpuniversalapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.kmpuniversalapp.core.DI
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.presentation.ui.home.HomeView
import com.example.kmpuniversalapp.presentation.ui.components.BottomTabNavigation
import com.example.kmpuniversalapp.presentation.ui.components.HomeTab
import com.example.kmpuniversalapp.presentation.ui.tabs.HomeTabContent
import com.example.kmpuniversalapp.presentation.ui.tabs.SearchTabContent
import com.example.kmpuniversalapp.presentation.ui.tabs.MessageTabContent
import com.example.kmpuniversalapp.presentation.ui.tabs.ProfileTabContent

private val logger: ILogger = DI.getLogger()

@Composable
fun MainTabView() {
    // Tab状态管理
    var selectedTab by remember { mutableStateOf(HomeTab.HOME) }
    
    // 页面进入日志
    LaunchedEffect(Unit) {
        logger.i("MainTabView", "主Tab页面已加载")
    }
    
    Scaffold(
        bottomBar = {
            BottomTabNavigation(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                HomeTab.HOME -> {
                    HomeView()
                }
                HomeTab.SEARCH -> {
                    SearchTabContent()
                }
                HomeTab.MESSAGE -> {
                    MessageTabContent()
                }
                HomeTab.PROFILE -> {
                    ProfileTabContent()
                }
            }
        }
    }
}
