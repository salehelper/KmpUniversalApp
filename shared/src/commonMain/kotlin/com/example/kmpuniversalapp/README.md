# 模块化架构说明

## 📁 目录结构

```
modules/
├── core/                    # 核心模块
│   ├── base/               # 基础类
│   │   ├── BaseViewModel.kt
│   │   ├── Result.kt
│   │   └── AppConfig.kt
│   ├── utils/              # 工具类
│   │   ├── log/           # 日志工具
│   │   ├── time/          # 时间工具
│   │   ├── storage/       # 存储工具
│   │   └── utils/         # 通用工具
│   └── di/                # 依赖注入
│       ├── AppModule.kt
│       ├── KoinInitializer.kt
│       └── ViewModelFactory.kt
├── features/               # 功能模块
│   ├── home/              # 首页功能
│   │   ├── HomeViewModel.kt
│   │   ├── HomeApiService.kt
│   │   ├── BannerModel.kt
│   │   └── DynamicModel.kt
│   ├── search/            # 搜索功能
│   │   ├── SearchViewModel.kt
│   │   ├── SearchApiService.kt
│   │   └── SearchResultModel.kt
│   ├── message/           # 消息功能
│   │   ├── MessageViewModel.kt
│   │   └── MessageModel.kt
│   ├── profile/           # 个人资料功能
│   │   ├── ProfileViewModel.kt
│   │   └── ProfileModel.kt
│   └── account/           # 账户功能
│       ├── AccountViewModel.kt
│       ├── AccountModel.kt
│       ├── User.kt
│       └── UserViewModel.kt
├── infrastructure/         # 基础设施模块
│   ├── network/           # 网络层
│   │   └── HttpClient.kt
│   ├── database/          # 数据库层 (已移除SQLDelight)
│   └── storage/           # 存储层
│       ├── DataStoreManager.kt
│       └── StorageManager.kt
└── presentation/          # 表现层模块
    ├── ui/               # UI组件
    │   ├── home/         # 首页UI
    │   ├── search/       # 搜索UI
    │   ├── message/      # 消息UI
    │   ├── profile/      # 个人资料UI
    │   ├── MainTabView.kt
    │   └── CommonWidgets.kt
    └── navigation/       # 导航层
        ├── AppComponent.kt
        ├── AppNavigation.kt
        ├── AppScreen.kt
        └── Route.kt
```

## 🏗️ 模块职责

### 1. Core模块 (核心模块)
- **base**: 提供基础类，如BaseViewModel、Result等
- **utils**: 提供工具类，如日志、时间、存储等
- **di**: 提供依赖注入配置

### 2. Features模块 (功能模块)
- **home**: 首页相关功能
- **search**: 搜索相关功能
- **message**: 消息相关功能
- **profile**: 个人资料相关功能
- **account**: 账户相关功能

### 3. Infrastructure模块 (基础设施模块)
- **network**: 网络请求、HTTP客户端
- **database**: 数据库相关
- **storage**: 存储相关

### 4. Presentation模块 (表现层模块)
- **ui**: UI组件和视图
- **navigation**: 导航和路由

## 🔗 模块依赖关系

```
presentation
    ↓
features + core
    ↓
infrastructure + core
    ↓
core
```

- **core**: 不依赖任何其他模块
- **infrastructure**: 只依赖core模块
- **features**: 依赖core和infrastructure模块
- **presentation**: 依赖core、infrastructure和features模块

## 📦 模块导出

每个模块都有对应的`package.kt`文件，用于导出模块的公共接口：

- `modules/core/package.kt` - 导出核心模块
- `modules/features/package.kt` - 导出功能模块
- `modules/infrastructure/package.kt` - 导出基础设施模块
- `modules/presentation/package.kt` - 导出表现层模块

## 🚀 使用方式

### 导入模块
```kotlin
// 导入核心模块
import com.example.kmpuniversalapp.modules.core.*

// 导入功能模块
import com.example.kmpuniversalapp.modules.features.home.*

// 导入基础设施模块
import com.example.kmpuniversalapp.modules.infrastructure.network.*

// 导入表现层模块
import com.example.kmpuniversalapp.modules.presentation.ui.*
```

### 创建ViewModel
```kotlin
class HomeViewModel(
    private val homeApiService: HomeApiService,
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 实现业务逻辑
}
```

## ✅ 模块化优势

1. **清晰的职责分离**: 每个模块有明确的职责
2. **降低耦合度**: 模块间依赖关系清晰
3. **提高可维护性**: 修改某个功能只影响对应模块
4. **便于测试**: 可以独立测试每个模块
5. **支持团队协作**: 不同开发者可以负责不同模块
6. **代码复用**: 核心模块可以被多个功能模块复用

## 🔄 迁移指南

### 从旧结构迁移到新结构

1. **更新包名**: 所有文件的包名需要更新为新的模块化包名
2. **更新导入**: 所有import语句需要更新为新的模块路径
3. **更新依赖注入**: DI配置需要更新为新的模块结构
4. **测试验证**: 确保所有功能在新结构下正常工作

### 包名映射

| 旧包名 | 新包名 |
|--------|--------|
| `com.example.kmpuniversalapp.core` | `com.example.kmpuniversalapp.modules.core.base` |
| `com.example.kmpuniversalapp.home` | `com.example.kmpuniversalapp.modules.features.home` |
| `com.example.kmpuniversalapp.libs.utils` | `com.example.kmpuniversalapp.modules.core.utils` |
| `com.example.kmpuniversalapp.libs.http` | `com.example.kmpuniversalapp.modules.infrastructure.network` |
| `com.example.kmpuniversalapp.ui` | `com.example.kmpuniversalapp.modules.presentation.ui` |

## 📝 注意事项

1. **循环依赖**: 避免模块间出现循环依赖
2. **公共接口**: 只通过模块的公共接口进行交互
3. **版本兼容**: 保持模块接口的向后兼容性
4. **文档更新**: 及时更新相关文档和注释
