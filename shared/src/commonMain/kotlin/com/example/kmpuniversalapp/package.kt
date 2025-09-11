/**
 * 模块化架构主导出文件
 * 统一导出所有模块的公共接口
 */
@file:Suppress("unused")

package com.example.kmpuniversalapp.modules

/**
 * 模块化架构说明：
 * 
 * 1. core - 核心模块
 *    - base: 基础类、通用工具
 *    - utils: 工具类、日志、时间等
 *    - di: 依赖注入配置
 * 
 * 2. features - 功能模块
 *    - home: 首页功能
 *    - search: 搜索功能
 *    - message: 消息功能
 *    - profile: 个人资料功能
 *    - account: 账户功能
 * 
 * 3. infrastructure - 基础设施模块
 *    - network: 网络请求、HTTP客户端
 *    - database: 数据库相关
 *    - storage: 存储相关
 * 
 * 4. presentation - 表现层模块
 *    - ui: UI组件、视图
 *    - navigation: 导航、路由
 * 
 * 模块依赖关系：
 * - features 依赖 core 和 infrastructure
 * - presentation 依赖 core 和 features
 * - infrastructure 依赖 core
 * - core 不依赖其他模块
 */
