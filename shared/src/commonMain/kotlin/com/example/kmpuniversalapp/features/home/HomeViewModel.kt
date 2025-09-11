package com.example.kmpuniversalapp.features.home

import com.example.kmpuniversalapp.core.utils.log.AppLogger
import com.example.kmpuniversalapp.core.utils.time.PlatformTimeUtils
import com.example.kmpuniversalapp.core.base.BaseViewModel
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val homeApiService: HomeApiService,
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    
    // Banner数据
    private val _banners = MutableStateFlow<List<BannerModel>>(emptyList())
    val banners: StateFlow<List<BannerModel>> = _banners.asStateFlow()
    
    // 动态数据
    private val _dynamics = MutableStateFlow<List<DynamicModel>>(emptyList())
    val dynamics: StateFlow<List<DynamicModel>> = _dynamics.asStateFlow()
    
    // 统计数据
    private val _statistics = MutableStateFlow<Map<String, Any>>(emptyMap())
    val statistics: StateFlow<Map<String, Any>> = _statistics.asStateFlow()
    
    // 当前Banner索引
    private val _currentBannerIndex = MutableStateFlow(0)
    val currentBannerIndex: StateFlow<Int> = _currentBannerIndex.asStateFlow()
    
    // 加载状态和错误信息继承自BaseViewModel
    
    // 是否自动播放Banner
    private val _autoPlayBanner = MutableStateFlow(true)
    val autoPlayBanner: StateFlow<Boolean> = _autoPlayBanner.asStateFlow()
    
    fun loadHomeData() {
        logger.i("HomeViewModel", "开始加载首页数据")
        logger.userAction("加载首页数据")
        
        // 使用 BaseViewModel 的协程管理方法
        submit {
            val startTime = getCurrentTimeMillis()
            setLoading(true)
            clearMessages()
            
            try {
                logger.d("HomeViewModel", "并行加载Banner、动态和统计数据")
                
                // 并行加载数据 - 使用 execute 方法
                val bannersDeferred = execute { 
                    logger.d("HomeViewModel", "开始加载Banner数据")
                    homeApiService.getBanners() 
                }
                val dynamicsDeferred = execute { 
                    logger.d("HomeViewModel", "开始加载动态数据")
                    homeApiService.getDynamics() 
                }
                val statisticsDeferred = execute { 
                    logger.d("HomeViewModel", "开始加载统计数据")
                    homeApiService.getHomeStatistics() 
                }
                
                // 等待所有结果
                val bannersResult = bannersDeferred.await()
                val dynamicsResult = dynamicsDeferred.await()
                val statisticsResult = statisticsDeferred.await()
                
                logger.d("HomeViewModel", "数据加载完成 - Banner: ${bannersResult.size}条, 动态: ${dynamicsResult.size}条, 统计: ${statisticsResult.size}项")
                
                _banners.value = bannersResult
                _dynamics.value = dynamicsResult
                _statistics.value = statisticsResult
                
                val duration = getCurrentTimeMillis() - startTime
                logger.performance("加载首页数据", duration, "Banner: ${bannersResult.size}, 动态: ${dynamicsResult.size}")
                logger.i("HomeViewModel", "首页数据加载成功")
                setSuccess("数据加载成功")
                
            } catch (e: Exception) {
                logger.error("HomeViewModel", "加载首页数据", e, "并行加载Banner、动态和统计数据")
                handleException(e, "加载数据失败")
            } finally {
                setLoading(false)
            }
        }
    }
    
    fun refreshBanners() {
        logger.d("HomeViewModel", "开始刷新Banner数据")
        submit {
            try {
                val banners = homeApiService.getBanners()
                _banners.value = banners
                logger.i("HomeViewModel", "Banner刷新成功，共${banners.size}条")
                setSuccess("Banner刷新成功")
            } catch (e: Exception) {
                logger.error("HomeViewModel", "刷新Banner", e)
                handleException(e, "刷新Banner失败")
            }
        }
    }
    
    fun refreshDynamics() {
        logger.d("HomeViewModel", "开始刷新动态数据")
        submit {
            try {
                val dynamics = homeApiService.getDynamics()
                _dynamics.value = dynamics
                logger.i("HomeViewModel", "动态刷新成功，共${dynamics.size}条")
                setSuccess("动态刷新成功")
            } catch (e: Exception) {
                logger.error("HomeViewModel", "刷新动态", e)
                handleException(e, "刷新动态失败")
            }
        }
    }
    
    fun loadMoreDynamics() {
        logger.d("HomeViewModel", "开始加载更多动态数据")
        submit {
            try {
                // 模拟加载更多数据
                val moreDynamics = homeApiService.getDynamics(page = 2)
                val oldSize = _dynamics.value.size
                _dynamics.value = _dynamics.value + moreDynamics
                logger.i("HomeViewModel", "加载更多成功，新增${moreDynamics.size}条，总计${_dynamics.value.size}条")
                setSuccess("加载更多成功")
            } catch (e: Exception) {
                logger.error("HomeViewModel", "加载更多动态", e)
                handleException(e, "加载更多失败")
            }
        }
    }
    
    fun setBannerIndex(index: Int) {
        if (index >= 0 && index < _banners.value.size) {
            logger.d("HomeViewModel", "设置Banner索引: $index")
            _currentBannerIndex.value = index
        } else {
            logger.w("HomeViewModel", "Banner索引超出范围: $index, 总数: ${_banners.value.size}")
        }
    }
    
    fun nextBanner() {
        if (_banners.value.isNotEmpty()) {
            val nextIndex = (_currentBannerIndex.value + 1) % _banners.value.size
            logger.d("HomeViewModel", "切换到下一个Banner: ${_currentBannerIndex.value} -> $nextIndex")
            _currentBannerIndex.value = nextIndex
        } else {
            logger.w("HomeViewModel", "Banner列表为空，无法切换")
        }
    }
    
    fun previousBanner() {
        if (_banners.value.isNotEmpty()) {
            val prevIndex = if (_currentBannerIndex.value > 0) {
                _currentBannerIndex.value - 1
            } else {
                _banners.value.size - 1
            }
            logger.d("HomeViewModel", "切换到上一个Banner: ${_currentBannerIndex.value} -> $prevIndex")
            _currentBannerIndex.value = prevIndex
        } else {
            logger.w("HomeViewModel", "Banner列表为空，无法切换")
        }
    }
    
    fun onBannerTap(banner: BannerModel) {
        logger.userAction("点击Banner", "标题: ${banner.title}, ID: ${banner.id}")
        logger.d("HomeViewModel", "Banner点击事件 - 标题: ${banner.title}")
    }
    
    fun onDynamicTap(dynamic: DynamicModel) {
        logger.userAction("点击动态", "标题: ${dynamic.title}, ID: ${dynamic.id}")
        logger.d("HomeViewModel", "动态点击事件 - 标题: ${dynamic.title}")
    }
    
    fun getStatisticValue(key: String, defaultValue: String = "0"): String {
        return _statistics.value[key]?.toString() ?: defaultValue
    }
    
    val hasBanners: Boolean
        get() = _banners.value.isNotEmpty()
    
    val hasDynamics: Boolean
        get() = _dynamics.value.isNotEmpty()
    
    val hasStatistics: Boolean
        get() = _statistics.value.isNotEmpty()
    
    // 平台特定的时间获取
    private fun getCurrentTimeMillis(): Long = PlatformTimeUtils.getCurrentTimeMillis()
}
