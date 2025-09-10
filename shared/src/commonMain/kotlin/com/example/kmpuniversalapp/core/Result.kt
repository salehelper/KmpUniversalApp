package com.example.kmpuniversalapp.core

/**
 * 通用结果封装
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

/**
 * 扩展函数：检查是否为成功状态
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * 扩展函数：检查是否为错误状态
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * 扩展函数：检查是否为加载状态
 */
fun <T> Result<T>.isLoading(): Boolean = this is Result.Loading

/**
 * 扩展函数：获取数据
 */
fun <T> Result<T>.getDataOrNull(): T? = when (this) {
    is Result.Success -> data
    else -> null
}

/**
 * 扩展函数：获取异常
 */
fun <T> Result<T>.getExceptionOrNull(): Throwable? = when (this) {
    is Result.Error -> exception
    else -> null
}
