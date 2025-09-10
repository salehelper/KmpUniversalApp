package com.example.kmpuniversalapp.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultModel(
    val id: String,
    val title: String,
    val summary: String,
    val type: String,
    val typeIcon: String,
    val category: String? = null,
    val author: String,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val createdAt: String,
    val tags: List<String> = emptyList(),
    val url: String? = null
) {
    val formattedCreatedAt: String
        get() = formatTime(createdAt)
    
    private fun formatTime(timeStr: String): String {
        // 简单的时间格式化，实际项目中可以使用更复杂的时间处理
        return timeStr.take(10) // 只显示日期部分
    }
}
