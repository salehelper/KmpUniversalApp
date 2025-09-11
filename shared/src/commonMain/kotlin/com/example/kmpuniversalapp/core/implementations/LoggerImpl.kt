package com.example.kmpuniversalapp.core.implementations

import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.utils.log.AppLogger

/**
 * 日志实现类
 * 使用现有的AppLogger实现ILogger接口
 */
class LoggerImpl : ILogger {
    
    override fun d(tag: String, message: String) {
        AppLogger.d(tag, message)
    }

    override fun i(tag: String, message: String) {
        AppLogger.i(tag, message)
    }

    override fun w(tag: String, message: String) {
        AppLogger.w(tag, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable?) {
        AppLogger.e(tag, message, throwable)
    }
}
