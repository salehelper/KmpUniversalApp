# Compose布局组件使用指南

## 概述

在Compose开发中，`Column`和`LazyColumn`是最常用的垂直布局组件。选择正确的组件对性能和用户体验至关重要。

## Column - 简单垂直布局

### 基本用法

```kotlin
@Composable
fun SimpleList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("项目1")
        Text("项目2") 
        Text("项目3")
    }
}
```

### 带滚动的Column

```kotlin
@Composable
fun ScrollableList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        repeat(50) { index ->
            Text("项目 $index")
        }
    }
}
```

### 适用场景

- ✅ 固定数量的子组件（< 20个）
- ✅ 需要简单垂直排列
- ✅ 在复杂布局容器内使用
- ✅ 需要自定义间距和排列

## LazyColumn - 懒加载列表

### 基本用法

```kotlin
@Composable
fun LazyList() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dataList) { item ->
            ItemCard(item = item)
        }
    }
}
```

### 带索引的列表

```kotlin
@Composable
fun IndexedList() {
    LazyColumn {
        itemsIndexed(dataList) { index, item ->
            ItemCard(
                item = item,
                index = index
            )
        }
    }
}
```

### 适用场景

- ✅ 大量数据（> 20个item）
- ✅ 需要性能优化
- ✅ 独立页面使用
- ✅ 需要懒加载

## 常见坑和解决方案

### 坑1：LazyColumn嵌套导致约束错误

**问题代码：**
```kotlin
// ❌ 错误：在Scaffold内直接使用LazyColumn
Scaffold {
    LazyColumn {  // 会导致无限高度约束错误
        items(data) { item -> ... }
    }
}
```

**解决方案：**
```kotlin
// ✅ 正确：使用Column + verticalScroll
Scaffold {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach { item ->
            ItemCard(item)
        }
    }
}
```

### 坑2：LazyColumn在有限高度容器内

**问题代码：**
```kotlin
// ❌ 错误：在Box内使用LazyColumn
Box(modifier = Modifier.height(200.dp)) {
    LazyColumn {  // 约束冲突
        items(data) { item -> ... }
    }
}
```

**解决方案：**
```kotlin
// ✅ 正确：使用Column
Box(modifier = Modifier.height(200.dp)) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        data.forEach { item ->
            ItemCard(item)
        }
    }
}
```

### 坑3：忘记导入滚动相关函数

**问题代码：**
```kotlin
// ❌ 错误：缺少导入
Column(
    modifier = Modifier.verticalScroll(rememberScrollState())  // 编译错误
)
```

**解决方案：**
```kotlin
// ✅ 正确：添加导入
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
)
```

### 坑4：LazyColumn的items函数使用错误

**问题代码：**
```kotlin
// ❌ 错误：在Column中使用items
Column {
    items(data) { item ->  // 编译错误
        ItemCard(item)
    }
}
```

**解决方案：**
```kotlin
// ✅ 正确：使用forEach
Column {
    data.forEach { item ->
        ItemCard(item)
    }
}
```

## 性能对比

| 场景 | Column | LazyColumn | 推荐 |
|------|--------|------------|------|
| 5个item | 快 | 慢 | Column |
| 20个item | 快 | 中等 | Column |
| 50个item | 中等 | 快 | LazyColumn |
| 100+个item | 慢 | 快 | LazyColumn |

## 最佳实践

### 1. 选择合适的组件

```kotlin
// 少量数据，使用Column
@Composable
fun SettingsScreen() {
    Column {
        SettingItem("通知")
        SettingItem("隐私")
        SettingItem("关于")
    }
}

// 大量数据，使用LazyColumn
@Composable
fun NewsListScreen() {
    LazyColumn {
        items(newsList) { news ->
            NewsItem(news)
        }
    }
}
```

### 2. 正确处理滚动

```kotlin
// 在复杂布局中，优先使用Column + verticalScroll
@Composable
fun HomeScreen() {
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection()
            ContentSection()
            FooterSection()
        }
    }
}
```

### 3. 避免过度嵌套

```kotlin
// ❌ 避免：过度嵌套
LazyColumn {
    item { Header() }
    items(data) { item ->
        LazyRow {  // 嵌套LazyRow
            items(item.subItems) { subItem ->
                SubItemCard(subItem)
            }
        }
    }
}

// ✅ 推荐：简化结构
LazyColumn {
    item { Header() }
    items(data) { item ->
        Column {  // 使用Column
            item.subItems.forEach { subItem ->
                SubItemCard(subItem)
            }
        }
    }
}
```

## 总结

- **Column**：适合简单布局，少量数据，需要嵌套在复杂容器中
- **LazyColumn**：适合大量数据，独立页面，需要性能优化
- **关键**：避免在Scaffold等容器内直接使用LazyColumn
- **记住**：Column + verticalScroll 是大多数场景的最佳选择
