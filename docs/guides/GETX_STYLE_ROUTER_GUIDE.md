# GetX风格路由系统指南

## 🎯 概述

基于Flutter GetX库的路由实现方案，为KMP项目提供了完整的URL路由导航系统，支持参数传递、中间件、状态管理等功能。

## ✨ 核心特性

### 1. 命名路由
```kotlin
// 定义路由常量
object AppRoutes {
    const val HOME = "/home"
    const val SEARCH = "/search"
    const val SEARCH_DETAIL = "/search-detail"
    const val MESSAGE_DETAIL = "/message-detail"
}

// 使用命名路由
router.to(AppRoutes.HOME)
router.to(AppRoutes.SEARCH_DETAIL)
```

### 2. URL参数传递
```kotlin
// 支持格式: /path?param1=value1&param2=value2
val url = "/search-detail?keyword=KMP&page=2&category=tech"
router.to(url)

// 或者使用构建器
val url = "/search-detail".toUrlBuilder()
    .addString("keyword", "KMP")
    .addInt("page", 2)
    .addString("category", "tech")
    .build()
```

### 3. 类型安全的参数解析
```kotlin
val params = router.getCurrentParams()
val keyword = params.getString("keyword", "")
val page = params.getInt("page", 1)
val category = params.getStringOrNull("category")
val isActive = params.getBoolean("isActive", false)
```

### 4. 中间件支持
```kotlin
// 认证中间件
class AuthMiddleware : RouteMiddleware {
    override suspend fun redirect(route: String): String? {
        if (needAuth(route) && !isLoggedIn()) {
            return "/login"
        }
        return null
    }
}

// 注册中间件
router.registerMiddleware(AuthMiddleware())
```

### 5. 扩展函数
```kotlin
// 便捷的导航方法
router.toSearchDetail("Kotlin", 1, "programming")
router.toMessageDetail("msg_123", "conv_456")
router.toProfileDetail("user_789", "posts")
```

## 🚀 使用方法

### 1. 基础导航

```kotlin
// 导航到指定页面
router.to("/home")
router.to("/search")
router.to("/message")

// 替换当前页面
router.off("/login")

// 替换所有页面（清除历史）
router.offAll("/main")

// 返回上一页
router.back()

// 返回到指定页面
router.backTo("/home")
```

### 2. 带参数导航

```kotlin
// 方法1: 直接传递URL
router.to("/search-detail?keyword=KMP&page=2")

// 方法2: 使用扩展函数
router.toSearchDetail("KMP", 2, "tech")

// 方法3: 使用构建器
val url = "/search-detail".toUrlBuilder()
    .addString("keyword", "KMP")
    .addInt("page", 2)
    .addString("category", "tech")
    .build()
router.to(url)
```

### 3. 参数获取

```kotlin
// 获取当前页面参数
val params = router.getCurrentParams()
val keyword = params.getString("keyword")
val page = params.getInt("page", 1)

// 获取指定参数
val messageId = router.getArgument("messageId")
val userId = router.getArgument("userId", "default")
```

### 4. 状态检查

```kotlin
// 检查当前页面
val isHome = router.isCurrentPage("/home")
val isSearch = router.isCurrentPage("/search")

// 检查当前标签页
val isHomeTab = router.isCurrentTab("home")
val currentTabIndex = router.getCurrentTabIndex()

// 检查路由状态
val inAuthFlow = RouteState.isInAuthFlow(router)
val inMainFlow = RouteState.isInMainFlow(router)
val canGoBack = RouteState.canGoBack(router)
```

## 📱 实际应用场景

### 1. 搜索功能
```kotlin
// 搜索页面
router.toSearch()

// 搜索结果详情
router.toSearchDetail(
    keyword = "Kotlin Multiplatform",
    page = 1,
    category = "technology"
)

// 获取搜索参数
val searchParams = router.getCurrentParams()
val keyword = searchParams.getString("keyword")
val page = searchParams.getInt("page")
```

