# KMP Universal App

一个基于 Kotlin Multiplatform (KMP) 的跨平台通用应用，支持 Android、iOS、Desktop 和 Web 平台。

## 🚀 快速开始

### 环境要求
- JDK 17+
- Android Studio 或 IntelliJ IDEA
- Xcode (iOS 开发)
- Node.js (Web 开发)

### 构建和运行

```bash
# 构建所有平台
./gradlew build

# 运行 Android
./gradlew :androidApp:assembleDebug

# 运行 iOS
./gradlew :iosApp:assembleXCFramework

# 运行 Desktop
./gradlew :desktopApp:run

# 运行 Web
./gradlew :webApp:jsBrowserDevelopmentRun
```

## 📱 支持平台

- ✅ **Android** - 原生 Android 应用
- ✅ **iOS** - 原生 iOS 应用  
- ✅ **Desktop** - Windows、macOS、Linux 桌面应用
- ✅ **Web** - 现代浏览器 Web 应用

## 🏗️ 技术架构

### 核心技术栈
- **Kotlin Multiplatform** - 跨平台代码共享
- **Compose Multiplatform** - 跨平台 UI 框架
- **MVVM 架构** - 清晰的代码组织
- **Koin** - 依赖注入
- **Ktor** - 网络请求
- **SQLDelight** - 类型安全数据库
- **Decompose** - 导航管理

### 模块结构
```
shared/
├── commonMain/
│   ├── main/           # 主模块
│   ├── home/           # 首页模块
│   ├── search/         # 搜索模块
│   ├── message/        # 消息模块
│   ├── profile/        # 个人资料模块
│   ├── account/        # 账户模块
│   ├── network/        # 网络模块
│   ├── widgets/        # 通用组件
│   ├── common/         # 通用工具
│   ├── core/           # 核心功能
│   └── libs/           # 第三方库封装
```

## 📚 文档

详细的文档请查看 [docs/](docs/) 目录：

- [📖 文档中心](docs/README.md) - 完整的文档索引
- [🚀 快速开始](docs/QUICK_START.md) - 详细的上手指南
- [🏗️ 架构设计](docs/architecture/) - 架构相关文档
- [🔄 迁移指南](docs/migration/) - Flutter 到 KMP 迁移
- [📚 开发指南](docs/guides/) - 开发相关指南
- [📦 第三方库](docs/libraries/) - 库使用说明
- [🧪 测试指南](docs/testing/) - 测试策略

## 🛠️ 开发

### 项目结构
```
KmpUniversalApp/
├── androidApp/         # Android 应用
├── iosApp/            # iOS 应用
├── desktopApp/        # Desktop 应用
├── webApp/            # Web 应用
├── shared/            # 共享代码
├── docs/              # 项目文档
└── build.gradle.kts   # 构建配置
```

### 代码规范
- 遵循 Kotlin 官方编码规范
- 使用 Ktlint 进行代码格式化
- 模块化设计，职责分离
- 完整的单元测试覆盖

## 🤝 贡献

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 🙏 致谢

感谢以下开源项目的支持：
- [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Koin](https://insert-koin.io/)
- [Ktor](https://ktor.io/)
- [SQLDelight](https://cashapp.github.io/sqldelight/)
- [Decompose](https://arkivanov.github.io/Decompose/)

---

*最后更新：2024年9月*
