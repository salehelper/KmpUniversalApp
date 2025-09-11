package com.example.kmpuniversalapp.presentation.ui.home

// 恢复图标导入和UI组件
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Architecture
import org.jetbrains.compose.resources.painterResource

// 资源文件导入 - 暂时注释掉，使用图标替代
// import kmpuniversalapp.composeapp.generated.resources.Res
// import kmpuniversalapp.composeapp.generated.resources.compose_multiplatform

// 恢复UI组件
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.kmpuniversalapp.features.home.BannerModel
import com.example.kmpuniversalapp.features.home.DynamicModel
import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.model.News
import com.example.kmpuniversalapp.domain.model.Video
import com.example.kmpuniversalapp.domain.model.ImageCarousel
import com.example.kmpuniversalapp.domain.model.Priority
import com.example.kmpuniversalapp.domain.model.NewsCategory
import com.example.kmpuniversalapp.domain.model.VideoCategory
import com.example.kmpuniversalapp.domain.model.VideoQuality
import com.example.kmpuniversalapp.domain.model.CarouselCategory
import com.example.kmpuniversalapp.presentation.ui.components.ImageCarouselComponent
import com.example.kmpuniversalapp.presentation.ui.components.VideoPlayerComponent
import com.example.kmpuniversalapp.presentation.ui.components.TodoListComponent
import com.example.kmpuniversalapp.presentation.ui.components.NewsListComponent
import com.example.kmpuniversalapp.presentation.ui.tabs.HomeTabContent
@Composable
fun HomeView(modifier: Modifier = Modifier) {
    // 暂时使用模拟数据，避免依赖注入问题
    val banners = remember { 
        listOf(
            BannerModel("1", "🚀 欢迎使用KMP Universal App", "https://example.com/image1.jpg", "https://example.com", 0, true, "2024-01-01"),
            BannerModel("2", "✨ 功能丰富", "https://example.com/image2.jpg", "https://example.com", 1, true, "2024-01-02"),
            BannerModel("3", "⚡ 性能优异", "https://example.com/image3.jpg", "https://example.com", 2, true, "2024-01-03")
        )
    }
    val dynamics = remember {
        listOf(
            DynamicModel("1", "项目启动", "KMP Universal App项目正式启动，开始构建跨平台应用", "news", null, null, 100, 50, 0, true, "2024-01-01"),
            DynamicModel("2", "功能开发", "完成基础架构搭建，开始功能模块开发", "news", null, null, 80, 30, 0, false, "2024-01-02"),
            DynamicModel("3", "UI优化", "优化用户界面，提升用户体验", "news", null, null, 60, 20, 0, false, "2024-01-03")
        )
    }
    val statistics = remember {
        mapOf(
            "totalUsers" to 1000,
            "totalPosts" to 500,
            "totalViews" to 10000,
            "onlineUsers" to 50
        )
    }
    val currentBannerIndex = remember { mutableStateOf(0) }
    val isLoading = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    
    // 新增数据：待办事项
    val todos = remember {
        listOf(
            Todo(
                id = "1",
                title = "完成KMP项目架构设计",
                description = "按照DDD原则设计项目架构，包括领域模型、用例和仓储",
                isCompleted = false,
                priority = Priority.HIGH,
                category = "工作",
                createdAt = "2024-01-01T10:00:00Z",
                updatedAt = "2024-01-01T10:00:00Z",
                tags = listOf("KMP", "架构", "DDD")
            ),
            Todo(
                id = "2",
                title = "学习Compose Multiplatform",
                description = "深入学习Compose Multiplatform的UI开发技术",
                isCompleted = true,
                priority = Priority.MEDIUM,
                category = "学习",
                createdAt = "2024-01-02T09:00:00Z",
                updatedAt = "2024-01-02T15:30:00Z",
                tags = listOf("Compose", "学习", "UI")
            ),
            Todo(
                id = "3",
                title = "优化应用性能",
                description = "分析并优化应用的性能瓶颈",
                isCompleted = false,
                priority = Priority.URGENT,
                dueDate = "2024-01-15",
                category = "优化",
                createdAt = "2024-01-03T14:00:00Z",
                updatedAt = "2024-01-03T14:00:00Z",
                tags = listOf("性能", "优化", "分析")
            )
        )
    }
    
    // 新增数据：资讯
    val news = remember {
        listOf(
            News(
                id = "1",
                title = "Kotlin Multiplatform 1.9.0 正式发布",
                content = "Kotlin Multiplatform 1.9.0 带来了许多新特性和改进...",
                summary = "Kotlin Multiplatform 1.9.0 正式发布，带来性能优化和新特性",
                author = "JetBrains团队",
                category = NewsCategory.TECHNOLOGY,
                tags = listOf("Kotlin", "Multiplatform", "Compose"),
                imageUrl = "https://example.com/kmp-1.9.0.jpg",
                source = "JetBrains官方博客",
                publishedAt = "2024-01-01T10:00:00Z",
                updatedAt = "2024-01-01T10:00:00Z",
                viewCount = 1250,
                likeCount = 89,
                commentCount = 23,
                isTop = true,
                isHot = true,
                isRecommended = true
            ),
            News(
                id = "2",
                title = "Compose Multiplatform 1.7.0 新功能详解",
                content = "Compose Multiplatform 1.7.0 引入了许多令人兴奋的新功能...",
                summary = "Compose Multiplatform 1.7.0 带来新功能和改进",
                author = "Compose团队",
                category = NewsCategory.TECHNOLOGY,
                tags = listOf("Compose", "Multiplatform", "UI"),
                imageUrl = "https://example.com/compose-1.7.0.jpg",
                source = "Compose官方文档",
                publishedAt = "2024-01-02T14:30:00Z",
                updatedAt = "2024-01-02T14:30:00Z",
                viewCount = 890,
                likeCount = 67,
                commentCount = 15,
                isTop = false,
                isHot = true,
                isRecommended = true
            )
        )
    }
    
    // 新增数据：视频
    val videos = remember {
        listOf(
            Video(
                id = "1",
                title = "Kotlin Multiplatform 入门教程",
                description = "从零开始学习Kotlin Multiplatform，了解跨平台开发的基本概念和实践",
                thumbnailUrl = "https://example.com/kmp-tutorial-thumb.jpg",
                videoUrl = "https://example.com/kmp-tutorial.mp4",
                duration = 1800,
                category = VideoCategory.EDUCATION,
                tags = listOf("Kotlin", "Multiplatform", "教程"),
                author = "技术讲师",
                viewCount = 2500,
                likeCount = 156,
                commentCount = 34,
                isLive = false,
                isRecommended = true,
                publishedAt = "2024-01-01T10:00:00Z",
                quality = VideoQuality.HD
            ),
            Video(
                id = "2",
                title = "Compose Multiplatform UI设计实战",
                description = "深入学习Compose Multiplatform的UI设计技巧和最佳实践",
                thumbnailUrl = "https://example.com/compose-ui-thumb.jpg",
                videoUrl = "https://example.com/compose-ui.mp4",
                duration = 2400,
                category = VideoCategory.TUTORIAL,
                tags = listOf("Compose", "UI", "设计"),
                author = "UI设计师",
                viewCount = 1800,
                likeCount = 98,
                commentCount = 22,
                isLive = false,
                isRecommended = true,
                publishedAt = "2024-01-02T14:30:00Z",
                quality = VideoQuality.FHD
            )
        )
    }
    
    // 新增数据：轮播图
    val carousels = remember {
        listOf(
            ImageCarousel(
                id = "1",
                title = "KMP Universal App 正式发布",
                description = "基于Kotlin Multiplatform的跨平台应用正式发布",
                imageUrl = "https://example.com/carousel-1.jpg",
                linkUrl = "https://example.com/app-release",
                category = CarouselCategory.PROMOTION,
                sortOrder = 1,
                isActive = true,
                startDate = "2024-01-01",
                endDate = "2024-01-31",
                clickCount = 1250,
                createdAt = "2024-01-01T10:00:00Z"
            ),
            ImageCarousel(
                id = "2",
                title = "新功能上线：智能推荐",
                description = "基于AI的智能推荐功能正式上线",
                imageUrl = "https://example.com/carousel-2.jpg",
                linkUrl = "https://example.com/ai-feature",
                category = CarouselCategory.PRODUCT,
                sortOrder = 2,
                isActive = true,
                startDate = "2024-01-02",
                endDate = "2024-01-31",
                clickCount = 890,
                createdAt = "2024-01-02T14:30:00Z"
            )
        )
    }
    
    // 调试信息
    LaunchedEffect(Unit) {
        println("HomeView - todos: ${todos.size}")
        println("HomeView - news: ${news.size}")
        println("HomeView - videos: ${videos.size}")
        println("HomeView - carousels: ${carousels.size}")
    }
    
    // 直接显示首页内容，不包含底部导航栏
    // 底部导航栏由MainTabView.kt处理
    HomeTabContent(
        modifier = modifier,
        todos = todos,
        news = news,
        videos = videos,
        carousels = carousels,
        onTodoClick = { todo ->
            println("点击待办事项: ${todo.title}")
        },
        onTodoToggle = { todo ->
            println("切换待办事项状态: ${todo.title}")
        },
        onAddTodo = {
            println("添加新待办事项")
        },
        onNewsClick = { newsItem ->
            println("点击资讯: ${newsItem.title}")
        },
        onNewsLike = { newsItem ->
            println("点赞资讯: ${newsItem.title}")
        },
        onVideoClick = { video ->
            println("点击视频: ${video.title}")
        },
        onVideoLike = { video ->
            println("点赞视频: ${video.title}")
        },
        onCarouselClick = { carousel ->
            println("点击轮播图: ${carousel.title}")
        }
    )
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
        if (currentIndex < banners.size) {
        pagerState.animateScrollToPage(currentIndex)
        }
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
                FeatureItem("消息", "消息管理、会话列表、通知提醒", Icons.AutoMirrored.Filled.Chat, Color(0xFFFF9800)),
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

data class FeatureItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)

