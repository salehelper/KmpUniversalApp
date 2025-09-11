package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.IStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 存储实现类
 * 提供内存存储功能（实际项目中应该使用平台特定的存储实现）
 */
class StorageImpl : IStorage {
    
    private val storage = mutableMapOf<String, String>()
    private val observers = mutableMapOf<String, MutableStateFlow<String>>()
    
    override suspend fun putString(key: String, value: String) {
        storage[key] = value
        // 通知观察者
        observers[key]?.value = value
    }

    override suspend fun getString(key: String, defaultValue: String): String {
        return storage[key] ?: defaultValue
    }

    override suspend fun remove(key: String) {
        storage.remove(key)
        observers.remove(key)
    }

    override suspend fun contains(key: String): Boolean {
        return storage.containsKey(key)
    }

    override fun observeString(key: String, defaultValue: String): Flow<String> {
        val currentValue = storage[key] ?: defaultValue
        val flow = observers.getOrPut(key) { 
            MutableStateFlow(currentValue) 
        }
        return flow.asStateFlow()
    }
}
