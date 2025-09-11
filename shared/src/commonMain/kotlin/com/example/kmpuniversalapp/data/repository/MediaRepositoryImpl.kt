package com.example.kmpuniversalapp.data.repository

import com.example.kmpuniversalapp.domain.model.Video
import com.example.kmpuniversalapp.domain.model.ImageCarousel
import com.example.kmpuniversalapp.domain.model.VideoCategory
import com.example.kmpuniversalapp.domain.model.VideoQuality
import com.example.kmpuniversalapp.domain.model.CarouselCategory
import com.example.kmpuniversalapp.domain.repository.MediaRepository
import com.example.kmpuniversalapp.domain.repository.MediaStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * 媒体仓储实现
 * 按照DDD原则，实现仓储接口
 */
class MediaRepositoryImpl : MediaRepository {
    
    // 模拟数据存储
    private val videos = mutableListOf<Video>()
    private val carousels = mutableListOf<ImageCarousel>()
    
    init {
        // 初始化视频数据
        videos.addAll(
            listOf(
                Video(
                    id = "1",
                    title = "Kotlin Multiplatform 入门教程",
                    description = "从零开始学习Kotlin Multiplatform，了解跨平台开发的基本概念和实践",
                    thumbnailUrl = "https://example.com/kmp-tutorial-thumb.jpg",
                    videoUrl = "https://example.com/kmp-tutorial.mp4",
                    duration = 1800, // 30分钟
                    category = VideoCategory.EDUCATION,
                    tags = listOf("Kotlin", "Multiplatform", "教程"),
                    author = "技术讲师",
                    viewCount = 2500,
                    likeCount = 156,
                    commentCount = 34,
                    isLive = false,
                    isRecommended = true,
                    publishedAt = "2024-01-01T10:00:00Z",
                    quality = VideoQuality.HD
                ),
                Video(
                    id = "2",
                    title = "Compose Multiplatform UI设计实战",
                    description = "深入学习Compose Multiplatform的UI设计技巧和最佳实践",
                    thumbnailUrl = "https://example.com/compose-ui-thumb.jpg",
                    videoUrl = "https://example.com/compose-ui.mp4",
                    duration = 2400, // 40分钟
                    category = VideoCategory.TUTORIAL,
                    tags = listOf("Compose", "UI", "设计"),
                    author = "UI设计师",
                    viewCount = 1800,
                    likeCount = 98,
                    commentCount = 22,
                    isLive = false,
                    isRecommended = true,
                    publishedAt = "2024-01-02T14:30:00Z",
                    quality = VideoQuality.FHD
                ),
                Video(
                    id = "3",
                    title = "移动应用性能优化技巧",
                    description = "分享移动应用性能优化的实用技巧和工具",
                    thumbnailUrl = "https://example.com/performance-thumb.jpg",
                    videoUrl = "https://example.com/performance.mp4",
                    duration = 1200, // 20分钟
                    category = VideoCategory.EDUCATION,
                    tags = listOf("性能优化", "移动开发", "技巧"),
                    author = "性能专家",
                    viewCount = 1200,
                    likeCount = 67,
                    commentCount = 15,
                    isLive = false,
                    isRecommended = false,
                    publishedAt = "2024-01-03T09:15:00Z",
                    quality = VideoQuality.HD
                ),
                Video(
                    id = "4",
                    title = "Kotlin协程深度解析",
                    description = "深入理解Kotlin协程的工作原理和使用场景",
                    thumbnailUrl = "https://example.com/coroutines-thumb.jpg",
                    videoUrl = "https://example.com/coroutines.mp4",
                    duration = 2100, // 35分钟
                    category = VideoCategory.EDUCATION,
                    tags = listOf("Kotlin", "协程", "异步编程"),
                    author = "Kotlin专家",
                    viewCount = 3200,
                    likeCount = 189,
                    commentCount = 45,
                    isLive = false,
                    isRecommended = true,
                    publishedAt = "2024-01-04T16:45:00Z",
                    quality = VideoQuality.FHD
                ),
                Video(
                    id = "5",
                    title = "直播：KMP项目实战开发",
                    description = "实时直播KMP项目的开发过程，分享开发经验和技巧",
                    thumbnailUrl = "https://example.com/live-thumb.jpg",
                    videoUrl = "https://example.com/live-stream.mp4",
                    duration = 3600, // 60分钟
                    category = VideoCategory.LIVE,
                    tags = listOf("直播", "KMP", "实战"),
                    author = "开发团队",
                    viewCount = 4500,
                    likeCount = 234,
                    commentCount = 67,
                    isLive = true,
                    isRecommended = true,
                    publishedAt = "2024-01-05T20:00:00Z",
                    quality = VideoQuality.HD
                )
            )
        )
        
        // 初始化轮播图数据
        carousels.addAll(
            listOf(
                ImageCarousel(
                    id = "1",
                    title = "KMP Universal App 正式发布",
                    description = "基于Kotlin Multiplatform的跨平台应用正式发布",
                    imageUrl = "https://example.com/carousel-1.jpg",
                    linkUrl = "https://example.com/app-release",
                    category = CarouselCategory.PROMOTION,
                    sortOrder = 1,
                    isActive = true,
                    startDate = "2024-01-01",
                    endDate = "2024-01-31",
                    clickCount = 1250,
                    createdAt = "2024-01-01T10:00:00Z"
                ),
                ImageCarousel(
                    id = "2",
                    title = "新功能上线：智能推荐",
                    description = "基于AI的智能推荐功能正式上线",
                    imageUrl = "https://example.com/carousel-2.jpg",
                    linkUrl = "https://example.com/ai-feature",
                    category = CarouselCategory.PRODUCT,
                    sortOrder = 2,
                    isActive = true,
                    startDate = "2024-01-02",
                    endDate = "2024-01-31",
                    clickCount = 890,
                    createdAt = "2024-01-02T14:30:00Z"
                ),
                ImageCarousel(
                    id = "3",
                    title = "技术分享会：KMP最佳实践",
                    description = "邀请行业专家分享KMP开发的最佳实践",
                    imageUrl = "https://example.com/carousel-3.jpg",
                    linkUrl = "https://example.com/tech-share",
                    category = CarouselCategory.EVENT,
                    sortOrder = 3,
                    isActive = true,
                    startDate = "2024-01-03",
                    endDate = "2024-01-15",
                    clickCount = 567,
                    createdAt = "2024-01-03T09:15:00Z"
                ),
                ImageCarousel(
                    id = "4",
                    title = "用户反馈：五星好评",
                    description = "感谢用户的支持和反馈，我们会继续努力",
                    imageUrl = "https://example.com/carousel-4.jpg",
                    linkUrl = "https://example.com/user-feedback",
                    category = CarouselCategory.NEWS,
                    sortOrder = 4,
                    isActive = true,
                    startDate = "2024-01-04",
                    endDate = "2024-01-31",
                    clickCount = 432,
                    createdAt = "2024-01-04T16:45:00Z"
                ),
                ImageCarousel(
                    id = "5",
                    title = "开发者社区：欢迎加入",
                    description = "加入我们的开发者社区，与其他开发者交流经验",
                    imageUrl = "https://example.com/carousel-5.jpg",
                    linkUrl = "https://example.com/community",
                    category = CarouselCategory.PROMOTION,
                    sortOrder = 5,
                    isActive = true,
                    startDate = "2024-01-05",
                    endDate = "2024-01-31",
                    clickCount = 678,
                    createdAt = "2024-01-05T11:20:00Z"
                )
            )
        )
    }
    
