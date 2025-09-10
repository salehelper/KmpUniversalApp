# KMP Universal App 快速开始指南

## 🎯 项目概述

这是一个基于 Kotlin Multiplatform 的通用应用脚手架，支持以下平台：
- 📱 Android
- 🍎 iOS  
- 🖥️ Desktop (Windows, macOS, Linux)
- 🌐 Web (WASM)

## 🚀 快速运行

### 方法一：使用运行脚本（推荐）
```bash
# 运行所有平台
./run.sh all

# 运行特定平台
./run.sh android    # Android
./run.sh desktop    # Desktop
./run.sh web        # Web
./run.sh ios        # iOS (需要Xcode)
```

### 方法二：使用Gradle命令
```bash
# 同步项目
./gradlew build

# 运行各平台
./gradlew :composeApp:runDebugAndroid      # Android
./gradlew :composeApp:runDesktop           # Desktop
./gradlew :composeApp:jsBrowserDevelopmentRun  # Web
```

## 📁 项目结构

```
KmpUniversalApp/
├── composeApp/              # 主应用模块
│   ├── src/
│   │   ├── commonMain/      # 通用代码（UI、业务逻辑）
│   │   ├── androidMain/     # Android特定代码
│   │   ├── iosMain/         # iOS特定代码
│   │   ├── jvmMain/         # Desktop特定代码
│   │   └── wasmJsMain/      # Web特定代码
│   └── build.gradle.kts
├── shared/                  # 共享业务逻辑模块
│   ├── src/
│   │   ├── commonMain/      # 通用业务逻辑
│   │   ├── androidMain/     # Android特定实现
│   │   ├── iosMain/         # iOS特定实现
│   │   ├── jvmMain/         # Desktop特定实现
│   │   └── wasmJsMain/      # Web特定实现
│   └── build.gradle.kts
├── iosApp/                  # iOS项目文件
├── server/                  # Ktor后端服务
└── gradle/                  # Gradle配置
```

## 🛠️ 开发指南

### 添加新功能

1. **添加数据模型** (shared模块)
```kotlin
// shared/src/commonMain/kotlin/data/YourModel.kt
@Serializable
data class YourModel(
    val id: Int,
    val name: String
)
```

2. **添加网络服务** (shared模块)
```kotlin
// shared/src/commonMain/kotlin/network/YourApiService.kt
class YourApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { /* 配置 */ })
        }
    }
    
    suspend fun getData(): List<YourModel> {
        // 实现API调用
    }
}
```

3. **添加ViewModel** (shared模块)
```kotlin
// shared/src/commonMain/kotlin/viewmodel/YourViewModel.kt
class YourViewModel {
    private val _data = MutableStateFlow<List<YourModel>>(emptyList())
    val data: StateFlow<List<YourModel>> = _data.asStateFlow()
    
    suspend fun loadData() {
        // 实现数据加载逻辑
    }
}
```

4. **添加UI组件** (composeApp模块)
```kotlin
// composeApp/src/commonMain/kotlin/ui/YourScreen.kt
@Composable
fun YourScreen(viewModel: YourViewModel) {
    val data by viewModel.data.collectAsState()
    
    LazyColumn {
        items(data) { item ->
            YourItem(item = item)
        }
    }
}
```

### 平台特定实现

使用 `expect/actual` 机制处理平台差异：

```kotlin
// commonMain
expect class PlatformSpecificClass {
    fun getPlatformInfo(): String
}

// androidMain
actual class PlatformSpecificClass {
    actual fun getPlatformInfo(): String = "Android"
}

// iosMain  
actual class PlatformSpecificClass {
    actual fun getPlatformInfo(): String = "iOS"
}
```

## 🔧 配置说明

### 依赖管理
- 使用 `gradle/libs.versions.toml` 管理版本
- 在 `build.gradle.kts` 中引用依赖

### 构建配置
- Android: 最低SDK 24，目标SDK 36
- iOS: 最低版本 14.1
- Desktop: 支持 Windows, macOS, Linux
- Web: 使用 WASM 技术

## 🐛 常见问题

### 1. 构建失败
```bash
# 清理项目
./gradlew clean
./gradlew build
```

### 2. iOS构建问题
```bash
# 安装CocoaPods依赖
cd iosApp
pod install
cd ..
```

### 3. Web应用无法访问
- 确保端口8080未被占用
- 检查浏览器控制台错误信息

## 📚 学习资源

- [Kotlin Multiplatform官方文档](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform文档](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor文档](https://ktor.io/)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

## 🤝 贡献

1. Fork 项目
2. 创建功能分支
3. 提交更改
4. 创建 Pull Request

## 📄 许可证

MIT License
