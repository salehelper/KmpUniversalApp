package com.example.kmpuniversalapp.domain.repository

import com.example.kmpuniversalapp.domain.model.Video
import com.example.kmpuniversalapp.domain.model.ImageCarousel
import com.example.kmpuniversalapp.domain.model.VideoCategory
import com.example.kmpuniversalapp.domain.model.CarouselCategory
import kotlinx.coroutines.flow.Flow

/**
 * 媒体仓储接口
 * 按照DDD原则，定义仓储契约
 */
interface MediaRepository {
    /**
     * 获取所有视频
     */
    suspend fun getAllVideos(): Flow<List<Video>>
    
    /**
     * 根据ID获取视频
     */
    suspend fun getVideoById(id: String): Video?
    
    /**
     * 根据分类获取视频
     */
    suspend fun getVideosByCategory(category: VideoCategory): Flow<List<Video>>
    
    /**
     * 获取推荐视频
     */
    suspend fun getRecommendedVideos(): Flow<List<Video>>
    
    /**
     * 获取热门视频
     */
    suspend fun getPopularVideos(): Flow<List<Video>>
    
    /**
     * 搜索视频
     */
    suspend fun searchVideos(query: String): Flow<List<Video>>
    
    /**
     * 增加视频浏览量
     */
    suspend fun incrementVideoViewCount(id: String): Result<Unit>
    
    /**
     * 点赞视频
     */
    suspend fun likeVideo(id: String): Result<Unit>
    
    /**
     * 取消点赞视频
     */
    suspend fun unlikeVideo(id: String): Result<Unit>
    
    /**
     * 获取所有轮播图
     */
    suspend fun getAllCarousels(): Flow<List<ImageCarousel>>
    
    /**
     * 根据ID获取轮播图
     */
    suspend fun getCarouselById(id: String): ImageCarousel?
    
    /**
     * 根据分类获取轮播图
     */
    suspend fun getCarouselsByCategory(category: CarouselCategory): Flow<List<ImageCarousel>>
    
    /**
     * 获取活跃轮播图
     */
    suspend fun getActiveCarousels(): Flow<List<ImageCarousel>>
    
    /**
     * 记录轮播图点击
     */
    suspend fun recordCarouselClick(id: String): Result<Unit>
    
    /**
     * 获取媒体统计
     */
    suspend fun getMediaStatistics(): Flow<MediaStatistics>
}

/**
 * 媒体统计信息
 */
data class MediaStatistics(
    val totalVideos: Int,
    val totalCarousels: Int,
    val totalVideoViews: Long,
    val totalCarouselClicks: Long,
    val popularVideosCount: Int,
    val activeCarouselsCount: Int
)
