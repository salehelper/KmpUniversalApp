package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.IDeviceInfo

/**
 * 设备信息实现类
 * 提供设备信息获取功能
 */
class DeviceInfoImpl : IDeviceInfo {
    
    override fun getPlatformName(): String {
        // 简化实现，返回通用平台名称
        // 在实际项目中，可以通过平台特定的实现来获取准确的平台信息
        return "KMP"
    }

    override fun getAppVersion(): String {
        // 实际项目中应该从配置文件中读取版本号
        return "1.0.0"
    }

    override fun isDebugMode(): Boolean {
        // 实际项目中应该根据构建配置来判断
        return true
    }
}
