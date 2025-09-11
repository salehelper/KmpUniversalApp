package com.example.kmpuniversalapp.presentation.ui.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * 个人中心Tab内容
 */
@Composable
fun ProfileTabContent(
    modifier: Modifier = Modifier
) {
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var menuItems by remember { mutableStateOf(getProfileMenuItems()) }
    
    // 模拟用户数据
    LaunchedEffect(Unit) {
        userProfile = UserProfile(
            id = "1",
            name = "开发者",
            email = "developer@example.com",
            avatar = null
        )
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 用户信息卡片
        UserProfileCard(userProfile = userProfile)
        
        // 菜单项
        menuItems.forEach { menuItem ->
            ProfileMenuItem(
                menuItem = menuItem,
                onClick = { /* 处理菜单项点击 */ }
            )
        }
    }
}

@Composable
private fun UserProfileCard(userProfile: UserProfile?) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 头像
            CircleAvatar(
                text = userProfile?.name?.take(1) ?: "U",
                modifier = Modifier.size(80.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 用户名
            Text(
                text = userProfile?.name ?: "未登录",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            // 邮箱
            userProfile?.email?.let { email ->
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 统计信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem("项目", "12")
                StatisticItem("关注", "156")
                StatisticItem("粉丝", "89")
            }
        }
    }
}

@Composable
private fun StatisticItem(label: String, value: String) {
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
private fun ProfileMenuItem(
    menuItem: ProfileMenuItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.title
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            
            if (menuItem.badge != null) {
                Badge {
                    Text(menuItem.badge)
                }
            }
        }
    }
}

@Composable
private fun CircleAvatar(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

private fun getProfileMenuItems(): List<ProfileMenuItem> {
    return listOf(
        ProfileMenuItem("设置", Icons.Filled.Settings),
        ProfileMenuItem("关于", Icons.Filled.Info),
        ProfileMenuItem("帮助", Icons.AutoMirrored.Filled.Help),
        ProfileMenuItem("退出登录", Icons.AutoMirrored.Filled.ExitToApp)
    )
}

data class UserProfile(
    val id: String,
    val name: String,
    val email: String?,
    val avatar: String?
)

data class ProfileMenuItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val badge: String? = null
)
