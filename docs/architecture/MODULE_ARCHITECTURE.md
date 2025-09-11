# KMP Universal App 模块架构图

## 整体架构

```
┌─────────────────────────────────────────────────────────────┐
│                    KMP Universal App                        │
├─────────────────────────────────────────────────────────────┤
│  composeApp (UI Layer)                                      │
│  ├── MainTabView.kt                                         │
│  ├── HomeView.kt                                            │
│  ├── SearchView.kt                                          │
│  ├── MessageView.kt                                         │
│  └── ProfileView.kt                                         │
├─────────────────────────────────────────────────────────────┤
│  shared (Business Logic Layer)                              │
│  ├── main/                  # 主模块                        │
│  ├── home/                  # 首页模块                      │
│  ├── search/                # 搜索模块                      │
│  ├── message/               # 消息模块                      │
│  ├── profile/               # 个人中心模块                  │
│  ├── account/               # 账户模块                      │
│  ├── network/               # 网络模块                      │
│  ├── widgets/               # 通用组件模块                  │
│  ├── common/                # 通用模块                      │
│  ├── core/                  # 核心模块                      │
│  └── libs/                  # 基础库模块                    │
│      ├── base/              # 基础库                        │
│      ├── http/              # HTTP库                        │
│      ├── web/               # Web库                         │
│      └── utils/             # 工具库                        │
│          ├── storage/       # 存储工具                      │
│          ├── utils/         # 通用工具                      │
│          ├── log/           # 日志工具                      │
│          └── notification/  # 通知工具                      │
└─────────────────────────────────────────────────────────────┘
```

## 📊 模块依赖关系图

```
                    ┌─────────────┐
                    │    main     │
                    └─────┬───────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
   ┌─────────┐       ┌─────────┐       ┌─────────┐
   │  home   │       │ search  │       │ message │
   └────┬────┘       └────┬────┘       └────┬────┘
        │                 │                 │
        ▼                 ▼                 ▼
   ┌─────────┐       ┌─────────┐       ┌─────────┐
   │ profile │       │ account │       │ network │
   └────┬────┘       └────┬────┘       └────┬────┘
        │                 │                 │
        └─────────────────┼─────────────────┘
                          │
                          ▼
                    ┌─────────────┐
                    │    core     │
                    └─────┬───────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
   ┌─────────┐       ┌─────────┐       ┌─────────┐
   │ widgets │       │ common  │       │  libs   │
   └─────────┘       └─────────┘       └────┬────┘
                                             │
                    ┌─────────────────────────┼─────────────────────────┐
                    │                         │                         │
                    ▼                         ▼                         ▼
               ┌─────────┐               ┌─────────┐               ┌─────────┐
               │  base   │               │  http   │               │  utils  │
               └─────────┘               └─────────┘               └────┬────┘
                                                                        │
                                                           ┌─────────────┼─────────────┐
                                                           │             │             │
                                                           ▼             ▼             ▼
                                                      ┌─────────┐ ┌─────────┐ ┌─────────┐
                                                      │storage  │ │ utils   │ │   log   │
                                                      └─────────┘ └─────────┘ └─────────┘
```

## 数据流向图

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    UI       │    │  ViewModel  │    │ Repository  │
│  (Compose)  │◄──►│ (StateFlow) │◄──►│   (Ktor)    │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       │                   │                   │
       ▼                   ▼                   ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Widgets   │    │    Core     │    │    Libs     │
│ (Components)│    │ (BaseVM)    │    │  (Utils)    │
└─────────────┘    └─────────────┘    └─────────────┘
```

## 📱 平台支持架构

```
                    ┌─────────────────┐
                    │   composeApp    │
                    │  (Common UI)    │
                    └─────────┬───────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│  androidApp │       │   iosApp    │       │  desktopApp │
│   (Android) │       │    (iOS)    │       │ (Desktop)   │
└─────────────┘       └─────────────┘       └─────────────┘
        │                     │                     │
        └─────────────────────┼─────────────────────┘
                              │
                    ┌─────────┴───────┐
                    │     webApp      │
                    │     (Web)       │
                    └─────────────────┘
```

## 模块职责矩阵

| 模块 | 职责 | 核心类 | 依赖关系 |
|------|------|--------|----------|
| **main** | 主界面导航 | MainTabController | home, search, message, profile |
| **home** | 首页功能 | HomeViewModel, BannerModel | network, core, libs/base |
| **search** | 搜索功能 | SearchViewModel, SearchResultModel | network, core, libs/base |
| **message** | 消息功能 | MessageViewModel, MessageModel | core, libs/base |
| **profile** | 个人中心 | ProfileViewModel, ProfileModel | account, core, libs/base |
| **account** | 账户管理 | AccountViewModel, UserModel | network, core, libs/base |
| **network** | 网络请求 | ApiService | core, libs/http |
| **widgets** | UI组件 | CommonWidgets | - |
| **common** | 通用常量 | Constants | - |
| **core** | 核心功能 | BaseViewModel, AppConfig | libs/utils |
| **libs/base** | 基础库 | BaseRepository | core, libs/http |
| **libs/http** | HTTP库 | HttpClient | core |
| **libs/utils** | 工具库 | Logger, StorageManager | core |

## 开发工作流

### 1. 新功能开发
```
1. 在对应业务模块创建数据模型
2. 在network模块创建API服务
3. 在业务模块创建ViewModel
4. 在composeApp创建UI组件
5. 集成到主界面
```

### 2. 模块间通信
```
UI Layer → ViewModel → Repository → API Service
    ↓         ↓           ↓
  Widgets   Core      Libs/Utils
```

### 3. 状态管理
```
StateFlow (ViewModel) → Compose State → UI Update
    ↓
  Repository → API → Data
```

## 最佳实践

### 1. 模块设计原则
- **单一职责**: 每个模块只负责一个业务领域
- **低耦合**: 模块间通过接口通信
- **高内聚**: 相关功能放在同一模块
- **可测试**: 每个模块都可以独立测试

### 2. 代码组织
- **按功能分组**: 相关文件放在同一模块
- **统一命名**: 遵循Kotlin命名规范
- **文档完善**: 每个模块都有说明文档
- **版本管理**: 使用语义化版本号

### 3. 依赖管理
- **最小依赖**: 只依赖必要的模块
- **接口隔离**: 使用接口定义依赖关系
- **循环依赖**: 避免模块间循环依赖
- **版本控制**: 统一管理依赖版本

这种模块化的架构设计使得KMP Universal App具有很好的可维护性、可扩展性和可测试性，为后续的功能开发和团队协作提供了坚实的基础。
