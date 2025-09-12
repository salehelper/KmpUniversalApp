package com.example.kmpuniversalapp.business.auth

import com.example.kmpuniversalapp.core.base.Result
import com.example.kmpuniversalapp.core.base.AppError
import com.example.kmpuniversalapp.core.base.Constants
import com.example.kmpuniversalapp.core.IStorage
import com.example.kmpuniversalapp.core.ILogger
import com.example.kmpuniversalapp.core.models.User
import com.example.kmpuniversalapp.core.models.LoginRequest
import com.example.kmpuniversalapp.core.models.RegisterRequest
import com.example.kmpuniversalapp.core.models.ResetPasswordRequest
import com.example.kmpuniversalapp.core.models.ChangePasswordRequest
import com.example.kmpuniversalapp.core.utils.validation.ValidationUtils
import com.example.kmpuniversalapp.core.utils.getSimpleClassName
import com.example.kmpuniversalapp.core.utils.getCurrentTimeMillis
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * 认证管理器
 * 处理用户登录、注册、密码重置等认证相关功能
 */
class AuthManager(
    private val storage: IStorage,
    private val logger: ILogger
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    /**
     * 用户登录
     */
    suspend fun login(request: LoginRequest): Result<User> {
        return try {
            logger.i("AuthManager", "开始用户登录: ${request.username}")
            
            // 验证输入
            if (!ValidationUtils.isValidEmail(request.username) && !ValidationUtils.isValidPhone(request.username)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "用户名格式不正确",
                    details = "请输入有效的邮箱或手机号"
                ))
            }
            
            if (!ValidationUtils.isValidPassword(request.password)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "密码格式不正确",
                    details = "密码长度应在${Constants.PASSWORD_MIN_LENGTH}-${Constants.PASSWORD_MAX_LENGTH}位之间"
                ))
            }
            
            // 模拟登录逻辑
            val user = User(
                id = "user_${getCurrentTimeMillis()}",
                username = request.username,
                email = if (ValidationUtils.isValidEmail(request.username)) request.username else "",
                phone = if (ValidationUtils.isValidPhone(request.username)) request.username else "",
                displayName = request.username,
                avatar = null,
                isEmailVerified = false,
                isPhoneVerified = false,
                createdAt = getCurrentTimeMillis(),
                lastLoginAt = getCurrentTimeMillis(),
                preferences = emptyMap()
            )
            
            // 保存用户信息到本地存储
            val userJson = json.encodeToString(user)
            storage.putString(Constants.StorageKeys.USER_INFO, userJson)
            storage.putString(Constants.StorageKeys.USER_TOKEN, "mock_token_${getCurrentTimeMillis()}")
            
            logger.i("AuthManager", "用户登录成功: ${user.id}")
            Result.Success(user)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "登录失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "登录失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 用户注册
     */
    suspend fun register(request: RegisterRequest): Result<User> {
        return try {
            logger.i("AuthManager", "开始用户注册: ${request.username}")
            
            // 验证输入
            if (!ValidationUtils.isValidEmail(request.email)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "邮箱格式不正确",
                    details = "请输入有效的邮箱地址"
                ))
            }
            
            if (!ValidationUtils.isValidPassword(request.password)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "密码格式不正确",
                    details = "密码长度应在${Constants.PASSWORD_MIN_LENGTH}-${Constants.PASSWORD_MAX_LENGTH}位之间"
                ))
            }
            
            if (request.password != request.confirmPassword) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "密码确认不匹配",
                    details = "两次输入的密码不一致"
                ))
            }
            
            // 模拟注册逻辑
            val user = User(
                id = "user_${getCurrentTimeMillis()}",
                username = request.username,
                email = request.email,
                phone = request.phone ?: "",
                displayName = request.displayName ?: request.username,
                avatar = null,
                isEmailVerified = false,
                isPhoneVerified = false,
                createdAt = getCurrentTimeMillis(),
                lastLoginAt = 0L,
                preferences = emptyMap()
            )
            
            // 保存用户信息到本地存储
            val userJson = json.encodeToString(user)
            storage.putString(Constants.StorageKeys.USER_INFO, userJson)
            
            logger.i("AuthManager", "用户注册成功: ${user.id}")
            Result.Success(user)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "注册失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "注册失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 用户登出
     */
    suspend fun logout(): Result<Unit> {
        return try {
            logger.i("AuthManager", "开始用户登出")
            
            // 清除本地存储的用户信息
            storage.remove(Constants.StorageKeys.USER_INFO)
            storage.remove(Constants.StorageKeys.USER_TOKEN)
            
            logger.i("AuthManager", "用户登出成功")
            Result.Success(Unit)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "登出失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "登出失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 获取当前用户信息
     */
    suspend fun getCurrentUser(): Result<User?> {
        return try {
            val userJson = storage.getString(Constants.StorageKeys.USER_INFO, "")
            if (userJson.isNotEmpty()) {
                val user = json.decodeFromString<User>(userJson)
                Result.Success(user)
            } else {
                Result.Success(null)
            }
        } catch (e: Exception) {
            logger.e("AuthManager", "获取用户信息失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "获取用户信息失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 检查用户是否已登录
     */
    suspend fun isLoggedIn(): Result<Boolean> {
        return try {
            val token = storage.getString(Constants.StorageKeys.USER_TOKEN, "")
            val userInfo = storage.getString(Constants.StorageKeys.USER_INFO, "")
            Result.Success(token.isNotEmpty() && userInfo.isNotEmpty())
        } catch (e: Exception) {
            logger.e("AuthManager", "检查登录状态失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "检查登录状态失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 重置密码
     */
    suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit> {
        return try {
            logger.i("AuthManager", "开始重置密码: ${request.email}")
            
            // 验证邮箱格式
            if (!ValidationUtils.isValidEmail(request.email)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "邮箱格式不正确",
                    details = "请输入有效的邮箱地址"
                ))
            }
            
            // 模拟重置密码逻辑
            logger.i("AuthManager", "密码重置邮件已发送到: ${request.email}")
            Result.Success(Unit)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "重置密码失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "重置密码失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 修改密码
     */
    suspend fun changePassword(request: ChangePasswordRequest): Result<Unit> {
        return try {
            logger.i("AuthManager", "开始修改密码")
            
            // 验证输入
            if (!ValidationUtils.isValidPassword(request.newPassword)) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "新密码格式不正确",
                    details = "密码长度应在${Constants.PASSWORD_MIN_LENGTH}-${Constants.PASSWORD_MAX_LENGTH}位之间"
                ))
            }
            
            if (request.newPassword != request.confirmPassword) {
                return Result.Error(AppError(
                    code = Constants.ERROR_CODE_VALIDATION,
                    message = "密码确认不匹配",
                    details = "两次输入的新密码不一致"
                ))
            }
            
            // 模拟修改密码逻辑
            logger.i("AuthManager", "密码修改成功")
            Result.Success(Unit)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "修改密码失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "修改密码失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
    
    /**
     * 刷新用户令牌
     */
    suspend fun refreshToken(): Result<String> {
        return try {
            logger.i("AuthManager", "开始刷新用户令牌")
            
            // 模拟刷新令牌逻辑
            val newToken = "refreshed_token_${getCurrentTimeMillis()}"
            storage.putString(Constants.StorageKeys.USER_TOKEN, newToken)
            
            logger.i("AuthManager", "用户令牌刷新成功")
            Result.Success(newToken)
            
        } catch (e: Exception) {
            logger.e("AuthManager", "刷新令牌失败", e)
            Result.Error(AppError(
                code = Constants.ERROR_CODE_UNKNOWN,
                message = "刷新令牌失败",
                details = e.message,
                errorCause = e.getSimpleClassName()
            ))
        }
    }
}
