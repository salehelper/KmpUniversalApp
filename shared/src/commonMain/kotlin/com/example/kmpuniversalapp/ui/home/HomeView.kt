package com.example.kmpuniversalapp.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
// 暂时注释图标导入，避免编译错误
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.Star
// import androidx.compose.material.icons.filled.Favorite
// import androidx.compose.material.icons.filled.Share
// import androidx.compose.material.icons.filled.Comment
// import androidx.compose.material.icons.filled.Visibility
// import androidx.compose.material.icons.filled.ThumbUp
// import androidx.compose.material.icons.filled.MoreVert
// import androidx.compose.material.icons.filled.PlayArrow
// import androidx.compose.material.icons.filled.ArrowForward
// import androidx.compose.material.icons.filled.Home
// import androidx.compose.material.icons.filled.Search
// import androidx.compose.material.icons.filled.Message
// import androidx.compose.material.icons.filled.Person
// import androidx.compose.material.icons.filled.ArrowBack
// import androidx.compose.material.icons.filled.Menu
// import androidx.compose.material.icons.filled.Notifications
// import androidx.compose.material.icons.filled.Settings
// import androidx.compose.material.icons.filled.Info
// import androidx.compose.material.icons.filled.Help
// import androidx.compose.material.icons.filled.ExitToApp
// import androidx.compose.material.icons.filled.Architecture
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpuniversalapp.home.BannerModel
import com.example.kmpuniversalapp.home.DynamicModel
import com.example.kmpuniversalapp.home.HomeViewModel
import kotlinx.coroutines.launch
// import org.jetbrains.compose.resources.painterResource

// 资源文件暂时注释，避免编译错误
// import kmpuniversalapp.composeapp.generated.resources.Res
// import kmpuniversalapp.composeapp.generated.resources.compose_multiplatform

