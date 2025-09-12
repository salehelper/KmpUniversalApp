package com.example.kmpuniversalapp.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<String>>(emptyList()) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 搜索栏
        TopAppBar(
            title = { Text("搜索") },
            navigationIcon = {
                IconButton(onClick = { /* 简化实现，暂时不处理返回 */ }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                }
            }
        )
        
        // 搜索输入框
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("搜索内容") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "搜索") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        
        // 搜索结果
        if (searchResults.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) "请输入搜索内容" else "暂无搜索结果",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                searchResults.forEach { result ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { /* 处理搜索结果点击 */ }
                    ) {
                        Text(
                            text = result,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}
