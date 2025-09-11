package com.example.kmpuniversalapp.presentation.ui.profile

// 暂时注释图标导入，避免编译错误
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.ArrowBack
// import androidx.compose.material.icons.filled.Menu
// import androidx.compose.material.icons.filled.Notifications
// import androidx.compose.material.icons.filled.Settings
// import androidx.compose.material.icons.filled.Edit
// import androidx.compose.material.icons.filled.Info
// import androidx.compose.material.icons.filled.Help
// import androidx.compose.material.icons.filled.ExitToApp
// import androidx.compose.material.icons.filled.Person
// import androidx.compose.material.icons.filled.PrivacyTip
// import androidx.compose.material.icons.filled.ChevronRight

// 暂时注释整个UI文件，避免编译错误
/*
@Composable
fun ProfileView() {
    // 使用依赖注入获取ViewModel Factory
    val viewModelFactory = org.koin.compose.koinInject<com.example.kmpuniversalapp.di.ViewModelFactory>()
    val profileViewModel = remember { viewModelFactory.createProfileViewModel() }
    val users by profileViewModel.users.collectAsState()
    val isLoading by profileViewModel.isLoading.collectAsState()
    val error by profileViewModel.error.collectAsState()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 用户信息卡片
        item {
            UserInfoCard(
                userName = "KMP开发者",
                userEmail = "developer@kmp.com",
                avatar = "K"
            )
        }
        
        // 功能菜单
        item {
            Text(
                text = "功能设置",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        val menuItems = listOf(
            MenuItem("个人信息", "查看和编辑个人资料", Icons.Filled.Person, Color.Blue),
            MenuItem("账户设置", "密码、安全等账户相关设置", Icons.Filled.Settings, Color.Green),
            MenuItem("通知设置", "管理推送通知和提醒", Icons.Filled.Notifications, Color(0xFFFF9800)),
            MenuItem("隐私设置", "隐私政策和数据管理", Icons.Filled.PrivacyTip, Color(0xFF9C27B0)),
            MenuItem("关于应用", "版本信息和帮助文档", Icons.Filled.Info, Color.Gray),
            MenuItem("退出登录", "安全退出当前账户", Icons.Filled.ExitToApp, Color.Red)
        )
        
        items(menuItems) { menuItem ->
            MenuItemCard(menuItem = menuItem)
        }
        
        // 用户数据展示
        if (users.isNotEmpty()) {
            item {
                Text(
                    text = "用户数据",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            item {
                UserDataCard(users = users)
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
        error?.let { errorMessage ->
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = "错误: $errorMessage",
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoCard(
    userName: String,
    userEmail: String,
    avatar: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = avatar,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 用户信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // 编辑按钮
            IconButton(
                onClick = { //编辑个人信息 
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "编辑",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { //处理菜单点击 
        },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 图标
            Icon(
                imageVector = menuItem.icon,
                contentDescription = null,
                tint = menuItem.color,
                modifier = Modifier.size(24.dp)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // 内容
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = menuItem.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // 箭头
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun UserDataCard(users: List<com.example.kmpuniversalapp.data.User>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "用户列表 (${users.size}人)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            users.take(3).forEach { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.name.first().toString(),
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = user.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = user.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                if (user != users.take(3).last()) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                }
            }
            
            if (users.size > 3) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "还有 ${users.size - 3} 个用户...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

data class MenuItem(
    val title: String,
    val description: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val color: Color
)
*/