// 暂时注释整个UI文件，避免编译错误
/*
@Composable
fun HomeView() {
    // 使用依赖注入获取ViewModel Factory
    val viewModelFactory = org.koin.compose.koinInject<com.example.kmpuniversalapp.di.ViewModelFactory>()
    val homeViewModel = remember { viewModelFactory.createHomeViewModel() }
    val banners by homeViewModel.banners.collectAsState()
    val dynamics by homeViewModel.dynamics.collectAsState()
    val statistics by homeViewModel.statistics.collectAsState()
    val currentBannerIndex by homeViewModel.currentBannerIndex.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val errorMessage by homeViewModel.errorMessage.collectAsState()
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        homeViewModel.loadHomeData()
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 顶部欢迎区域
        item {
            WelcomeSection()
        }
        
        // 统计数据卡片
        item {
            StatisticsCard(statistics = statistics)
        }
        
        // Banner轮播
        if (banners.isNotEmpty()) {
            item {
                BannerSection(
                    banners = banners,
                    currentIndex = currentBannerIndex,
                    onBannerClick = { homeViewModel.onBannerTap(it) },
                    onIndexChange = { homeViewModel.setBannerIndex(it) }
                )
            }
        }
        
        // 功能模块介绍
        item {
            FeatureIntroductionSection()
        }
        
        // 架构介绍
        item {
            ArchitectureIntroductionSection()
        }
        
        // 动态列表
        if (dynamics.isNotEmpty()) {
            item {
                Text(
                    text = "最新动态",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(dynamics) { dynamic ->
                DynamicItem(
                    dynamic = dynamic,
                    onClick = { homeViewModel.onDynamicTap(dynamic) }
                )
            }
        }
        
        // 加载状态
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        
        // 错误状态
        errorMessage?.let { error ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 暂时使用图标替代图片
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "KMP Universal App",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "基于Kotlin Multiplatform的跨平台应用",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun StatisticsCard(statistics: Map<String, Any>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "数据统计",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem("总用户", statistics["totalUsers"]?.toString() ?: "0")
                StatisticItem("总文章", statistics["totalPosts"]?.toString() ?: "0")
                StatisticItem("总浏览", statistics["totalViews"]?.toString() ?: "0")
                StatisticItem("在线", statistics["onlineUsers"]?.toString() ?: "0")
            }
        }
    }
}

@Composable
fun StatisticItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun BannerSection(
    banners: List<BannerModel>,
    currentIndex: Int,
    onBannerClick: (BannerModel) -> Unit,
    onIndexChange: (Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(currentIndex) {
        pagerState.animateScrollToPage(currentIndex)
    }
    
    Column {
        Text(
            text = "推荐内容",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(200.dp)
        ) { page ->
            val banner = banners[page]
            BannerItem(
                banner = banner,
                onClick = { onBannerClick(banner) }
            )
        }
        
        // 指示器
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(banners.size) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == currentIndex) 
                                MaterialTheme.colorScheme.primary 
                            else 
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                )
                if (index < banners.size - 1) {
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Composable
fun BannerItem(
    banner: BannerModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            // 这里应该显示图片，暂时用占位符
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun FeatureIntroductionSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "功能模块",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            val features = listOf(
                FeatureItem("首页", "数据统计、Banner轮播、动态列表", Icons.Filled.Home, Color.Blue),
                FeatureItem("搜索", "智能搜索、历史记录、热门搜索", Icons.Filled.Search, Color.Green),
                FeatureItem("消息", "消息管理、会话列表、通知提醒", Icons.Filled.Message, Color(0xFFFF9800)),
                FeatureItem("个人中心", "用户信息、设置配置、账户安全", Icons.Filled.Person, Color(0xFF9C27B0))
            )
            
            features.forEach { feature ->
                FeatureCard(feature = feature)
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun FeatureCard(feature: FeatureItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = feature.icon,
            contentDescription = null,
            tint = feature.color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = feature.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DynamicItem(
    dynamic: DynamicModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = dynamic.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (dynamic.isTop) {
                    Surface(
                        color = MaterialTheme.colorScheme.error,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "置顶",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = dynamic.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dynamic.viewCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = dynamic.likeCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Text(
                    text = dynamic.createdAt.take(10),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ArchitectureIntroductionSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // 标题
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Architecture,
                    contentDescription = null,
                    tint = Color.Blue,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "技术架构介绍",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue.copy(alpha = 0.8f)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // 架构特点
            val architectureFeatures = listOf(
                ArchitectureFeature(
                    "🏗️ 模块化设计",
                    "采用模块化架构，每个功能模块独立开发、测试和部署，提高代码复用性和维护性。"
                ),
                ArchitectureFeature(
                    "📱 MVVM模式",
                    "使用MVVM架构模式，实现数据、视图和业务逻辑的分离，提高代码的可测试性和可维护性。"
                ),
                ArchitectureFeature(
                    "🔄 响应式编程",
                    "基于StateFlow的响应式编程，实现数据驱动的UI更新，提供流畅的用户体验。"
                ),
                ArchitectureFeature(
                    "🔧 依赖注入",
                    "使用依赖注入系统，管理服务、控制器和页面之间的依赖关系，降低耦合度。"
                ),
                ArchitectureFeature(
                    "📦 代码生成",
                    "集成Kotlinx Serialization，自动生成JSON序列化代码，提高开发效率。"
                ),
                ArchitectureFeature(
                    "🎨 组件化UI",
                    "采用组件化设计，将复杂的UI拆分为可复用的组件，提高开发效率和代码质量。"
                )
            )
            
            architectureFeatures.forEach { feature ->
                _buildArchitectureFeature(feature)
                if (feature != architectureFeatures.last()) {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // 技术栈
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.7f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "技术栈",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.8f)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    val techStack = listOf(
                        "Kotlin Multiplatform", "Compose Multiplatform", "Ktor", 
                        "Kotlinx Serialization", "StateFlow", "MVVM", 
                        "模块化架构", "响应式编程"
                    )
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(techStack) { tech ->
                            Surface(
                                color = Color.Blue.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = tech,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Blue.copy(alpha = 0.8f),
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun _buildArchitectureFeature(feature: ArchitectureFeature) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color.Blue.copy(alpha = 0.6f)),
            contentAlignment = Alignment.Center
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W600,
                color = Color.Blue.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = feature.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )
        }
    }
}

data class FeatureItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

data class ArchitectureFeature(
    val title: String,
    val description: String
)
*/
