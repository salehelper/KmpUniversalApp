package com.example.kmpuniversalapp.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
// 暂时使用简单的文本替代图标，避免依赖问题
// import org.jetbrains.compose.material.icons.Icons
// import org.jetbrains.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
// import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpuniversalapp.navigation.AppScreen
import com.example.kmpuniversalapp.navigation.LocalNavigator
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import moe.tlaster.precompose.navigation.Navigator
// 暂时使用简单的占位符组件
@Composable
fun HomeView() {
    // 页面进入日志
    LaunchedEffect(Unit) {
        AppLogger.i("HomeView", "首页页面已加载")
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("首页 - 开发中")
    }
}

@Composable
fun SearchView() {
    // 页面进入日志
    LaunchedEffect(Unit) {
        AppLogger.i("SearchView", "搜索页面已加载")
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("搜索 - 开发中")
    }
}

@Composable
fun MessageView() {
    // 页面进入日志
    LaunchedEffect(Unit) {
        AppLogger.i("MessageView", "消息页面已加载")
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("消息 - 开发中")
    }
}

@Composable
fun ProfileView() {
    // 页面进入日志
    LaunchedEffect(Unit) {
        AppLogger.i("ProfileView", "个人资料页面已加载")
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("个人资料 - 开发中")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTabView() {
    val pagerState = rememberPagerState(pageCount = { 4 })
    var selectedTab by remember { mutableIntStateOf(0) }
    var isLogPanelVisible by remember { mutableStateOf(false) }
    var logPanelHeight by remember { mutableStateOf(300.dp) }
    val navigator = LocalNavigator.current
    
        val tabs = listOf(
            TabItem(0, "首页", "🏠", "🏠"),
            TabItem(1, "搜索", "🔍", "🔍"),
            TabItem(2, "消息", "💬", "💬"),
            TabItem(3, "我的", "👤", "👤")
        )
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            bottomBar = {
                _buildBottomNavigationBar(tabs, selectedTab) { index ->
                    // 记录tab点击日志
                    val tabName = tabs[index].title
                    AppLogger.userAction("点击Tab", "切换到: $tabName")
                    AppLogger.d("MainTabView", "Tab点击: $tabName (索引: $index)")
                    
                    selectedTab = index
                    
                    // 使用导航系统跳转到对应页面
                    when (index) {
                        0 -> {
                            AppLogger.d("MainTabView", "导航到首页")
                            navigator.navigate(AppScreen.Home.route)
                        }
                        1 -> {
                            AppLogger.d("MainTabView", "导航到搜索页")
                            navigator.navigate(AppScreen.Search.route)
                        }
                        2 -> {
                            AppLogger.d("MainTabView", "导航到消息页")
                            navigator.navigate(AppScreen.Message.route)
                        }
                        3 -> {
                            AppLogger.d("MainTabView", "导航到个人资料页")
                            navigator.navigate(AppScreen.Profile.route)
                        }
                    }
                }
            },
            floatingActionButton = {
                _buildLogFloatingButton(
                    isVisible = isLogPanelVisible,
                    onToggle = { 
                        isLogPanelVisible = !isLogPanelVisible
                        AppLogger.userAction("切换日志面板", "状态: ${if (isLogPanelVisible) "关闭" else "打开"}")
                        AppLogger.d("MainTabView", "日志面板切换: ${if (isLogPanelVisible) "关闭" else "打开"}")
                    }
                )
            },
                // 移除不支持的 floatingActionButtonLocation 参数
        ) { paddingValues ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) { page ->
                when (page) {
                    0 -> HomeView()
                    1 -> SearchView()
                    2 -> MessageView()
                    3 -> ProfileView()
                }
            }
            
            // 同步Pager和Tab状态
            LaunchedEffect(selectedTab) {
                AppLogger.d("MainTabView", "Pager滚动到页面: $selectedTab")
                pagerState.animateScrollToPage(selectedTab)
            }
            
            LaunchedEffect(pagerState.currentPage) {
                val pageName = tabs[pagerState.currentPage].title
                AppLogger.d("MainTabView", "Pager页面变化: $pageName (索引: ${pagerState.currentPage})")
                selectedTab = pagerState.currentPage
            }
        }
        
        // 日志面板 - 从底部滑出
        AnimatedVisibility(
            visible = isLogPanelVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            LogPanel(
                isVisible = isLogPanelVisible,
                onClose = { isLogPanelVisible = false },
                onResize = { newHeight -> logPanelHeight = newHeight },
                height = logPanelHeight
            )
        }
    }
}

@Composable
fun _buildBottomNavigationBar(
    tabs: List<TabItem>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White
        ) {
            tabs.forEach { tab ->
                NavigationBarItem(
                    icon = { 
                        Text(
                            text = if (selectedTab == tab.index) tab.activeIcon else tab.icon,
                            fontSize = 20.sp
                        )
                    },
                    label = { 
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selectedTab == tab.index) FontWeight.W600 else FontWeight.Normal
                        )
                    },
                    selected = selectedTab == tab.index,
                    onClick = { onTabSelected(tab.index) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }
    }
}

@Composable
fun _buildLogFloatingButton(
    isVisible: Boolean,
    onToggle: () -> Unit
) {
        FloatingActionButton(
            onClick = onToggle,
            modifier = Modifier.size(56.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ) {
        Text(
            text = "🐛",
            fontSize = 20.sp
        )
    }
}

@Composable
fun LogPanel(
    isVisible: Boolean,
    onClose: () -> Unit,
    onResize: (androidx.compose.ui.unit.Dp) -> Unit,
    height: androidx.compose.ui.unit.Dp
) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 拖拽手柄
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "日志面板",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Text(
                            text = "❌",
                            fontSize = 16.sp
                        )
                    }
                }
                
                // 拖拽指示器
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.Gray.copy(alpha = 0.5f))
                )
            }
            
            Divider()
            
            // 日志内容
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                        Text(
                            text = "🐛",
                            fontSize = 48.sp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "日志功能开发中",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "敬请期待...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

data class TabItem(
    val index: Int,
    val title: String,
    val icon: String,
    val activeIcon: String
)