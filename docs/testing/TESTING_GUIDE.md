# KMP项目测试指南

## 测试架构概览

本项目采用分层测试架构，包含以下测试类型：

### 1. 单元测试 (Unit Tests)
- **ViewModel测试**: 测试业务逻辑和状态管理
- **Repository测试**: 测试数据访问层
- **工具类测试**: 测试工具函数和辅助类
- **网络层测试**: 测试HTTP客户端和API服务

### 2. 集成测试 (Integration Tests)
- **端到端测试**: 测试完整的业务流程
- **数据库测试**: 测试数据持久化
- **路由测试**: 测试页面导航和参数传递

### 3. 测试框架

#### 主要依赖
```kotlin
// 测试框架
implementation("io.kotest:kotest-framework-engine:5.8.1")
implementation("io.kotest:kotest-assertions-core:5.8.1")
implementation("io.kotest:kotest-property:5.8.1")
implementation("io.kotest:kotest-runner-junit5:5.8.1")

// Mock框架
implementation("io.mockk:mockk:1.13.8")

// 协程测试
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")

// 依赖注入测试
implementation("io.insert-koin:koin-test:3.5.6")

// 数据库测试
implementation("app.cash.sqldelight:sqlite-driver:2.0.4")
```

## 测试实现示例

### 1. ViewModel测试
```kotlin
class HomeViewModelTest : TestBase() {
    
    private val mockApiService = mockk<HomeApiService>()
    private val mockLogger = mockk<AppLogger>(relaxed = true)
    
    init {
        test("加载首页数据应该成功") {
            runTest {
                // Given
                val expectedBanners = listOf(TestDataFactory.createTestBanner())
                coEvery { mockApiService.getBanners() } returns expectedBanners
                
                // When
                val viewModel = HomeViewModel(mockApiService, mockLogger)
                viewModel.loadHomeData()
                
                // Then
                viewModel.banners.value shouldBe expectedBanners
                coVerify { mockApiService.getBanners() }
            }
        }
    }
}
```

### 2. Repository测试
```kotlin
class BaseRepositoryTest : TestBase() {
    
    init {
        test("Repository应该能够获取数据") {
            runTest {
                // Given
                val expectedData = listOf(TestDataFactory.createTestBanner())
                coEvery { mockApiService.getBanners() } returns expectedData
                
                // When
                val repository = TestRepository(mockApiService, mockLogger)
                val result = repository.getData()
                
                // Then
                result shouldBe expectedData
            }
        }
    }
}
```

### 3. 工具类测试
```kotlin
class DateUtilsTest : TestBase() {
    
    init {
        test("获取当前时间戳应该返回有效值") {
            // When
            val timestamp = DateUtils.currentTimestamp()
            
            // Then
            timestamp shouldNotBe null
        }
    }
}
```

### 4. 数据库测试
```kotlin
class DatabaseTest : TestBase() {
    
    init {
        test("插入用户应该成功") {
            runTest {
                // Given
                val user = TestDataFactory.createTestUser()
                val userDao = database.getUserDao()
                
                // When
                userDao.insertUser(user)
                val retrievedUser = userDao.getUserById(user.id)
                
                // Then
                retrievedUser shouldNotBe null
            }
        }
    }
}
```

### 5. 路由测试
```kotlin
class GetXStyleRouterTest : TestBase() {
    
    init {
        test("导航到指定页面应该成功") {
            runTest {
                // When
                router.to(AppRoutes.HOME)
                
                // Then
                router.currentRoute() shouldBe AppRoutes.HOME
            }
        }
    }
}
```

## 测试最佳实践

### 1. 测试命名
- 使用描述性的测试名称
- 遵循 "Given-When-Then" 模式
- 使用中文描述测试场景

### 2. 测试数据
- 使用 `TestDataFactory` 创建测试数据
- 避免硬编码测试数据
- 使用有意义的测试数据

### 3. Mock使用
- 使用 `MockK` 进行依赖模拟
- 设置合理的默认行为
- 验证交互行为

### 4. 异步测试
- 使用 `runTest` 进行协程测试
- 使用 `TestDispatcher` 控制时间
- 避免使用 `runBlocking`

### 5. 测试隔离
- 每个测试独立运行
- 使用 `beforeEach` 和 `afterEach` 清理状态
- 避免测试间的依赖

## 运行测试

### 运行所有测试
```bash
./gradlew test
```

### 运行特定测试
```bash
./gradlew test --tests "HomeViewModelTest"
```

### 运行测试并生成报告
```bash
./gradlew test jacocoTestReport
```

## 测试覆盖率

项目配置了 JaCoCo 测试覆盖率报告，目标覆盖率：
- 行覆盖率: > 80%
- 分支覆盖率: > 70%
- 方法覆盖率: > 85%

## 持续集成

测试在 CI/CD 管道中自动运行：
1. 代码提交触发测试
2. 测试失败阻止合并
3. 生成测试报告
4. 发布覆盖率报告

## 注意事项

1. **平台兼容性**: 确保测试在所有目标平台上运行
2. **性能考虑**: 避免长时间运行的测试
3. **资源清理**: 及时释放测试资源
4. **错误处理**: 测试异常情况的处理
5. **边界条件**: 测试边界值和极端情况
