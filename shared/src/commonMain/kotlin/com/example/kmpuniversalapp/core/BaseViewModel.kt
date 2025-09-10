package com.example.kmpuniversalapp.core

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 优化的基础ViewModel
 * 集成生命周期管理和更好的错误处理
 */
abstract class BaseViewModel(
    private val lifecycle: Lifecycle
) {
    // 协程作用域
    protected val viewModelScope = CoroutineScope(SupervisorJob())
    
    // 加载状态
    protected val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // 错误信息
    protected val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    // 成功信息
    protected val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()
    
    init {
        lifecycle.doOnCreate {
            onCreated()
        }
        
        lifecycle.doOnDestroy {
            onDestroyed()
            viewModelScope.cancel()
        }
    }
    
    /**
     * ViewModel创建时调用
     */
    protected open fun onCreated() {
        AppLogger.d("BaseViewModel", "${this::class.simpleName} created")
    }
    
    /**
     * ViewModel销毁时调用
     */
    protected open fun onDestroyed() {
        AppLogger.d("BaseViewModel", "${this::class.simpleName} destroyed")
    }
    
    /**
     * 设置加载状态
     */
    protected fun setLoading(loading: Boolean) {
        _isLoading.value = loading
        AppLogger.d("BaseViewModel", "Loading: $loading")
    }
    
    /**
     * 设置错误信息
     */
    protected fun setError(message: String?) {
        _errorMessage.value = message
        if (message != null) {
            AppLogger.e("BaseViewModel", "Error: $message")
        }
    }
    
    /**
     * 设置成功信息
     */
    protected fun setSuccess(message: String?) {
        _successMessage.value = message
        if (message != null) {
            AppLogger.i("BaseViewModel", "Success: $message")
        }
    }
    
    /**
     * 清除所有消息
     */
    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
    
    /**
     * 处理异常 - 增强版本
     */
    protected fun handleException(e: Exception, defaultMessage: String = "操作失败") {
        val errorMessage = "$defaultMessage: ${e.message ?: e.toString()}"
        setError(errorMessage)
        AppLogger.e("BaseViewModel", "Exception in ${this::class.simpleName}", e)
    }
    
    /**
     * 安全执行操作
     */
    protected suspend fun <T> safeExecute(
        operation: suspend () -> T,
        onError: (Exception) -> Unit = { handleException(it) }
    ): T? {
        return try {
            operation()
        } catch (e: Exception) {
            onError(e)
            null
        }
    }
    
    /**
     * 安全执行操作（带默认值）
     */
    protected suspend fun <T> safeExecuteWithDefault(
        operation: suspend () -> T,
        defaultValue: T,
        onError: (Exception) -> Unit = { handleException(it) }
    ): T {
        return try {
            operation()
        } catch (e: Exception) {
            onError(e)
            defaultValue
        }
    }
    
    /**
     * 执行异步任务并返回 Deferred 结果
     * 参考 Translation-KMP 的 execute 方法
     */
    fun <T> execute(
        scope: CoroutineScope = viewModelScope,
        context: CoroutineContext = Dispatchers.Default,
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return scope.async(context) { 
            try {
                block()
            } catch (e: Exception) {
                handleException(e)
                throw e
            }
        }
    }
    
    /**
     * 提交异步任务并返回 Job
     * 参考 Translation-KMP 的 submit 方法
     */
    fun <T> submit(
        scope: CoroutineScope = viewModelScope,
        context: CoroutineContext = Dispatchers.Default,
        block: suspend CoroutineScope.() -> T
    ): Job {
        return scope.launch(context) { 
            try {
                block()
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }
}
