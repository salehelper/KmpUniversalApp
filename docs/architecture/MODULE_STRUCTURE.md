# KMP Universal App 模块结构

## 📁 项目结构

```
shared/src/commonMain/kotlin/com/example/kmpuniversalapp/
├── main/                           # 主模块
│   ├── MainTabController.kt        # 主标签页控制器
│   └── MainModule.kt               # 模块导出
│
├── home/                           # 首页模块
│   ├── BannerModel.kt              # Banner数据模型
│   ├── DynamicModel.kt             # 动态数据模型
│   ├── HomeViewModel.kt            # 首页ViewModel
│   ├── HomeApiService.kt           # 首页API服务
│   └── HomeModule.kt               # 模块导出
│
├── search/                         # 搜索模块
│   ├── SearchResultModel.kt        # 搜索结果数据模型
│   ├── SearchViewModel.kt          # 搜索ViewModel
│   ├── SearchApiService.kt         # 搜索API服务
│   └── SearchModule.kt             # 模块导出
│
├── message/                        # 消息模块
│   ├── MessageModel.kt             # 消息数据模型
│   ├── MessageViewModel.kt         # 消息ViewModel
│   └── MessageModule.kt            # 模块导出
│
├── profile/                        # 个人中心模块
│   ├── ProfileModel.kt             # 个人资料数据模型
│   ├── ProfileViewModel.kt         # 个人中心ViewModel
│   └── ProfileModule.kt            # 模块导出
│
├── account/                        # 账户模块
│   ├── User.kt                     # 用户数据模型
│   ├── AccountModel.kt             # 账户相关数据模型
│   ├── UserViewModel.kt            # 用户ViewModel
│   ├── AccountViewModel.kt         # 账户ViewModel
│   └── AccountModule.kt            # 模块导出
│
├── network/                        # 网络模块
│   └── ApiService.kt               # 通用API服务
│
├── widgets/                        # 通用组件模块
│   ├── CommonWidgets.kt            # 通用UI组件
│   └── WidgetsModule.kt            # 模块导出
│
├── common/                         # 通用模块
│   ├── Constants.kt                # 通用常量
│   └── CommonModule.kt             # 模块导出
│
├── core/                           # 核心模块
│   ├── BaseViewModel.kt            # 基础ViewModel
│   ├── AppConfig.kt                # 应用配置
│   ├── Result.kt                   # 结果封装
│   └── CoreModule.kt               # 模块导出
│
└── libs/                           # 基础库模块
    ├── base/                       # 基础库
    │   └── BaseRepository.kt       # 基础Repository
    │
    ├── http/                       # HTTP库
    │   └── HttpClient.kt           # HTTP客户端配置
    │
    ├── web/                        # Web相关库
    │   └── WebModule.kt            # Web模块
    │
    └── utils/                      # 工具库
        ├── storage/                # 存储工具
        │   └── StorageManager.kt   # 存储管理器
        │
        ├── utils/                  # 通用工具
        │   └── DateUtils.kt        # 日期时间工具
        │
        ├── log/                    # 日志工具
        │   └── Logger.kt           # 日志管理器
        │
        └── notification/           # 通知工具
            └── NotificationManager.kt  # 通知管理器
```

## 🏗️ 模块职责

### 业务模块 (Business Modules)

#### 1. main - 主模块
- **职责**: 管理应用主界面和导航
- **核心类**: `MainTabController`
- **功能**: 标签页切换、页面状态管理

#### 2. home - 首页模块
- **职责**: 首页相关功能和数据
- **核心类**: `HomeViewModel`, `BannerModel`, `DynamicModel`
- **功能**: 统计数据展示、Banner轮播、动态列表

#### 3. search - 搜索模块
- **职责**: 搜索相关功能和数据
- **核心类**: `SearchViewModel`, `SearchResultModel`
- **功能**: 搜索建议、历史记录、搜索结果展示

#### 4. message - 消息模块
- **职责**: 消息相关功能和数据
- **核心类**: `MessageViewModel`, `MessageModel`
- **功能**: 消息列表、消息状态管理

