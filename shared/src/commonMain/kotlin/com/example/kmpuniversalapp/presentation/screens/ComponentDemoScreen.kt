package com.example.kmpuniversalapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmpuniversalapp.presentation.components.dialogs.*
import com.example.kmpuniversalapp.presentation.components.forms.*
import com.example.kmpuniversalapp.presentation.components.status.*

/**
 * 组件演示页面
 * 展示所有实现的跨平台组件
 */
@Composable
fun ComponentDemoScreen() {
    val statusManager = rememberStatusLayoutManager()
    val formValidator = rememberFormValidator()
    val countdownState = rememberCountdownState()
    
    // 状态管理
    var showMessageDialog by remember { mutableStateOf(false) }
    var showInputDialog by remember { mutableStateOf(false) }
    var showMenuDialog by remember { mutableStateOf(false) }
    
    // 表单状态
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    
    // 列表数据
    val demoItems = remember { 
        listOf("项目 1", "项目 2", "项目 3", "项目 4", "项目 5")
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 标题
        Text(
            text = "跨平台组件演示",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 状态布局演示
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "状态布局演示",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = { statusManager.setLoading() }) {
                                Text("加载中")
                            }
                            Button(onClick = { statusManager.setError() }) {
                                Text("错误")
                            }
                            Button(onClick = { statusManager.setEmpty() }) {
                                Text("空数据")
                            }
                            Button(onClick = { statusManager.setSuccess() }) {
                                Text("成功")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        StatusLayoutContent(
                            statusManager = statusManager,
                            onRetry = { statusManager.setLoading() },
                            modifier = Modifier.height(200.dp)
                        ) {
                            LazyColumn {
                                items(demoItems) { item ->
                                    ListItem(
                                        headlineContent = { Text(item) },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // 对话框演示
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "对话框演示",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(onClick = { showMessageDialog = true }) {
                                Text("消息对话框")
                            }
                            Button(onClick = { showInputDialog = true }) {
                                Text("输入对话框")
                            }
                            Button(onClick = { showMenuDialog = true }) {
                                Text("菜单对话框")
                            }
                        }
                    }
                }
            }
            
            // 表单组件演示
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "表单组件演示",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 邮箱输入框
                        EmailTextField(
                            value = email,
                            onValueChange = { email = it },
                            isError = formValidator.getError("邮箱") != null,
                            errorMessage = formValidator.getError("邮箱") ?: "",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 手机号输入框
                        PhoneTextField(
                            value = phone,
                            onValueChange = { phone = it },
                            isError = formValidator.getError("手机号") != null,
                            errorMessage = formValidator.getError("手机号") ?: "",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 密码输入框
                        PasswordTextField(
                            value = password,
                            onValueChange = { password = it },
                            isError = formValidator.getError("密码") != null,
                            errorMessage = formValidator.getError("密码") ?: "",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 确认密码输入框
                        PasswordTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = "确认密码",
                            placeholder = "请再次输入密码",
                            isError = formValidator.getError("确认密码") != null,
                            errorMessage = formValidator.getError("确认密码") ?: "",
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 验证码输入框
                        VerificationCodeTextField(
                            value = verificationCode,
                            onValueChange = { verificationCode = it },
                            countdown = countdownState.countdown,
                            canSendCode = countdownState.isCountingDown().not(),
                            onSendCode = { countdownState.startCountdown() },
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        // 验证按钮
                        Button(
                            onClick = {
                                formValidator.clearErrors()
                                var isValid = true
                                
                                isValid = formValidator.validateEmail(email) && isValid
                                isValid = formValidator.validatePhone(phone) && isValid
                                isValid = formValidator.validatePassword(password) && isValid
                                isValid = formValidator.validateConfirmPassword(password, confirmPassword) && isValid
                                
                                if (isValid) {
                                    // 验证成功
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("验证表单")
                        }
                    }
                }
            }
        }
    }
    
    // 消息对话框
    MessageDialog(
        title = "提示",
        message = "这是一个消息对话框",
        showDialog = showMessageDialog,
        onDismiss = { showMessageDialog = false }
    )
    
    // 输入对话框
    InputDialog(
        title = "输入内容",
        hint = "请输入一些内容",
        showDialog = showInputDialog,
        onConfirm = { input ->
            // 处理输入
        },
        onDismiss = { showInputDialog = false }
    )
    
    // 菜单对话框
    MenuDialog(
        title = "选择选项",
        items = listOf("选项 1", "选项 2", "选项 3", "选项 4"),
        onItemSelected = { index, item ->
            // 处理选择
        },
        showDialog = showMenuDialog,
        onDismiss = { showMenuDialog = false }
    )
}
