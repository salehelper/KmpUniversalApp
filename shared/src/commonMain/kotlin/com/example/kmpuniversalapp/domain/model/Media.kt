package com.example.kmpuniversalapp.domain.model

import kotlinx.serialization.Serializable

/**
 * 视频领域模型
 */
@Serializable
data class Video(
    val id: String,
    val title: String,
    val description: String? = null,
    val thumbnailUrl: String,
    val videoUrl: String,
    val duration: Int, // 秒
    val category: VideoCategory,
    val tags: List<String> = emptyList(),
    val author: String,
    val viewCount: Int = 0,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val isLive: Boolean = false,
    val isRecommended: Boolean = false,
    val publishedAt: String,
    val quality: VideoQuality = VideoQuality.HD
) {
    /**
     * 业务规则：格式化时长
     */
    fun getFormattedDuration(): String {
        val minutes = duration / 60
        val seconds = duration % 60
        return "${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }
    
    /**
     * 业务规则：检查是否为长视频
     */
    fun isLongVideo(): Boolean = duration > 600 // 10分钟
}

@Serializable
enum class VideoCategory {
    EDUCATION, ENTERTAINMENT, NEWS, TUTORIAL, LIVE, OTHER
}

@Serializable
enum class VideoQuality {
    SD, HD, FHD, UHD
}

/**
 * 图片轮播领域模型
 */
@Serializable
data class ImageCarousel(
    val id: String,
    val title: String,
    val description: String? = null,
    val imageUrl: String,
    val linkUrl: String? = null,
    val category: CarouselCategory,
    val sortOrder: Int = 0,
    val isActive: Boolean = true,
    val startDate: String? = null,
    val endDate: String? = null,
    val clickCount: Int = 0,
    val createdAt: String
) {
    /**
     * 业务规则：检查是否在有效期内
     */
    fun isInValidPeriod(): Boolean {
        // 简化实现，实际应该比较日期
        return isActive
    }
    
    /**
     * 业务规则：检查是否为热门轮播
     */
    fun isPopular(): Boolean = clickCount > 100
}

@Serializable
enum class CarouselCategory {
    PROMOTION, NEWS, PRODUCT, EVENT, OTHER
}

/**
 * 媒体聚合根
 */
@Serializable
data class MediaCollection(
    val videos: List<Video>,
    val carousels: List<ImageCarousel>,
    val totalVideos: Int,
    val totalCarousels: Int
) {
    /**
     * 业务规则：获取推荐视频
     */
    fun getRecommendedVideos(): List<Video> = videos.filter { it.isRecommended }
    
    /**
     * 业务规则：获取活跃轮播
     */
    fun getActiveCarousels(): List<ImageCarousel> = 
        carousels.filter { it.isActive && it.isInValidPeriod() }
            .sortedBy { it.sortOrder }
}
