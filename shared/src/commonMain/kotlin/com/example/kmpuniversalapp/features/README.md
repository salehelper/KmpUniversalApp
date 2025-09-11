# Features Module (功能模块)

## 📋 模块概述

功能模块包含应用的核心业务功能，每个功能模块都是独立的业务单元，通过接口与其他模块交互。

## 🏗️ 模块结构

```
features/
├── FeatureInterfaces.kt # 功能模块接口
├── home/                # 首页功能
│   ├── HomeViewModel.kt # 首页ViewModel
│   ├── HomeApiService.kt # 首页API服务
│   ├── HomeModule.kt    # 首页Koin模块
│   └── models/          # 首页数据模型
├── search/              # 搜索功能
│   ├── SearchViewModel.kt # 搜索ViewModel
│   ├── SearchApiService.kt # 搜索API服务
│   ├── SearchModule.kt  # 搜索Koin模块
│   └── models/          # 搜索数据模型
├── account/             # 账户功能
│   ├── AccountViewModel.kt # 账户ViewModel
│   ├── AccountModule.kt # 账户Koin模块
│   └── models/          # 账户数据模型
├── message/             # 消息功能
│   ├── MessageViewModel.kt # 消息ViewModel
│   ├── MessageModule.kt # 消息Koin模块
│   └── models/          # 消息数据模型
└── profile/             # 个人资料功能
    ├── ProfileViewModel.kt # 个人资料ViewModel
    ├── ProfileModule.kt # 个人资料Koin模块
    └── models/          # 个人资料数据模型
```

## 🎯 模块职责

### 1. 接口定义 (interfaces/)
- **职责**: 定义功能模块的公共接口
- **原则**: 接口隔离原则，每个功能一个接口
- **包含**: IHomeFeature, ISearchFeature, IAccountFeature, IMessageFeature, IProfileFeature

### 2. 功能实现 (各功能目录/)
- **职责**: 实现具体的业务功能
- **包含**: ViewModel, ApiService, Module, Models
- **特点**: 每个功能模块独立，可单独测试

## 🔗 依赖关系

```
features
  ↓
core (基础功能)
  ↓
infrastructure (基础设施)
```

## 📦 公共接口

### IHomeFeature (首页功能接口)
```kotlin
interface IHomeFeature {
    suspend fun loadBanners(): Result<List<Any>>
    suspend fun loadDynamicContent(): Result<List<Any>>
    suspend fun refreshData(): Result<Unit>
}
```

### ISearchFeature (搜索功能接口)
```kotlin
interface ISearchFeature {
    suspend fun search(keyword: String, page: Int, pageSize: Int): Result<SearchResult>
    suspend fun getSuggestions(keyword: String): Result<List<String>>
    suspend fun getSearchHistory(): Result<List<String>>
    suspend fun saveSearchHistory(keyword: String): Result<Unit>
}
```

## 🚀 使用方式

### 导入功能模块
```kotlin
import com.example.kmpuniversalapp.features.home.*
import com.example.kmpuniversalapp.features.interfaces.*
```

### 使用功能接口
```kotlin
class HomeViewModel(
    private val homeFeature: IHomeFeature,
    private val logger: ILogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 实现业务逻辑
}
```

### 依赖注入配置
```kotlin
val homeModule = module {
    single<IHomeFeature> { HomeApiService() }
    single<HomeViewModel> { HomeViewModel(get(), get(), get()) }
}
```

## ✅ 设计原则

1. **单一职责原则**: 每个功能模块只负责一个业务领域
2. **接口隔离原则**: 通过接口定义功能边界
3. **依赖倒置原则**: 依赖接口而非具体实现
4. **开闭原则**: 对扩展开放，对修改关闭

## 📝 注意事项

1. **模块独立**: 功能模块之间不应直接依赖
2. **接口优先**: 通过接口与其他模块交互
3. **数据封装**: 使用Result封装返回结果
4. **错误处理**: 统一的错误处理机制

## 🧪 测试策略

1. **单元测试**: 测试每个功能模块的独立功能
2. **集成测试**: 测试模块间的接口交互
3. **Mock测试**: 使用Mock对象测试依赖关系


