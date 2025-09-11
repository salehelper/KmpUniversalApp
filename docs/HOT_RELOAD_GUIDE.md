# Compose Hot Reload 热加载指南

## 功能概述

Compose Hot Reload 是 JetBrains 为 Compose Multiplatform 开发的热加载功能，，。

## 前置条件

- Android Studio 或 IntelliJ IDEA
- Kotlin Multiplatform 项目
- Compose Multiplatform 1.7.0+
- compose-hot-reload 插件

## 配置说明

### 1. 插件配置

项目已配置 `compose-hot-reload` 插件：

```kotlin
// composeApp/build.gradle.kts
plugins {
    alias(libs.plugins.composeHotReload)
}
```

### 2. 版本信息

```toml
# gradle/libs.versions.toml
composeHotReload = "1.0.0-beta06"
```

## 使用方法

### 1. 启动热加载模式

#### Android 平台
```bash
# 启动 Android 应用并启用热加载
./gradlew :composeApp:installDebug
```

#### iOS 平台
```bash
# 启动 iOS 模拟器
./gradlew :composeApp:assembleXCFramework
```

### 2. 开发流程

1. **启动应用**：运行上述命令启动应用
2. **修改代码**：在 IDE 中修改 Compose 代码
3. **自动刷新**：保存文件后，应用会自动刷新显示更改
4. **查看日志**：在控制台查看热加载状态

### 3. 支持的热加载场景

✅ **支持热加载**：
- Compose UI 组件修改
- 状态变量更改
- 样式和布局调整
- 颜色、字体、尺寸修改
- 简单的业务逻辑修改

❌ **不支持热加载**：
- 新增或删除函数
- 修改函数签名
- 添加新的依赖
- 修改构建配置
- 平台特定代码修改

## 配置选项

### 1. 热加载端口配置

```kotlin
// composeApp/build.gradle.kts
compose {
    hotReload {
        // 可选：自定义端口
        port = 8080
    }
}
```

### 2. 日志级别配置

```kotlin
// 在应用启动时设置
System.setProperty("compose.hot.reload.log.level", "DEBUG")
```

## 📱 平台特定说明

### Android
- 需要 Android 7.0 (API 24) 或更高版本
- 支持真机和模拟器
- 建议使用 Android Studio 的预览功能

### iOS
- 支持 iOS 模拟器和真机
- 需要 Xcode 12.0 或更高版本
- 建议使用 Xcode 预览功能

## 故障排除

### 常见问题

1. **热加载不工作**
   - 检查插件是否正确应用
   - 确认应用是否在调试模式运行
   - 重启应用和 IDE

2. **更改不生效**
   - 检查修改是否在支持范围内
   - 尝试手动刷新应用
   - 查看控制台错误信息

3. **性能问题**
   - 减少热加载频率
   - 关闭不必要的调试功能
   - 检查设备性能

### 调试技巧

1. **查看热加载日志**
   ```bash
   # 启用详细日志
   ./gradlew :composeApp:installDebug --info
   ```

2. **手动触发刷新**
   - 在应用中摇动设备（Android）
   - 使用快捷键 Cmd+R (iOS)

3. **重置热加载状态**
   - 停止应用
   - 清理构建缓存
   - 重新启动应用

## 📊 性能优化

### 1. 减少热加载时间
- 避免大型 UI 组件修改
- 使用 `@Stable` 和 `@Immutable` 注解
- 合理使用 `remember` 和 `mutableStateOf`

### 2. 内存管理
- 及时释放不需要的资源
- 避免在热加载过程中创建大量对象
- 使用 `DisposableEffect` 管理副作用

## 🔍 最佳实践

1. **代码组织**
   - 将 UI 和业务逻辑分离
   - 使用 ViewModel 管理状态
   - 合理使用 Compose 的组件化

2. **状态管理**
   - 使用 `remember` 缓存计算结果
   - 避免在 `@Composable` 函数中执行耗时操作
   - 合理使用 `LaunchedEffect` 和 `SideEffect`

3. **调试技巧**
   - 使用 `Log.d` 输出调试信息
   - 利用 Compose 的预览功能
   - 定期检查内存使用情况

## 相关资源

- [Compose Multiplatform 官方文档](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Compose Hot Reload 插件文档](https://github.com/JetBrains/compose-multiplatform)
- [Kotlin Multiplatform 开发指南](https://kotlinlang.org/docs/multiplatform.html)

## 🎉 总结

Compose Hot Reload 是提升开发效率的强大工具，通过合理配置和使用，可以显著加快 Compose Multiplatform 应用的开发速度。记住在开发过程中保持代码的简洁和模块化，这样热加载的效果会更好。
