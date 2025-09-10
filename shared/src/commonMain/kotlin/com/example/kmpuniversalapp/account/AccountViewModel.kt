package com.example.kmpuniversalapp.account

import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import com.example.kmpuniversalapp.core.BaseViewModel
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 账户管理ViewModel
 */
class AccountViewModel(
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    // 当前用户
    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser: StateFlow<UserModel?> = _currentUser.asStateFlow()
    
    // 登录状态
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()
    
    // 加载状态和错误信息继承自BaseViewModel
    
    /**
     * 用户登录
     */
    suspend fun login(email: String, password: String, rememberMe: Boolean = false) {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            val request = LoginRequest(email, password, rememberMe)
            val response = performLogin(request)
            
            if (response.success && response.user != null) {
                _currentUser.value = response.user
                _isLoggedIn.value = true
            } else {
                _errorMessage.value = response.message ?: "登录失败"
            }
            
        } catch (e: Exception) {
            _errorMessage.value = "登录失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 用户注册
     */
    suspend fun register(name: String, email: String, password: String, confirmPassword: String) {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            if (password != confirmPassword) {
                _errorMessage.value = "两次输入的密码不一致"
                return
            }
            
            val request = RegisterRequest(name, email, password, confirmPassword)
            val response = performRegister(request)
            
            if (response.success && response.user != null) {
                _currentUser.value = response.user
                _isLoggedIn.value = true
            } else {
                _errorMessage.value = response.message ?: "注册失败"
            }
            
        } catch (e: Exception) {
            _errorMessage.value = "注册失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 用户登出
     */
    suspend fun logout() {
        try {
            _currentUser.value = null
            _isLoggedIn.value = false
            _errorMessage.value = null
        } catch (e: Exception) {
            _errorMessage.value = "登出失败: ${e.message}"
        }
    }
    
    /**
     * 忘记密码
     */
    suspend fun forgotPassword(email: String) {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            val request = ForgotPasswordRequest(email)
            val success = performForgotPassword(request)
            
            if (!success) {
                _errorMessage.value = "发送重置邮件失败"
            }
            
        } catch (e: Exception) {
            _errorMessage.value = "忘记密码失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    /**
     * 重置密码
     */
    suspend fun resetPassword(token: String, newPassword: String, confirmPassword: String) {
        try {
            _isLoading.value = true
            _errorMessage.value = null
            
            if (newPassword != confirmPassword) {
                _errorMessage.value = "两次输入的密码不一致"
                return
            }
            
            val request = ResetPasswordRequest(token, newPassword, confirmPassword)
            val success = performResetPassword(request)
            
            if (!success) {
                _errorMessage.value = "重置密码失败"
            }
            
        } catch (e: Exception) {
            _errorMessage.value = "重置密码失败: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }
    
    private suspend fun performLogin(request: LoginRequest): LoginResponse {
        // 模拟登录API调用
        return LoginResponse(
            success = true,
            token = "mock_token_${kotlinx.datetime.Clock.System.now().toEpochMilliseconds()}",
            user = UserModel(
                id = "user_001",
                name = "KMP开发者",
                email = request.email,
                avatar = "https://via.placeholder.com/150",
                isVerified = true,
                createdAt = "2024-01-01T00:00:00Z",
                lastLoginAt = "2024-01-15T10:00:00Z"
            )
        )
    }
    
    private suspend fun performRegister(request: RegisterRequest): RegisterResponse {
        // 模拟注册API调用
        return RegisterResponse(
            success = true,
            user = UserModel(
                id = "user_${kotlinx.datetime.Clock.System.now().toEpochMilliseconds()}",
                name = request.name,
                email = request.email,
                avatar = null,
                isVerified = false,
                createdAt = "2024-01-15T10:00:00Z",
                lastLoginAt = null
            )
        )
    }
    
    private suspend fun performForgotPassword(request: ForgotPasswordRequest): Boolean {
        // 模拟忘记密码API调用
        return true
    }
    
    private suspend fun performResetPassword(request: ResetPasswordRequest): Boolean {
        // 模拟重置密码API调用
        return true
    }
}
