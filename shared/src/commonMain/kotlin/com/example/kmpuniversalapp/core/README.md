# Core Module (核心模块)

## 📋 模块概述

核心模块提供应用的基础功能和通用工具，是其他所有模块的依赖基础。遵循接口隔离原则，通过接口定义清晰的模块边界。

## 🏗️ 模块结构

```
core/
├── CoreInterfaces.kt # 核心模块接口
├── base/                # 基础类
│   ├── BaseViewModel.kt # 基础ViewModel
│   ├── AppConfig.kt     # 应用配置
│   ├── Constants.kt     # 常量定义
│   └── Result.kt        # 结果封装
├── di/                  # 依赖注入
│   ├── AppModule.kt     # 应用模块
│   ├── KoinInitializer.kt # Koin初始化
│   └── ViewModelFactory.kt # ViewModel工厂
└── utils/               # 工具类
    ├── log/            # 日志工具
    ├── time/           # 时间工具
    ├── storage/        # 存储工具
    ├── debug/          # 调试工具
    ├── device/         # 设备工具
    └── notification/   # 通知工具
```

## 🎯 模块职责

### 1. 接口定义 (interfaces/)
- **职责**: 定义核心模块的公共接口
- **原则**: 接口隔离原则，每个接口职责单一
- **包含**: ILogger, IStorage, INetworkClient, ITimeProvider, IDeviceInfo

### 2. 基础类 (base/)
- **职责**: 提供应用的基础类和通用组件
- **包含**: BaseViewModel, AppConfig, Constants, Result
- **特点**: 可被所有模块复用

### 3. 依赖注入 (di/)
- **职责**: 管理依赖注入配置
- **包含**: AppModule, KoinInitializer, ViewModelFactory
- **特点**: 统一管理依赖关系

### 4. 工具类 (utils/)
- **职责**: 提供通用工具函数
- **包含**: 日志、时间、存储、调试、设备、通知等工具
- **特点**: 平台无关的通用功能

## 🔗 依赖关系

```
core (无依赖)
  ↑
infrastructure (依赖 core)
  ↑
features (依赖 core + infrastructure)
  ↑
presentation (依赖 core + features)
```

## 📦 公共接口

### ILogger (日志接口)
```kotlin
interface ILogger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}
```

### IStorage (存储接口)
```kotlin
interface IStorage {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String = ""): String
    suspend fun remove(key: String)
    suspend fun contains(key: String): Boolean
    fun observeString(key: String, defaultValue: String): Flow<String>
}
```

## 🚀 使用方式

### 导入核心模块
```kotlin
import com.example.kmpuniversalapp.core.*
import com.example.kmpuniversalapp.core.interfaces.*
```

### 使用基础类
```kotlin
class MyViewModel(
    private val logger: ILogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 实现业务逻辑
}
```

### 使用工具类
```kotlin
val logger = AppLogger
logger.d("MyTag", "Debug message")
```

## ✅ 设计原则

1. **单一职责原则**: 每个类只有一个变化的理由
2. **接口隔离原则**: 接口小而专一，避免臃肿接口
3. **依赖倒置原则**: 依赖抽象而非具体实现
4. **开闭原则**: 对扩展开放，对修改关闭

## 📝 注意事项

1. **无外部依赖**: 核心模块不应依赖其他业务模块
2. **接口优先**: 优先定义接口，再提供实现
3. **平台无关**: 工具类应支持多平台
4. **向后兼容**: 保持接口的向后兼容性


