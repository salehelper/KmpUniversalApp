package com.example.kmpuniversalapp.presentation.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * 通用对话框
 * 基于 AndroidProject-Kotlin 的 CommonDialog 实现
 */
@Composable
fun CommonDialog(
    title: String? = null,
    message: String? = null,
    confirmText: String = "确定",
    cancelText: String? = null,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showDialog: Boolean = true,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    content: @Composable (() -> Unit)? = null
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                if (dismissOnClickOutside) {
                    onDismiss()
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 标题
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    
                    // 内容
                    if (content != null) {
                        content()
                    } else {
                        message?.let {
                            Text(
                                text = it,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 24.dp)
                            )
                        }
                    }
                    
                    // 按钮
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // 取消按钮
                        cancelText?.let {
                            TextButton(
                                onClick = {
                                    onCancel()
                                    onDismiss()
                                }
                            ) {
                                Text(
                                    text = it,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        
                        // 确定按钮
                        Button(
                            onClick = {
                                onConfirm()
                                onDismiss()
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(text = confirmText)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 消息对话框
 * 基于 AndroidProject-Kotlin 的 MessageDialog 实现
 */
@Composable
fun MessageDialog(
    title: String? = null,
    message: String,
    confirmText: String = "确定",
    cancelText: String? = "取消",
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showDialog: Boolean = true
) {
    CommonDialog(
        title = title,
        message = message,
        confirmText = confirmText,
        cancelText = cancelText,
        onConfirm = onConfirm,
        onCancel = onCancel,
        onDismiss = onDismiss,
        showDialog = showDialog
    )
}

/**
 * 输入对话框
 * 基于 AndroidProject-Kotlin 的 InputDialog 实现
 */
@Composable
fun InputDialog(
    title: String? = null,
    hint: String = "请输入内容",
    initialValue: String = "",
    confirmText: String = "确定",
    cancelText: String = "取消",
    onConfirm: (String) -> Unit = {},
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showDialog: Boolean = true
) {
    var inputValue by remember { mutableStateOf(initialValue) }
    
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 标题
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    
                    // 输入框
                    OutlinedTextField(
                        value = inputValue,
                        onValueChange = { inputValue = it },
                        label = { Text(hint) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        singleLine = true
                    )
                    
                    // 按钮
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        // 取消按钮
                        TextButton(
                            onClick = {
                                onCancel()
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = cancelText,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        
                        // 确定按钮
                        Button(
                            onClick = {
                                onConfirm(inputValue)
                                onDismiss()
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(text = confirmText)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 菜单对话框
 * 基于 AndroidProject-Kotlin 的 MenuDialog 实现
 */
@Composable
fun MenuDialog(
    title: String? = null,
    items: List<String>,
    onItemSelected: (Int, String) -> Unit = { _, _ -> },
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {},
    showDialog: Boolean = true
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // 标题
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }
                    
                    // 菜单项
                    items.forEachIndexed { index, item ->
                        MenuItem(
                            text = item,
                            onClick = {
                                onItemSelected(index, item)
                                onDismiss()
                            }
                        )
                        
                        if (index < items.size - 1) {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * 菜单项
 */
@Composable
private fun MenuItem(
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
