package com.example.kmpuniversalapp.presentation.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kmpuniversalapp.domain.model.Todo
import com.example.kmpuniversalapp.domain.model.News
import com.example.kmpuniversalapp.domain.model.Video
import com.example.kmpuniversalapp.domain.model.ImageCarousel
import com.example.kmpuniversalapp.presentation.ui.components.ImageCarouselComponent
import com.example.kmpuniversalapp.presentation.ui.components.VideoPlayerComponent
import com.example.kmpuniversalapp.presentation.ui.components.TodoListComponent
import com.example.kmpuniversalapp.presentation.ui.components.NewsListComponent
import com.example.kmpuniversalapp.presentation.ui.components.TestComponent

/**
 * 首页Tab内容
 * 包含所有主要功能模块
 */
@Composable
fun HomeTabContent(
    todos: List<Todo>,
    news: List<News>,
    videos: List<Video>,
    carousels: List<ImageCarousel>,
    onTodoClick: (Todo) -> Unit,
    onTodoToggle: (Todo) -> Unit,
    onAddTodo: () -> Unit,
    onNewsClick: (News) -> Unit,
    onNewsLike: (News) -> Unit,
    onVideoClick: (Video) -> Unit,
    onVideoLike: (Video) -> Unit,
    onCarouselClick: (ImageCarousel) -> Unit,
    modifier: Modifier = Modifier
) {
    // 调试信息
    LaunchedEffect(Unit) {
        println("HomeTabContent - todos: ${todos.size}")
        println("HomeTabContent - news: ${news.size}")
        println("HomeTabContent - videos: ${videos.size}")
        println("HomeTabContent - carousels: ${carousels.size}")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 欢迎区域
        WelcomeCard()
        
        // 测试组件
        TestComponent(
            title = "测试组件",
            content = "这是一个测试组件，用于验证显示是否正常"
        )
        
        // 调试信息卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "调试信息",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("待办事项: ${todos.size} 个")
                Text("资讯: ${news.size} 条")
                Text("视频: ${videos.size} 个")
                Text("轮播图: ${carousels.size} 个")
            }
        }
        
        // 图片轮播 - 限制最多显示5个
        if (carousels.isNotEmpty()) {
            ImageCarouselComponent(
                carousels = carousels.take(5),
                onCarouselClick = onCarouselClick
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "轮播图数据为空",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // 视频播放 - 限制最多显示5个
        if (videos.isNotEmpty()) {
            VideoPlayerComponent(
                videos = videos.take(5),
                onVideoClick = onVideoClick,
                onVideoLike = onVideoLike
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "视频数据为空",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        
        // 待办事项 - 限制最多显示5条
        TodoListComponent(
            todos = todos.take(5),
            onTodoClick = onTodoClick,
            onTodoToggle = onTodoToggle,
            onAddTodo = onAddTodo
        )
        
        // 资讯列表 - 限制最多显示5条
        if (news.isNotEmpty()) {
            NewsListComponent(
                news = news.take(5),
                onNewsClick = onNewsClick,
                onNewsLike = onNewsLike
            )
        } else {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "资讯数据为空",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun WelcomeCard() {
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
            // 图标
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = androidx.compose.foundation.shape.CircleShape,
                modifier = Modifier.size(60.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏠",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "欢迎回来！",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "查看最新的内容和更新",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }
    }
}
