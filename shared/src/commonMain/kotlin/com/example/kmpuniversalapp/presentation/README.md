# Presentation Module (表现层模块)

## 📋 模块概述

表现层模块负责用户界面的展示和交互，包括UI组件、导航、状态管理、主题管理等。通过接口隔离具体实现，支持不同的UI框架和导航方案。

## 🏗️ 模块结构

```
presentation/
├── PresentationInterfaces.kt # 表现层接口
├── ui/                  # UI组件
│   ├── components/      # 通用组件
│   ├── screens/         # 屏幕组件
│   ├── themes/          # 主题定义
│   └── animations/      # 动画定义
├── navigation/          # 导航层
│   ├── AppNavigation.kt # 主导航
│   ├── AppScreen.kt     # 屏幕定义
│   ├── Route.kt         # 路由定义
│   └── managers/        # 导航管理器
├── state/               # 状态管理
│   ├── StateManager.kt  # 状态管理器
│   ├── StateModule.kt   # 状态Koin模块
│   └── reducers/        # 状态处理器
└── managers/            # 管理器
    ├── ThemeManager.kt  # 主题管理器
    ├── DialogManager.kt # 对话框管理器
    └── PermissionManager.kt # 权限管理器
```

## 🎯 模块职责

### 1. 接口定义 (interfaces/)
- **职责**: 定义表现层的公共接口
- **原则**: 接口隔离原则，支持多种实现
- **包含**: INavigation, IStateManager, IThemeManager, IDialogManager, IPermissionManager

### 2. UI组件 (ui/)
- **职责**: 提供可复用的UI组件
- **包含**: 通用组件、屏幕组件、主题、动画
- **特点**: 支持多平台UI框架

### 3. 导航层 (navigation/)
- **职责**: 管理应用导航和路由
- **包含**: 导航管理、屏幕定义、路由处理
- **特点**: 支持不同导航库

### 4. 状态管理 (state/)
- **职责**: 管理应用状态
- **包含**: 状态管理器、状态处理器
- **特点**: 支持不同状态管理方案

### 5. 管理器 (managers/)
- **职责**: 管理各种UI相关服务
- **包含**: 主题、对话框、权限等管理器
- **特点**: 统一管理UI相关功能

## 🔗 依赖关系

```
presentation
  ↓
features (业务功能)
  ↓
infrastructure (基础设施)
  ↓
core (核心功能)
```

## 📦 公共接口

### INavigation (导航接口)
```kotlin
interface INavigation {
    fun navigateTo(route: String, params: Map<String, Any> = emptyMap())
    fun navigateBack()
    fun canNavigateBack(): Boolean
    fun clearBackStack()
}
```

### IStateManager (状态管理接口)
```kotlin
interface IStateManager<T> {
    val state: State<T>
    fun updateState(updater: (T) -> T)
    fun resetState()
}
```

### IThemeManager (主题管理接口)
```kotlin
interface IThemeManager {
    fun isDarkMode(): Boolean
    fun toggleTheme()
    fun setTheme(isDark: Boolean)
    fun getCurrentTheme(): ThemeType
}
```

### IDialogManager (对话框管理接口)
```kotlin
interface IDialogManager {
    fun showDialog(
        title: String,
        message: String,
        positiveText: String = "确定",
        negativeText: String? = null,
        onPositive: (() -> Unit)? = null,
        onNegative: (() -> Unit)? = null
    )
    fun showLoadingDialog(message: String = "加载中...")
    fun hideLoadingDialog()
    fun showToast(message: String, duration: ToastDuration = ToastDuration.SHORT)
}
```

## 🚀 使用方式

### 导入表现层模块
```kotlin
import com.example.kmpuniversalapp.presentation.*
import com.example.kmpuniversalapp.presentation.interfaces.*
```

### 使用导航服务
```kotlin
class HomeViewModel(
    private val navigation: INavigation,
    private val logger: ILogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    
    fun navigateToSearch() {
        navigation.navigateTo("search")
    }
    
    fun navigateToProfile(userId: String) {
        navigation.navigateTo("profile", mapOf("userId" to userId))
    }
}
```

### 使用状态管理
```kotlin
class SearchViewModel(
    private val stateManager: IStateManager<SearchState>,
    private val searchFeature: ISearchFeature,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    
    fun updateSearchKeyword(keyword: String) {
        stateManager.updateState { currentState ->
            currentState.copy(keyword = keyword)
        }
    }
}
```

### 使用主题管理
```kotlin
@Composable
fun AppTheme(
    themeManager: IThemeManager = LocalThemeManager.current,
    content: @Composable () -> Unit
) {
    val isDark = themeManager.isDarkMode()
    MaterialTheme(
        colorScheme = if (isDark) darkColorScheme() else lightColorScheme()
    ) {
        content()
    }
}
```

## ✅ 设计原则

1. **接口隔离原则**: 每个接口职责单一
2. **依赖倒置原则**: 依赖抽象而非具体实现
3. **开闭原则**: 对扩展开放，对修改关闭
4. **单一职责原则**: 每个组件只有一个变化的理由

## 📝 注意事项

1. **平台兼容**: 支持多平台UI框架
2. **性能优化**: 考虑UI渲染性能
3. **用户体验**: 提供流畅的交互体验
4. **可访问性**: 支持无障碍访问

## 🧪 测试策略

1. **UI测试**: 测试UI组件的行为
2. **导航测试**: 测试导航逻辑
3. **状态测试**: 测试状态管理
4. **集成测试**: 测试表现层与业务层的集成