    // 视频相关方法
    override suspend fun getAllVideos(): Flow<List<Video>> = flow {
        emit(videos.toList())
    }
    
    override suspend fun getVideoById(id: String): Video? {
        return videos.find { it.id == id }
    }
    
    override suspend fun getVideosByCategory(category: VideoCategory): Flow<List<Video>> = flow {
        emit(videos.filter { it.category == category })
    }
    
    override suspend fun getRecommendedVideos(): Flow<List<Video>> = flow {
        emit(videos.filter { it.isRecommended })
    }
    
    override suspend fun getPopularVideos(): Flow<List<Video>> = flow {
        emit(videos.filter { it.viewCount > 1000 })
    }
    
    override suspend fun searchVideos(query: String): Flow<List<Video>> = flow {
        val searchQuery = query.lowercase()
        emit(videos.filter { 
            it.title.lowercase().contains(searchQuery) ||
            it.description?.lowercase()?.contains(searchQuery) == true ||
            it.tags.any { tag -> tag.lowercase().contains(searchQuery) }
        })
    }
    
    override suspend fun incrementVideoViewCount(id: String): Result<Unit> {
        return try {
            val video = videos.find { it.id == id }
            if (video != null) {
                val index = videos.indexOfFirst { it.id == id }
                val updatedVideo = video.copy(viewCount = video.viewCount + 1)
                videos[index] = updatedVideo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("视频不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun likeVideo(id: String): Result<Unit> {
        return try {
            val video = videos.find { it.id == id }
            if (video != null) {
                val index = videos.indexOfFirst { it.id == id }
                val updatedVideo = video.copy(likeCount = video.likeCount + 1)
                videos[index] = updatedVideo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("视频不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun unlikeVideo(id: String): Result<Unit> {
        return try {
            val video = videos.find { it.id == id }
            if (video != null) {
                val index = videos.indexOfFirst { it.id == id }
                val updatedVideo = video.copy(likeCount = maxOf(0, video.likeCount - 1))
                videos[index] = updatedVideo
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("视频不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // 轮播图相关方法
    override suspend fun getAllCarousels(): Flow<List<ImageCarousel>> = flow {
        emit(carousels.toList())
    }
    
    override suspend fun getCarouselById(id: String): ImageCarousel? {
        return carousels.find { it.id == id }
    }
    
    override suspend fun getCarouselsByCategory(category: CarouselCategory): Flow<List<ImageCarousel>> = flow {
        emit(carousels.filter { it.category == category })
    }
    
    override suspend fun getActiveCarousels(): Flow<List<ImageCarousel>> = flow {
        emit(carousels.filter { it.isActive && it.isInValidPeriod() }
            .sortedBy { it.sortOrder })
    }
    
    override suspend fun recordCarouselClick(id: String): Result<Unit> {
        return try {
            val carousel = carousels.find { it.id == id }
            if (carousel != null) {
                val index = carousels.indexOfFirst { it.id == id }
                val updatedCarousel = carousel.copy(clickCount = carousel.clickCount + 1)
                carousels[index] = updatedCarousel
                Result.success(Unit)
            } else {
                Result.failure(IllegalArgumentException("轮播图不存在"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getMediaStatistics(): Flow<MediaStatistics> = flow {
        val totalVideos = videos.size
        val totalCarousels = carousels.size
        val totalVideoViews = videos.sumOf { it.viewCount.toLong() }
        val totalCarouselClicks = carousels.sumOf { it.clickCount.toLong() }
        val popularVideosCount = videos.count { it.viewCount > 1000 }
        val activeCarouselsCount = carousels.count { it.isActive && it.isInValidPeriod() }
        
        emit(
            MediaStatistics(
                totalVideos = totalVideos,
                totalCarousels = totalCarousels,
                totalVideoViews = totalVideoViews,
                totalCarouselClicks = totalCarouselClicks,
                popularVideosCount = popularVideosCount,
                activeCarouselsCount = activeCarouselsCount
            )
        )
    }
}
