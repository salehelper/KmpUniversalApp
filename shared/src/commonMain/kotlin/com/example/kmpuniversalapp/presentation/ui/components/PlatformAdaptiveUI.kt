/*
 * 平台自适应UI组件
 * 展示如何在不同平台上提供最佳的用户体验
 */

package com.example.kmpuniversalapp.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmpuniversalapp.core.utils.log.AppLogger

/**
 * 平台自适应UI示例
 * 根据平台特性提供不同的UI体验
 */
@Composable
fun PlatformAdaptiveUI() {
    var selectedTab by remember { mutableStateOf(0) }
    var showPlatformInfo by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 标题
        Text(
            text = "平台自适应UI设计",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        // 平台信息卡片
        PlatformInfoCard(
            showDetails = showPlatformInfo,
            onToggleDetails = { showPlatformInfo = !showPlatformInfo }
        )
        
        // 平台特定的UI组件
        PlatformSpecificComponents()
        
        // 自适应布局示例
        AdaptiveLayoutExample()
        
        // 平台特定的交互示例
        PlatformInteractionExample()
        
        // 性能优化示例
        PerformanceOptimizationExample()
    }
}

/**
 * 平台信息卡片
 */
@Composable
private fun PlatformInfoCard(
    showDetails: Boolean,
    onToggleDetails: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "平台信息",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onToggleDetails) {
                    Icon(
                        imageVector = if (showDetails) 
                            com.example.kmpuniversalapp.core.utils.IconsUtils.VisibilityOff
                        else 
                            com.example.kmpuniversalapp.core.utils.IconsUtils.Visibility,
                        contentDescription = if (showDetails) "隐藏详情" else "显示详情"
                    )
                }
            }
            
            Text(
                text = "当前平台: ${getPlatformName()}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            
            if (showDetails) {
                Spacer(modifier = Modifier.height(8.dp))
                PlatformDetailsContent()
            }
        }
    }
}

/**
 * 平台详情内容
 */
@Composable
private fun PlatformDetailsContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "UI框架: Compose Multiplatform",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "设计系统: Material3",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "状态管理: Compose State",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
        Text(
            text = "导航: 自定义导航系统",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
        )
    }
}

/**
 * 平台特定的UI组件
 */
@Composable
private fun PlatformSpecificComponents() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "平台特定组件",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // 平台特定的按钮样式
            PlatformAdaptiveButton(
                text = "平台自适应按钮",
                onClick = { AppLogger.i("PlatformUI", "平台自适应按钮被点击") }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 平台特定的输入框
            PlatformAdaptiveTextField(
                label = "平台自适应输入框",
                onValueChange = { AppLogger.i("PlatformUI", "输入: $it") }
            )
        }
    }
}

/**
 * 平台自适应按钮
 */
@Composable
private fun PlatformAdaptiveButton(
    text: String,
    onClick: () -> Unit
) {
    val platformName = getPlatformName()
    val buttonStyle = when {
        platformName.contains("Android", ignoreCase = true) -> {
            // Android风格：Material Design
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text)
            }
        }
        platformName.contains("iOS", ignoreCase = true) -> {
            // iOS风格：更圆润的按钮
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Text(text)
            }
        }
        else -> {
            // 默认风格
            OutlinedButton(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text)
            }
        }
    }
}

/**
 * 平台自适应输入框
 */
@Composable
private fun PlatformAdaptiveTextField(
    label: String,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    
    val platformName = getPlatformName()
    val textFieldStyle = when {
        platformName.contains("Android", ignoreCase = true) -> {
            // Android风格：OutlinedTextField
            OutlinedTextField(
                value = text,
                onValueChange = { 
                    text = it
                    onValueChange(it)
                },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        platformName.contains("iOS", ignoreCase = true) -> {
            // iOS风格：OutlinedTextField
            OutlinedTextField(
                value = text,
                onValueChange = { 
                    text = it
                    onValueChange(it)
                },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth()
            )
        }
        else -> {
            // 默认风格
            OutlinedTextField(
                value = text,
                onValueChange = { 
                    text = it
                    onValueChange(it)
                },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * 自适应布局示例
 */
@Composable
private fun AdaptiveLayoutExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "自适应布局",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // 根据平台调整布局
            val platformName = getPlatformName()
            if (platformName.contains("Android", ignoreCase = true)) {
                // Android：垂直布局
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Android垂直布局")
                    Text("适合Android的Material Design")
                }
            } else {
                // iOS：水平布局
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("iOS水平布局")
                    Text("适合iOS的Human Interface Guidelines")
                }
            }
        }
    }
}

/**
 * 平台特定的交互示例
 */
@Composable
private fun PlatformInteractionExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "平台特定交互",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            val platformName = getPlatformName()
            when {
                platformName.contains("Android", ignoreCase = true) -> {
                    Text(
                        text = "Android: 支持长按、双击等手势",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                platformName.contains("iOS", ignoreCase = true) -> {
                    Text(
                        text = "iOS: 支持3D Touch、Haptic Feedback等",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
                else -> {
                    Text(
                        text = "通用: 基础触摸交互",
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    }
}

/**
 * 性能优化示例
 */
@Composable
private fun PerformanceOptimizationExample() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "性能优化",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "• 使用remember避免重复计算",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "• 使用LazyColumn优化长列表",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "• 平台特定的资源优化",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "• 条件编译减少包大小",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
