package com.example.kmpuniversalapp.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
// 暂时注释图标导入，避免编译错误
// import androidx.compose.material.icons.Icons
// import androidx.compose.material.icons.filled.ArrowBack
// import androidx.compose.material.icons.filled.Menu
// import androidx.compose.material.icons.filled.Notifications
// import androidx.compose.material.icons.filled.Settings
// import androidx.compose.material.icons.filled.Search
// import androidx.compose.material.icons.filled.Clear
// import androidx.compose.material.icons.filled.History
// import androidx.compose.material.icons.filled.TrendingUp
// import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kmpuniversalapp.search.SearchResultModel
import com.example.kmpuniversalapp.search.SearchViewModel
import kotlinx.coroutines.launch

// 暂时注释整个UI文件，避免编译错误
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView() {
    // 使用依赖注入获取ViewModel Factory
    val viewModelFactory = org.koin.compose.koinInject<com.example.kmpuniversalapp.di.ViewModelFactory>()
    val searchViewModel = remember { viewModelFactory.createSearchViewModel() }
    val searchKeyword by searchViewModel.searchKeyword.collectAsState()
    val searchResults by searchViewModel.searchResults.collectAsState()
    val suggestions by searchViewModel.suggestions.collectAsState()
    val searchHistory by searchViewModel.searchHistory.collectAsState()
    val hotSearches by searchViewModel.hotSearches.collectAsState()
    val isSearching by searchViewModel.isSearching.collectAsState()
    val showSearchResults by searchViewModel.showSearchResults.collectAsState()
    val searchStats by searchViewModel.searchStats.collectAsState()
    val paginationInfo by searchViewModel.paginationInfo.collectAsState()
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        searchViewModel.loadSearchHistory()
        searchViewModel.loadHotSearches()
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        keyword = searchKeyword,
                        onKeywordChange = { scope.launch { searchViewModel.setSearchKeyword(it) } },
                        onClearClick = { searchViewModel.clearSearchKeyword() },
                        isSearching = isSearching
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue.copy(alpha = 0.05f)
                ),
                elevation = TopAppBarDefaults.topAppBarElevation(defaultElevation = 0.dp)
            )
        }
    ) { paddingValues ->
        // 内容区域
        if (showSearchResults) {
            SearchResultsContent(
                searchResults = searchResults,
                searchStats = searchStats,
                paginationInfo = paginationInfo,
                onResultClick = { searchViewModel.onSearchResultTap(it) },
                onLoadMore = { scope.launch { searchViewModel.loadMoreResults() } }
            )
        } else {
            SearchSuggestionsContent(
                searchHistory = searchHistory,
                hotSearches = hotSearches,
                suggestions = suggestions,
                onHistoryClick = { scope.launch { searchViewModel.selectHistory(it) } },
                onHotSearchClick = { scope.launch { searchViewModel.selectHotSearch(it) } },
                onSuggestionClick = { scope.launch { searchViewModel.selectSuggestion(it) } },
                onClearHistory = { scope.launch { searchViewModel.clearSearchHistory() } }
            )
        }
    }
}

@Composable
fun SearchBar(
    keyword: String,
    onKeywordChange: (String) -> Unit,
    onClearClick: () -> Unit,
    isSearching: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            TextField(
                value = keyword,
                onValueChange = onKeywordChange,
                placeholder = { Text("搜索内容...") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            
            if (keyword.isNotEmpty()) {
                IconButton(onClick = onClearClick) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "清除",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            if (isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
fun SearchSuggestionsContent(
    searchHistory: List<String>,
    hotSearches: List<String>,
    suggestions: List<String>,
    onHistoryClick: (String) -> Unit,
    onHotSearchClick: (String) -> Unit,
    onSuggestionClick: (String) -> Unit,
    onClearHistory: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 搜索历史
        if (searchHistory.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "搜索历史",
                    icon = Icons.Filled.History,
                    onActionClick = onClearHistory,
                    actionText = "清除"
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchHistory) { history ->
                        SuggestionChip(
                            text = history,
                            onClick = { onHistoryClick(history) },
                            icon = Icons.Filled.History,
                            color = Color.Blue
                        )
                    }
                }
            }
        }
        
        // 热门搜索
        if (hotSearches.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "热门搜索",
                    icon = Icons.Filled.TrendingUp
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(hotSearches) { hotSearch ->
                        SuggestionChip(
                            text = hotSearch,
                            onClick = { onHotSearchClick(hotSearch) },
                            icon = Icons.Filled.TrendingUp,
                            color = Color(0xFFFF9800)
                        )
                    }
                }
            }
        }
        
        // 搜索建议
        if (suggestions.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "搜索建议",
                    icon = Icons.Filled.Lightbulb
                )
            }
            
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(suggestions) { suggestion ->
                        SuggestionChip(
                            text = suggestion,
                            onClick = { onSuggestionClick(suggestion) },
                            icon = Icons.Filled.Lightbulb,
                            color = Color.Green
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultsContent(
    searchResults: List<SearchResultModel>,
    searchStats: String,
    paginationInfo: String,
    onResultClick: (SearchResultModel) -> Unit,
    onLoadMore: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 搜索结果统计和分页信息
        if (searchStats.isNotEmpty() || paginationInfo.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 搜索结果统计
                    if (searchStats.isNotEmpty()) {
                        Text(
                            text = searchStats,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // 分页信息
                    if (paginationInfo.isNotEmpty()) {
                        Text(
                            text = paginationInfo,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
        
        // 搜索结果列表
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults) { result ->
                SearchResultItem(
                    result = result,
                    onClick = { onResultClick(result) }
                )
            }
            
            // 加载更多按钮
            item {
                Button(
                    onClick = onLoadMore,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text("加载更多")
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    result: SearchResultModel,
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
            // 标题和类型
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = result.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Surface(
                    color = getTypeColor(result.type).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = result.typeIcon,
                            style = MaterialTheme.typography.labelSmall
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = result.type,
                            style = MaterialTheme.typography.labelSmall,
                            color = getTypeColor(result.type)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 摘要
            Text(
                text = result.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 标签
            if (result.tags.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(result.tags.take(3)) { tag ->
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // 底部信息
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = result.author,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = result.viewCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = result.likeCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onActionClick: (() -> Unit)? = null,
    actionText: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        onActionClick?.let { action ->
            TextButton(onClick = action) {
                Text(
                    text = actionText ?: "操作",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SuggestionChip(
    text: String,
    onClick: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "article" -> Color.Blue
        "video" -> Color(0xFFFF9800)
        "image" -> Color.Green
        "document" -> Color(0xFF9C27B0)
        "link" -> Color(0xFFFF9800)
        else -> Color.Gray
    }
}
*/
