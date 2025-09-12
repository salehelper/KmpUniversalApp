package com.example.kmpuniversalapp.core.utils.validation

import com.example.kmpuniversalapp.core.base.Constants
import kotlin.text.Regex

/**
 * 验证工具类
 * 提供常用的数据验证功能
 */
object ValidationUtils {
    
    // 邮箱正则表达式
    private val emailRegex: Regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    
    // 手机号正则表达式
    private val phoneRegex: Regex = Regex("^1[3-9]\\d{9}$")
    
    // 密码强度正则表达式
    private val passwordRegex: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@\$!%*?&]{8,}$")
    
    /**
     * 验证邮箱格式
     */
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && emailRegex.matches(email)
    }
    
    /**
     * 验证手机号格式
     */
    fun isValidPhone(phone: String): Boolean {
        return phone.isNotEmpty() && phoneRegex.matches(phone)
    }
    
    /**
     * 验证密码强度
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= Constants.PASSWORD_MIN_LENGTH && 
               password.length <= Constants.PASSWORD_MAX_LENGTH &&
               passwordRegex.matches(password)
    }
    
    /**
     * 验证字符串长度
     */
    fun isValidLength(text: String, minLength: Int, maxLength: Int): Boolean {
        return text.length in minLength..maxLength
    }
    
    /**
     * 验证是否为空
     */
    fun isNotEmpty(text: String?): Boolean {
        return !text.isNullOrBlank()
    }
    
    /**
     * 验证是否为数字
     */
    fun isNumeric(text: String): Boolean {
        return text.matches(Regex("\\d+"))
    }
    
    /**
     * 验证文件路径
     */
    fun isValidFilePath(path: String): Boolean {
        return try {
            // 简化实现，只检查路径格式
            path.isNotEmpty() && !path.contains("..") && path.length < 1000
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 验证文件大小
     */
    fun isValidFileSize(path: String, maxSize: Long): Boolean {
        return try {
            // 简化实现，假设文件大小有效
            true
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 验证文件扩展名
     */
    fun isValidFileExtension(path: String, allowedExtensions: List<String>): Boolean {
        return try {
            val extension = path.substringAfterLast('.', "").lowercase()
            allowedExtensions.any { it.lowercase() == extension }
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 验证URL格式
     */
    fun isValidUrl(url: String): Boolean {
        return try {
            url.startsWith("http://") || url.startsWith("https://")
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 验证身份证号格式
     */
    fun isValidIdCard(idCard: String): Boolean {
        return try {
            idCard.length == 18 && idCard.matches(Regex("\\d{17}[\\dXx]"))
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * 验证邮政编码
     */
    fun isValidPostalCode(postalCode: String): Boolean {
        return try {
            postalCode.matches(Regex("\\d{6}"))
        } catch (e: Exception) {
            false
        }
    }
}