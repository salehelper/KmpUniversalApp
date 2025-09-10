package com.example.kmpuniversalapp

import android.util.Log
import android.widget.Toast
import android.content.Context

/**
 * 简单的日志测试类
 */
object LogTest {
    
    fun testAllLogMethods(context: Context) {
        // 1. Toast显示（最直观）
        Toast.makeText(context, "LogTest: 测试开始", Toast.LENGTH_LONG).show()
        
        // 2. Android Log
        Log.d("LogTest", "LogTest: 测试开始")
        Log.i("LogTest", "LogTest: 信息日志")
        Log.w("LogTest", "LogTest: 警告日志")
        Log.e("LogTest", "LogTest: 错误日志")
        
        // 3. System.out
        System.out.println("LogTest: System.out.println 测试")
        System.err.println("LogTest: System.err.println 测试")
        
        // 4. 强制刷新
        System.out.flush()
        System.err.flush()
        
        // 5. 延迟确保输出
        try {
            Thread.sleep(200)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
