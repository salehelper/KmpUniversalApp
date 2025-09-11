package com.example.kmpuniversalapp.core

import com.example.kmpuniversalapp.core.implementations.*

/**
 * 依赖注入配置
 * 管理接口和实现类的绑定关系
 */
object DI {
    
    // 核心服务实例
    private val _logger: ILogger by lazy { LoggerImpl() }
    private val _storage: IStorage by lazy { StorageImpl() }
    private val _networkClient: INetworkClient by lazy { NetworkClientImpl(_logger) }
    private val _timeProvider: ITimeProvider by lazy { TimeProviderImpl() }
    private val _deviceInfo: IDeviceInfo by lazy { DeviceInfoImpl() }
    
    /**
     * 获取日志服务
     */
    fun getLogger(): ILogger = _logger
    
    /**
     * 获取存储服务
     */
    fun getStorage(): IStorage = _storage
    
    /**
     * 获取网络客户端
     */
    fun getNetworkClient(): INetworkClient = _networkClient
    
    /**
     * 获取时间提供者
     */
    fun getTimeProvider(): ITimeProvider = _timeProvider
    
    /**
     * 获取设备信息
     */
    fun getDeviceInfo(): IDeviceInfo = _deviceInfo
    
    /**
     * 重置所有服务（用于测试）
     */
    fun reset() {
        // 在实际项目中，这里应该重置所有单例实例
        // 由于我们使用的是 lazy 初始化，这里只是示例
    }
}
