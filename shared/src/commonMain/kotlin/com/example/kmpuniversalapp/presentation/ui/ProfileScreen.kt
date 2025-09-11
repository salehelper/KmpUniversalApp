package com.example.kmpuniversalapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmpuniversalapp.presentation.navigation.LocalNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val navigator = LocalNavigator.current
    var userProfile by remember { mutableStateOf<UserProfile?>(null) }
    var menuItems by remember { mutableStateOf(getProfileMenuItems()) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 标题栏
        TopAppBar(
            title = { Text("个人中心") },
            navigationIcon = {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
            }
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 用户信息卡片
            Card(
                modifier = Modifier.fillMaxWidth()
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
                        style = MaterialTheme.typography.headlineSmall
                    )
                    
                    // 邮箱
                    userProfile?.email?.let { email ->
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // 菜单项
            menuItems.forEach { menuItem ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /* 处理菜单项点击 */ }
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
        shape = MaterialTheme.shapes.extraLarge,
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

private fun getProfileMenuItems(): List<ProfileMenuItem> {
    return listOf(
        ProfileMenuItem("设置", Icons.Default.Settings),
        ProfileMenuItem("关于", Icons.Default.Info),
        ProfileMenuItem("帮助", Icons.AutoMirrored.Filled.Help),
        ProfileMenuItem("退出登录", Icons.AutoMirrored.Filled.ExitToApp)
    )
}