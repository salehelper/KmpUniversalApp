package com.example.kmpuniversalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmpuniversalapp.libs.utils.log.AppLogger

/**
 * 主Activity
 */
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        // 初始化日志
        initLogging()

        setContent {
            App()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
    
    /**
     * 初始化日志系统
     */
    private fun initLogging() {
        AppLogger.d("MainActivity", "onCreate 开始")
        AppLogger.i("MainActivity", "应用启动中...")
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}