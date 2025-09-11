# KMP Universal App - 模块化架构

## 📋 项目概述

本项目采用Kotlin Multiplatform (KMP)技术，实现跨平台应用开发。通过模块化架构设计和接口隔离原则，提供清晰的代码组织、良好的可维护性和高度的可扩展性。

## 🏗️ 模块结构

```
shared/src/commonMain/kotlin/com/example/kmpuniversalapp/
├── core/                    # 核心模块
│   ├── interfaces/         # 核心接口定义
│   ├── base/               # 基础类 (BaseViewModel, AppConfig, Constants, Result)
│   ├── di/                 # 依赖注入 (AppModule, KoinInitializer, ViewModelFactory)
│   └── utils/              # 工具类 (log, debug, time, storage, device, notification)
├── features/               # 功能模块
│   ├── interfaces/         # 功能接口定义
│   ├── home/               # 首页模块
│   ├── search/             # 搜索模块
│   ├── account/            # 账户模块
│   ├── message/            # 消息模块
│   └── profile/            # 个人中心模块
├── infrastructure/         # 基础设施模块
│   ├── interfaces/         # 基础设施接口定义
│   ├── network/            # 网络层 (HttpClient)
│   ├── database/           # 数据库层
│   ├── storage/            # 存储层 (StorageManager, DataStoreManager)
│   └── cache/              # 缓存层
└── presentation/           # 表现层模块
    ├── interfaces/         # 表现层接口定义
    ├── navigation/         # 导航层 (AppNavigation, AppScreen, Route)
    ├── ui/                 # UI组件 (MainTabView, CommonWidgets, 各功能UI)
    ├── state/              # 状态管理
    └── managers/           # 管理器 (Theme, Dialog, Permission)
```

## 🎯 设计原则

### 1. 接口隔离原则 (Interface Segregation Principle)
- **定义**: 客户端不应依赖它不需要的接口
- **实现**: 每个模块通过接口定义清晰的边界
- **好处**: 降低耦合度，提高可维护性

### 2. 依赖倒置原则 (Dependency Inversion Principle)
- **定义**: 高层模块不应依赖低层模块，两者都应依赖抽象
- **实现**: 通过接口定义依赖关系
- **好处**: 支持多种实现，便于测试

### 3. 单一职责原则 (Single Responsibility Principle)
- **定义**: 每个类只有一个变化的理由
- **实现**: 每个模块职责明确
- **好处**: 代码更清晰，易于理解

### 4. 开闭原则 (Open/Closed Principle)
- **定义**: 对扩展开放，对修改关闭
- **实现**: 通过接口和抽象类支持扩展
- **好处**: 易于添加新功能

## 🔗 模块依赖关系

```
presentation (表现层)
  ↓ 依赖
features (功能层)
  ↓ 依赖
infrastructure (基础设施层)
  ↓ 依赖
core (核心层)
```

### 依赖规则
1. **core**: 不依赖任何其他模块
2. **infrastructure**: 只依赖core模块
3. **features**: 依赖core和infrastructure模块
4. **presentation**: 依赖core、infrastructure和features模块

## 📦 接口定义

### 核心模块接口
- `ILogger`: 日志接口
- `IStorage`: 存储接口
- `INetworkClient`: 网络客户端接口
- `ITimeProvider`: 时间提供者接口
- `IDeviceInfo`: 设备信息接口

### 功能模块接口
- `IHomeFeature`: 首页功能接口
- `ISearchFeature`: 搜索功能接口
- `IAccountFeature`: 账户功能接口
- `IMessageFeature`: 消息功能接口
- `IProfileFeature`: 个人资料功能接口

### 基础设施模块接口
- `IDatabase`: 数据库接口
- `ICache`: 缓存接口
- `IFileStorage`: 文件存储接口
- `INetworkMonitor`: 网络监控接口
- `IConfigManager`: 配置管理接口

### 表现层模块接口
- `INavigation`: 导航接口
- `IStateManager`: 状态管理接口
- `IThemeManager`: 主题管理接口
- `IDialogManager`: 对话框管理接口
- `IPermissionManager`: 权限管理接口

## 🚀 使用方式

### 导入模块
```kotlin
// 导入核心模块
import com.example.kmpuniversalapp.core.*
import com.example.kmpuniversalapp.core.interfaces.*

// 导入功能模块
import com.example.kmpuniversalapp.features.home.*
import com.example.kmpuniversalapp.features.interfaces.*

// 导入基础设施模块
import com.example.kmpuniversalapp.infrastructure.*
import com.example.kmpuniversalapp.infrastructure.interfaces.*

// 导入表现层模块
import com.example.kmpuniversalapp.presentation.*
import com.example.kmpuniversalapp.presentation.interfaces.*
```

### 依赖注入配置
```kotlin
val appModule = module {
    // 核心模块
    single<ILogger> { AppLogger }
    single<IStorage> { DataStoreManager() }
    single<INetworkClient> { HttpClient() }
    
    // 功能模块
    single<IHomeFeature> { HomeApiService() }
    single<ISearchFeature> { SearchApiService() }
    
    // 基础设施模块
    single<IDatabase> { DatabaseManager() }
    single<ICache> { CacheManager() }
    
    // 表现层模块
    single<INavigation> { NavigationManager() }
    single<IThemeManager> { ThemeManager() }
}
```

## ✅ 模块化优势

1. **清晰的职责分离**: 每个模块有明确的职责
2. **降低耦合度**: 模块间通过接口交互
3. **提高可维护性**: 修改某个功能只影响对应模块
4. **便于测试**: 可以独立测试每个模块
5. **支持团队协作**: 不同开发者可以负责不同模块
6. **代码复用**: 核心模块可以被多个功能模块复用
7. **易于扩展**: 通过接口支持多种实现

## 📝 开发规范

### 1. 接口定义规范
- 接口名以 `I` 开头
- 接口方法职责单一
- 避免臃肿接口
- 提供清晰的文档

### 2. 模块边界规范
- 模块间只能通过接口交互
- 避免循环依赖
- 保持模块独立性

### 3. 依赖注入规范
- 使用Koin进行依赖注入
- 为每个模块定义Koin模块
- 通过接口注入依赖

### 4. 测试规范
- 为每个模块编写单元测试
- 使用Mock对象测试依赖
- 测试模块间的接口交互

## 🔄 迁移指南

### 从旧结构迁移到新结构

1. **更新包名**: 所有文件的包名需要更新为新的模块化包名
2. **更新导入**: 所有import语句需要更新为新的模块路径
3. **更新依赖注入**: DI配置需要更新为新的模块结构
4. **测试验证**: 确保所有功能在新结构下正常工作

### 包名映射

| 旧包名 | 新包名 |
|--------|--------|
| `com.example.kmpuniversalapp.modules.core` | `com.example.kmpuniversalapp.core` |
| `com.example.kmpuniversalapp.modules.features.home` | `com.example.kmpuniversalapp.features.home` |
| `com.example.kmpuniversalapp.modules.infrastructure.network` | `com.example.kmpuniversalapp.infrastructure.network` |
| `com.example.kmpuniversalapp.modules.presentation.ui` | `com.example.kmpuniversalapp.presentation.ui` |

## 📚 相关文档

- [Core Module README](core/README.md)
- [Features Module README](features/README.md)
- [Infrastructure Module README](infrastructure/README.md)
- [Presentation Module README](presentation/README.md)


