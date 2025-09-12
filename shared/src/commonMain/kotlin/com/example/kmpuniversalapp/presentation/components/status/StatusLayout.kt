package com.example.kmpuniversalapp.presentation.components.status

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 状态布局
 * 基于 AndroidProject-Kotlin 的 StatusLayout 实现
 */
@Composable
fun StatusLayout(
    status: StatusType,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    when (status) {
        StatusType.Loading -> {
            LoadingView(modifier = modifier)
        }
        StatusType.Error -> {
            ErrorView(
                onRetry = onRetry,
                modifier = modifier
            )
        }
        StatusType.Empty -> {
            EmptyView(modifier = modifier)
        }
        StatusType.Success -> {
            content()
        }
    }
}

/**
 * 状态类型
 */
enum class StatusType {
    Loading,
    Error,
    Empty,
    Success
}

/**
 * 加载视图
 */
@Composable
private fun LoadingView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "加载中...",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 错误视图
 */
@Composable
private fun ErrorView(
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
        Icon(
            imageVector = Icons.Filled.Error,
            contentDescription = "错误",
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "请求出错，请重试",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onRetry
            ) {
                Text("重试")
            }
        }
    }
}

/**
 * 空数据视图
 */
@Composable
private fun EmptyView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Inbox,
                contentDescription = "空数据",
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.outline
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "空空如也",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * 状态布局管理器
 * 提供状态管理的业务逻辑
 */
class StatusLayoutManager {
    private var _status by mutableStateOf(StatusType.Loading)
    val status: StatusType get() = _status
    
    fun setLoading() {
        _status = StatusType.Loading
    }
    
    fun setError() {
        _status = StatusType.Error
    }
    
    fun setEmpty() {
        _status = StatusType.Empty
    }
    
    fun setSuccess() {
        _status = StatusType.Success
    }
    
    fun isSuccess(): Boolean = _status == StatusType.Success
    fun isLoading(): Boolean = _status == StatusType.Loading
    fun isError(): Boolean = _status == StatusType.Error
    fun isEmpty(): Boolean = _status == StatusType.Empty
}

/**
 * 状态布局组合
 * 提供状态管理的 Composable 函数
 */
@Composable
fun rememberStatusLayoutManager(): StatusLayoutManager {
    return remember { StatusLayoutManager() }
}

/**
 * 状态布局内容
 * 结合状态管理和内容显示
 */
@Composable
fun StatusLayoutContent(
    statusManager: StatusLayoutManager = rememberStatusLayoutManager(),
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    StatusLayout(
        status = statusManager.status,
        onRetry = onRetry,
        modifier = modifier,
        content = content
    )
}

/**
 * 列表状态布局
 * 专门用于列表的状态管理
 */
@Composable
fun ListStatusLayout(
    items: List<Any>,
    isLoading: Boolean = false,
    onRetry: () -> Unit = {},
    modifier: Modifier = Modifier,
    itemContent: @Composable (Any) -> Unit = {}
) {
    val status = when {
        isLoading -> StatusType.Loading
        items.isEmpty() -> StatusType.Empty
        else -> StatusType.Success
    }
    
    StatusLayout(
        status = status,
        onRetry = onRetry,
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                itemContent(item)
            }
        }
    }
}
