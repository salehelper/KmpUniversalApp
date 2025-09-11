package com.example.kmpuniversalapp.infrastructure.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.module

/**
 * 存储管理器接口
 */
interface StorageManager {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String = ""): String
    suspend fun putInt(key: String, value: Int)
    suspend fun getInt(key: String, defaultValue: Int = 0): Int
    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    suspend fun putLong(key: String, value: Long)
    suspend fun getLong(key: String, defaultValue: Long = 0L): Long
    suspend fun putFloat(key: String, value: Float)
    suspend fun getFloat(key: String, defaultValue: Float = 0f): Float
    suspend fun remove(key: String)
    suspend fun clear()
    suspend fun contains(key: String): Boolean
    fun observeString(key: String, defaultValue: String = ""): Flow<String>
}

/**
 * 内存存储管理器实现
 */
class MemoryStorageManager : StorageManager {
    private val storage = mutableMapOf<String, Any>()
    private val flows = mutableMapOf<String, MutableStateFlow<String>>()
    
    override suspend fun putString(key: String, value: String) {
        storage[key] = value
        flows[key]?.value = value
    }
    
    override suspend fun getString(key: String, defaultValue: String): String {
        return storage[key] as? String ?: defaultValue
    }
    
    override suspend fun putInt(key: String, value: Int) {
        storage[key] = value
    }
    
    override suspend fun getInt(key: String, defaultValue: Int): Int {
        return storage[key] as? Int ?: defaultValue
    }
    
    override suspend fun putBoolean(key: String, value: Boolean) {
        storage[key] = value
    }
    
    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return storage[key] as? Boolean ?: defaultValue
    }
    
    override suspend fun putLong(key: String, value: Long) {
        storage[key] = value
    }
    
    override suspend fun getLong(key: String, defaultValue: Long): Long {
        return storage[key] as? Long ?: defaultValue
    }
    
    override suspend fun putFloat(key: String, value: Float) {
        storage[key] = value
    }
    
    override suspend fun getFloat(key: String, defaultValue: Float): Float {
        return storage[key] as? Float ?: defaultValue
    }
    
    override suspend fun remove(key: String) {
        storage.remove(key)
        flows.remove(key)
    }
    
    override suspend fun clear() {
        storage.clear()
        flows.clear()
    }
    
    override suspend fun contains(key: String): Boolean {
        return storage.containsKey(key)
    }
    
    override fun observeString(key: String, defaultValue: String): Flow<String> {
        if (!flows.containsKey(key)) {
            flows[key] = MutableStateFlow(storage[key] as? String ?: defaultValue)
        }
        return flows[key]!!.asStateFlow()
    }
}

/**
 * 存储模块的Koin配置
 */
val storageModule = module {
    single<StorageManager> { MemoryStorageManager() }
}
