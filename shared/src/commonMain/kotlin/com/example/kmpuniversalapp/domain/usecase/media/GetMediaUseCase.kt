package com.example.kmpuniversalapp.domain.usecase.media

import com.example.kmpuniversalapp.domain.model.Video
import com.example.kmpuniversalapp.domain.model.ImageCarousel
import com.example.kmpuniversalapp.domain.model.VideoCategory
import com.example.kmpuniversalapp.domain.model.CarouselCategory
import com.example.kmpuniversalapp.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow

/**
 * 获取媒体用例
 * 按照DDD原则，封装业务逻辑
 */
class GetMediaUseCase(
    private val mediaRepository: MediaRepository
) {
    /**
     * 获取所有视频
     */
    suspend fun getAllVideos(): Flow<List<Video>> {
        return mediaRepository.getAllVideos()
    }
    
    /**
     * 根据分类获取视频
     */
    suspend fun getVideosByCategory(category: VideoCategory): Flow<List<Video>> {
        return mediaRepository.getVideosByCategory(category)
    }
    
    /**
     * 获取推荐视频
     */
    suspend fun getRecommendedVideos(): Flow<List<Video>> {
        return mediaRepository.getRecommendedVideos()
    }
    
    /**
     * 获取热门视频
     */
    suspend fun getPopularVideos(): Flow<List<Video>> {
        return mediaRepository.getPopularVideos()
    }
    
    /**
     * 搜索视频
     */
    suspend fun searchVideos(query: String): Flow<List<Video>> {
        if (query.isBlank()) {
            return mediaRepository.getAllVideos()
        }
        return mediaRepository.searchVideos(query)
    }
    
    /**
     * 获取所有轮播图
     */
    suspend fun getAllCarousels(): Flow<List<ImageCarousel>> {
        return mediaRepository.getAllCarousels()
    }
    
    /**
     * 根据分类获取轮播图
     */
    suspend fun getCarouselsByCategory(category: CarouselCategory): Flow<List<ImageCarousel>> {
        return mediaRepository.getCarouselsByCategory(category)
    }
    
    /**
     * 获取活跃轮播图
     */
    suspend fun getActiveCarousels(): Flow<List<ImageCarousel>> {
        return mediaRepository.getActiveCarousels()
    }
}
