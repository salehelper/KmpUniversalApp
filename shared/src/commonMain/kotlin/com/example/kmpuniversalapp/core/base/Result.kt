package com.example.kmpuniversalapp.core.base

/**
 * 通用结果类型
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Throwable) : Result<Nothing>()
}

/**
 * 扩展函数：检查是否为成功结果
 */
fun <T> Result<T>.isSuccess(): Boolean = this is Result.Success

/**
 * 扩展函数：检查是否为错误结果
 */
fun <T> Result<T>.isError(): Boolean = this is Result.Error

/**
 * 扩展函数：获取成功数据，如果失败则返回null
 */
fun <T> Result<T>.getDataOrNull(): T? = when (this) {
    is Result.Success -> data
    is Result.Error -> null
}

/**
 * 扩展函数：获取错误，如果成功则返回null
 */
fun <T> Result<T>.getErrorOrNull(): Throwable? = when (this) {
    is Result.Success -> null
    is Result.Error -> error
}

/**
 * 扩展函数：获取成功数据，如果失败则抛出异常
 */
fun <T> Result<T>.getDataOrThrow(): T = when (this) {
    is Result.Success -> data
    is Result.Error -> throw error
}

/**
 * 扩展函数：获取成功数据，如果失败则返回默认值
 */
fun <T> Result<T>.getDataOrDefault(defaultValue: T): T = when (this) {
    is Result.Success -> data
    is Result.Error -> defaultValue
}

/**
 * 扩展函数：映射成功数据
 */
fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> Result.Error(error)
}

/**
 * 扩展函数：映射错误
 */
fun <T> Result<T>.mapError(transform: (Throwable) -> Throwable): Result<T> = when (this) {
    is Result.Success -> this
    is Result.Error -> Result.Error(transform(error))
}

/**
 * 扩展函数：链式调用
 */
fun <T, R> Result<T>.flatMap(transform: (T) -> Result<R>): Result<R> = when (this) {
    is Result.Success -> transform(data)
    is Result.Error -> Result.Error(error)
}