package com.example.kmpuniversalapp.ui.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
// 暂时注释图标导入，避免编译错误
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.ArrowBack
// import androidx.compose.material.icons.filled.Menu
// import androidx.compose.material.icons.filled.Notifications
// import androidx.compose.material.icons.filled.Settings
// import androidx.compose.material.icons.filled.Search
// import androidx.compose.material.icons.filled.FilterList
// import androidx.compose.material.icons.filled.MoreVert
// import androidx.compose.material.icons.filled.Mail
// import androidx.compose.material.icons.filled.Reply
// import androidx.compose.material.icons.filled.ThumbUp
// import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

// 暂时注释整个UI文件，避免编译错误
/*
@Composable
fun MessageView() {
    // 使用依赖注入获取ViewModel Factory
    val viewModelFactory = org.koin.compose.koinInject<com.example.kmpuniversalapp.di.ViewModelFactory>()
    val messageViewModel = remember { viewModelFactory.createMessageViewModel() }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 顶部标题
        item {
            Text(
                text = "消息",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 功能开发中提示
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Filled.Message,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "消息功能开发中",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "敬请期待...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }
        
        // 功能预览
        item {
            Text(
                text = "功能预览",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 消息类型列表
        val messageTypes = listOf(
            MessageType("系统通知", "系统消息和重要通知", Icons.Filled.Notifications, Color.Blue),
            MessageType("私信", "用户之间的私信交流", Icons.Filled.Mail, Color.Green),
            MessageType("评论回复", "文章和动态的评论回复", Icons.Filled.Reply, Color(0xFFFF9800)),
            MessageType("点赞提醒", "收到点赞和收藏的提醒", Icons.Filled.ThumbUp, Color.Red),
            MessageType("关注动态", "关注用户的最新动态", Icons.Filled.PersonAdd, Color(0xFF9C27B0))
        )
        
        items(messageTypes) { messageType ->
            MessageTypeCard(messageType = messageType)
        }
    }
}

@Composable
fun MessageTypeCard(messageType: MessageType) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 图标
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(messageType.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = messageType.icon,
                    contentDescription = null,
                    tint = messageType.color,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 内容
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = messageType.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = messageType.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // 状态指示器
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )
        }
    }
}

data class MessageType(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)
*/
