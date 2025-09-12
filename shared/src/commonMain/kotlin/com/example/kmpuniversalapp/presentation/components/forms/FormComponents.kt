package com.example.kmpuniversalapp.presentation.components.forms

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.kmpuniversalapp.core.utils.validation.ValidationUtils

/**
 * 表单组件
 * 基于 AndroidProject-Kotlin 的表单控件实现
 */

/**
 * 密码输入框
 * 基于 AndroidProject-Kotlin 的 PasswordEditText 实现
 */
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "密码",
    placeholder: String = "请输入密码",
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = isError,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (passwordVisible) "隐藏密码" else "显示密码"
                )
            }
        }
    )
    
    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * 手机号输入框
 * 基于 AndroidProject-Kotlin 的 RegexEditText 实现
 */
@Composable
fun PhoneTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "手机号",
    placeholder: String = "请输入手机号",
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // 只允许输入数字
            if (newValue.all { it.isDigit() } && newValue.length <= 11) {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        isError = isError,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
    
    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * 邮箱输入框
 */
@Composable
fun EmailTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "邮箱",
    placeholder: String = "请输入邮箱",
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        isError = isError,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
    
    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * 验证码输入框
 */
@Composable
fun VerificationCodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "验证码",
    placeholder: String = "请输入验证码",
    isError: Boolean = false,
    errorMessage: String = "",
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onSendCode: () -> Unit = {},
    canSendCode: Boolean = true,
    countdown: Int = 0
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                // 只允许输入数字
                if (newValue.all { it.isDigit() } && newValue.length <= 6) {
                    onValueChange(newValue)
                }
            },
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError,
            enabled = enabled,
            modifier = Modifier.weight(1f)
        )
        
        Button(
            onClick = onSendCode,
            enabled = canSendCode && countdown == 0
        ) {
            Text(
                text = if (countdown > 0) "${countdown}s" else "发送验证码"
            )
        }
    }
    
    if (isError && errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

/**
 * 表单验证器
 * 提供表单验证功能
 */
class FormValidator {
    private val errors = mutableMapOf<String, String>()
    
    fun validateEmail(email: String, fieldName: String = "邮箱"): Boolean {
        return if (email.isBlank()) {
            errors[fieldName] = "$fieldName 不能为空"
            false
        } else if (!ValidationUtils.isValidEmail(email)) {
            errors[fieldName] = "$fieldName 格式不正确"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun validatePhone(phone: String, fieldName: String = "手机号"): Boolean {
        return if (phone.isBlank()) {
            errors[fieldName] = "$fieldName 不能为空"
            false
        } else if (!ValidationUtils.isValidPhone(phone)) {
            errors[fieldName] = "$fieldName 格式不正确"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun validatePassword(password: String, fieldName: String = "密码"): Boolean {
        return if (password.isBlank()) {
            errors[fieldName] = "$fieldName 不能为空"
            false
        } else if (!ValidationUtils.isValidPassword(password)) {
            errors[fieldName] = "$fieldName 强度不够"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun validateConfirmPassword(password: String, confirmPassword: String, fieldName: String = "确认密码"): Boolean {
        return if (confirmPassword.isBlank()) {
            errors[fieldName] = "$fieldName 不能为空"
            false
        } else if (password != confirmPassword) {
            errors[fieldName] = "两次密码输入不一致"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun validateRequired(value: String, fieldName: String): Boolean {
        return if (value.isBlank()) {
            errors[fieldName] = "$fieldName 不能为空"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun validateLength(value: String, minLength: Int, maxLength: Int, fieldName: String): Boolean {
        return if (!ValidationUtils.isValidLength(value, minLength, maxLength)) {
            errors[fieldName] = "$fieldName 长度必须在 $minLength-$maxLength 个字符之间"
            false
        } else {
            errors.remove(fieldName)
            true
        }
    }
    
    fun getError(fieldName: String): String? = errors[fieldName]
    
    fun hasErrors(): Boolean = errors.isNotEmpty()
    
    fun clearErrors() {
        errors.clear()
    }
    
    fun getAllErrors(): Map<String, String> = errors.toMap()
}

/**
 * 表单状态管理
 */
@Composable
fun rememberFormValidator(): FormValidator {
    return remember { FormValidator() }
}

/**
 * 倒计时状态管理
 */
@Composable
fun rememberCountdownState(): CountdownState {
    return remember { CountdownState() }
}

class CountdownState {
    private var _countdown by mutableStateOf(0)
    val countdown: Int get() = _countdown
    
    fun startCountdown(seconds: Int = 60) {
        _countdown = seconds
    }
    
    fun stopCountdown() {
        _countdown = 0
    }
    
    fun isCountingDown(): Boolean = _countdown > 0
}
