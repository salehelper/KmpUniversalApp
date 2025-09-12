package com.example.kmpuniversalapp.core.base

/**
 * 应用错误类
 * 用于表示应用中的各种错误情况
 */
data class AppError(
    val code: Int,
    override val message: String,
    val details: String? = null,
    val errorCause: String? = null
) : Throwable(message)