#### 5. profile - 个人中心模块
- **职责**: 个人中心相关功能和数据
- **核心类**: `ProfileViewModel`, `ProfileModel`
- **功能**: 用户信息展示、设置管理

#### 6. account - 账户模块
- **职责**: 账户相关功能和数据
- **核心类**: `AccountViewModel`, `UserModel`
- **功能**: 登录、注册、用户管理

### 基础模块 (Foundation Modules)

#### 7. network - 网络模块
- **职责**: 网络请求相关
- **核心类**: `ApiService`
- **功能**: API接口定义、网络请求封装

#### 8. widgets - 通用组件模块
- **职责**: 可复用的UI组件
- **核心类**: `CommonWidgets`
- **功能**: 加载指示器、错误卡片、空状态等

#### 9. common - 通用模块
- **职责**: 通用常量和工具
- **核心类**: `Constants`
- **功能**: 常量定义、通用工具函数

#### 10. core - 核心模块
- **职责**: 核心基础功能
- **核心类**: `BaseViewModel`, `AppConfig`, `Result`
- **功能**: 基础架构、应用配置、结果封装

### 基础库模块 (Library Modules)

#### 11. libs/base - 基础库
- **职责**: 基础数据访问层
- **核心类**: `BaseRepository`
- **功能**: 通用Repository功能、分页处理

#### 12. libs/http - HTTP库
- **职责**: HTTP客户端配置
- **核心类**: `HttpClient`
- **功能**: 网络客户端配置、请求拦截

#### 13. libs/web - Web库
- **职责**: Web相关功能
- **功能**: Web特定功能实现

#### 14. libs/utils - 工具库
- **职责**: 各种工具类
- **子模块**:
  - `storage`: 存储管理
  - `utils`: 通用工具
  - `log`: 日志管理
  - `notification`: 通知管理

## 🔄 模块依赖关系

```
main
├── home
├── search
├── message
├── profile
└── account

home
├── network
├── core
└── libs/base

search
├── network
├── core
└── libs/base

message
├── core
└── libs/base

profile
├── account
├── core
└── libs/base

account
├── network
├── core
└── libs/base

network
├── core
└── libs/http

core
└── libs/utils

libs/base
├── core
└── libs/http

libs/http
└── core

libs/utils
└── core
```

## 📋 模块开发规范

### 1. 命名规范
- **模块名**: 小写字母，使用下划线分隔
- **类名**: 大驼峰命名法 (PascalCase)
- **文件名**: 与类名保持一致
- **包名**: 小写字母，使用点分隔

### 2. 文件组织
- 每个模块包含对应的数据模型、ViewModel、API服务
- 每个模块都有对应的Module.kt文件用于导出
- 相关功能放在同一个模块下

### 3. 依赖管理
- 业务模块之间避免直接依赖
- 通过core模块和libs模块共享基础功能
- 使用接口隔离具体实现

### 4. 状态管理
- 使用StateFlow进行状态管理
- 继承BaseViewModel获得通用功能
- 使用Result封装异步操作结果

### 5. 错误处理
- 统一的错误处理机制
- 使用Logger记录错误信息
- 使用NotificationManager显示用户提示

## 🚀 使用示例

### 在ViewModel中使用其他模块
```kotlin
class HomeViewModel : BaseViewModel() {
    private val homeApiService = HomeApiService()
    private val logger = Logger
    
    suspend fun loadData() {
        try {
            setLoading(true)
            val result = homeApiService.getBanners()
            // 处理结果
        } catch (e: Exception) {
            handleException(e, "加载数据失败")
        } finally {
            setLoading(false)
        }
    }
}
```

### 在UI中使用通用组件
```kotlin
@Composable
fun HomeView() {
    val viewModel = remember { HomeViewModel() }
    val isLoading by viewModel.isLoading.collectAsState()
    
    if (isLoading) {
        LoadingIndicator(message = "加载首页数据...")
    } else {
        // 显示内容
    }
}
```

这种模块化的结构使得代码更加清晰、易于维护和扩展，每个模块都有明确的职责和边界。