### 2. 消息功能
```kotlin
// 消息列表
router.toMessage()

// 消息详情
router.toMessageDetail(
    messageId = "msg_123",
    conversationId = "conv_456"
)

// 获取消息参数
val messageId = router.getArgument("messageId")
val conversationId = router.getArgument("conversationId")
```

### 3. 用户功能
```kotlin
// 个人中心
router.toProfile()

// 用户详情
router.toProfileDetail(
    userId = "user_789",
    tab = "posts"
)

// 获取用户参数
val userId = router.getArgument("userId")
val tab = router.getArgument("tab", "profile")
```

### 4. 标签页导航
```kotlin
// 导航到指定标签页
router.toTab(0) // 首页
router.toTab(1) // 搜索
router.toTab(2) // 消息
router.toTab(3) // 个人中心

// 返回到指定标签页
router.backToHome()
router.backToSearch()
router.backToMessage()
router.backToProfile()
```

## 🔧 高级功能

### 1. 自定义中间件
```kotlin
class CustomMiddleware : RouteMiddleware {
    override val priority: Int = 1
    
    override suspend fun redirect(route: String): String? {
        // 自定义重定向逻辑
        return null
    }
    
    override suspend fun canNavigate(route: String): Boolean {
        // 自定义权限检查
        return true
    }
}

// 注册中间件
router.registerMiddleware(CustomMiddleware())
```

### 2. 自定义页面
```kotlin
val customPage = GetPage(
    name = "/custom",
    page = "custom",
    middlewares = listOf("auth"),
    transition = RouteTransition.slideInRight,
    duration = 500L
)

// 注册页面
router.registerPage(customPage)
```

### 3. 路由调试
```kotlin
// 打印当前路由状态
RouteDebugger.printCurrentState(router)

// 打印路由历史
RouteDebugger.printRouteHistory(router)

// 检查当前路由
val currentRoute = router.currentRoute()
val arguments = router.arguments()
val canPop = router.canPop()
```

## 📊 与Flutter GetX对比

| 功能 | Flutter GetX | KMP GetX Style Router |
|------|-------------|----------------------|
| 命名路由 | ✅ | ✅ |
| URL参数 | ✅ | ✅ |
| 中间件 | ✅ | ✅ |
| 过渡动画 | ✅ | ✅ |
| 状态管理 | ✅ | ✅ |
| 类型安全 | ❌ | ✅ |
| 跨平台 | ❌ | ✅ |

## 🎯 最佳实践

### 1. 路由命名规范
```kotlin
// 使用常量定义路由
object AppRoutes {
    const val HOME = "/home"
    const val SEARCH_DETAIL = "/search-detail"
    const val MESSAGE_DETAIL = "/message-detail"
}

// 避免硬编码
// ❌ 错误
router.to("/search-detail")

// ✅ 正确
router.to(AppRoutes.SEARCH_DETAIL)
```

### 2. 参数验证
```kotlin
// 验证必需参数
val params = router.getCurrentParams()
val keyword = params.getString("keyword")
if (keyword.isBlank()) {
    AppLogger.e("Router", "Missing required parameter: keyword")
    return
}
```

### 3. 错误处理
```kotlin
try {
    router.to("/search-detail?keyword=KMP")
} catch (e: Exception) {
    AppLogger.e("Router", "Navigation failed", e)
    // 处理错误
}
```

### 4. 性能优化
```kotlin
// 使用扩展函数减少重复代码
router.toSearchDetail("KMP", 1, "tech")

// 而不是
val url = "/search-detail?keyword=KMP&page=1&category=tech"
router.to(url)
```

## 🚀 总结

GetX风格的路由系统为KMP项目提供了：

1. **完整的URL路由支持** - 支持参数传递和解析
2. **类型安全** - 编译时检查，减少运行时错误
3. **中间件系统** - 支持认证、日志、权限检查等
4. **扩展函数** - 提供便捷的导航方法
5. **状态管理** - 完整的路由状态跟踪
6. **跨平台兼容** - 支持Android、iOS、Desktop、Web

通过这个路由系统，您可以实现与Flutter GetX相同的开发体验，同时享受KMP的跨平台优势！
