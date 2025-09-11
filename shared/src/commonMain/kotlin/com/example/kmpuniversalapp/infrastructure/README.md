# Infrastructure Module (基础设施模块)

## 📋 模块概述

基础设施模块提供应用的技术基础设施，包括网络、存储、数据库、缓存等技术服务。通过接口隔离具体实现，支持不同技术方案。

## 🏗️ 模块结构

```
infrastructure/
├── InfrastructureInterfaces.kt # 基础设施接口
├── network/             # 网络层
│   ├── HttpClient.kt    # HTTP客户端
│   ├── NetworkModule.kt # 网络Koin模块
│   └── interceptors/    # 网络拦截器
├── storage/             # 存储层
│   ├── StorageManager.kt # 存储管理器
│   ├── DataStoreManager.kt # DataStore管理器
│   ├── StorageModule.kt # 存储Koin模块
│   └── implementations/ # 存储实现
├── database/            # 数据库层 (已移除SQLDelight)
│   ├── entities/        # 数据库实体
│   └── migrations/      # 数据库迁移
└── cache/               # 缓存层
    ├── CacheManager.kt  # 缓存管理器
    ├── CacheModule.kt   # 缓存Koin模块
    └── strategies/      # 缓存策略
```

## 🎯 模块职责

### 1. 接口定义 (interfaces/)
- **职责**: 定义基础设施的公共接口
- **原则**: 接口隔离原则，支持多种实现
- **包含**: IDatabase, ICache, IFileStorage, INetworkMonitor, IConfigManager

### 2. 网络层 (network/)
- **职责**: 处理网络请求和响应
- **包含**: HTTP客户端、拦截器、网络监控
- **特点**: 支持不同网络库实现

### 3. 存储层 (storage/)
- **职责**: 处理数据持久化
- **包含**: 本地存储、DataStore、文件存储
- **特点**: 支持多种存储方式

### 4. 数据库层 (database/)
- **职责**: 处理结构化数据存储
- **包含**: 数据库操作、实体定义、迁移管理
- **特点**: 支持不同数据库实现

### 5. 缓存层 (cache/)
- **职责**: 处理数据缓存
- **包含**: 内存缓存、磁盘缓存、缓存策略
- **特点**: 支持多种缓存策略

## 🔗 依赖关系

```
infrastructure
  ↓
core (基础功能)
```

## 📦 公共接口

### IDatabase (数据库接口)
```kotlin
interface IDatabase {
    suspend fun <T> query(sql: String, mapper: (Map<String, Any?>) -> T): List<T>
    suspend fun execute(sql: String, params: List<Any?> = emptyList()): Int
    suspend fun transaction(block: suspend () -> Unit)
    suspend fun close()
}
```

### ICache (缓存接口)
```kotlin
interface ICache {
    suspend fun put(key: String, value: Any, ttl: Long = 0)
    suspend fun get(key: String): Any?
    suspend fun remove(key: String)
    suspend fun clear()
    suspend fun contains(key: String): Boolean
}
```

### INetworkMonitor (网络监控接口)
```kotlin
interface INetworkMonitor {
    fun isConnected(): Boolean
    fun getConnectionType(): ConnectionType
    fun observeConnectionState(): Flow<Boolean>
}
```

## 🚀 使用方式

### 导入基础设施模块
```kotlin
import com.example.kmpuniversalapp.infrastructure.*
import com.example.kmpuniversalapp.infrastructure.interfaces.*
```

### 使用网络服务
```kotlin
class ApiService(
    private val httpClient: INetworkClient,
    private val cache: ICache
) {
    suspend fun fetchData(): Result<String> {
        return try {
            val cached = cache.get("data") as? String
            if (cached != null) {
                Result.success(cached)
            } else {
                val data = httpClient.get("https://api.example.com/data")
                cache.put("data", data, 300000) // 5分钟缓存
                Result.success(data)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 使用存储服务
```kotlin
class UserRepository(
    private val storage: IStorage,
    private val database: IDatabase
) {
    suspend fun saveUser(user: User) {
        storage.putString("user_${user.id}", user.toJson())
        database.execute(
            "INSERT OR REPLACE INTO users (id, name, email) VALUES (?, ?, ?)",
            listOf(user.id, user.name, user.email)
        )
    }
}
```

## ✅ 设计原则

1. **接口隔离原则**: 每个接口职责单一
2. **依赖倒置原则**: 依赖抽象而非具体实现
3. **开闭原则**: 对扩展开放，对修改关闭
4. **单一职责原则**: 每个类只有一个变化的理由

## 📝 注意事项

1. **平台无关**: 接口定义应支持多平台
2. **错误处理**: 统一的错误处理机制
3. **性能考虑**: 考虑缓存和异步操作
4. **安全性**: 敏感数据加密存储

## 🧪 测试策略

1. **单元测试**: 测试每个基础设施组件
2. **集成测试**: 测试组件间的协作
3. **Mock测试**: 使用Mock对象测试依赖
4. **性能测试**: 测试缓存和网络性能


