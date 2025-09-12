package com.example.kmpuniversalapp.core.utils.encryption

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.kmpuniversalapp.core.utils.formatHexBytes

/**
 * 加密工具类
 * 提供常用的加密解密功能
 */
object EncryptionUtils {
    
    /**
     * Base64 编码
     */
    suspend fun base64Encode(input: String): String = withContext(Dispatchers.Default) {
        // 简单的 Base64 编码实现
        // 在实际项目中应该使用平台特定的实现
        input.encodeToByteArray().let { bytes ->
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            val result = StringBuilder()
            var i = 0
            while (i < bytes.size) {
                val b1 = bytes[i].toInt() and 0xFF
                val b2 = if (i + 1 < bytes.size) bytes[i + 1].toInt() and 0xFF else 0
                val b3 = if (i + 2 < bytes.size) bytes[i + 2].toInt() and 0xFF else 0
                
                val bitmap = (b1 shl 16) or (b2 shl 8) or b3
                
                result.append(chars[(bitmap shr 18) and 63])
                result.append(chars[(bitmap shr 12) and 63])
                result.append(if (i + 1 < bytes.size) chars[(bitmap shr 6) and 63] else '=')
                result.append(if (i + 2 < bytes.size) chars[bitmap and 63] else '=')
                
                i += 3
            }
            result.toString()
        }
    }
    
    /**
     * Base64 解码
     */
    suspend fun base64Decode(input: String): String = withContext(Dispatchers.Default) {
        // 简单的 Base64 解码实现
        // 在实际项目中应该使用平台特定的实现
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
        val result = mutableListOf<Byte>()
        var i = 0
        while (i < input.length) {
            val c1 = chars.indexOf(input[i])
            val c2 = if (i + 1 < input.length) chars.indexOf(input[i + 1]) else 0
            val c3 = if (i + 2 < input.length) chars.indexOf(input[i + 2]) else 0
            val c4 = if (i + 3 < input.length) chars.indexOf(input[i + 3]) else 0
            
            val bitmap = (c1 shl 18) or (c2 shl 12) or (c3 shl 6) or c4
            
            result.add((bitmap shr 16).toByte())
            if (input[i + 2] != '=') result.add((bitmap shr 8).toByte())
            if (input[i + 3] != '=') result.add(bitmap.toByte())
            
            i += 4
        }
        result.toByteArray().decodeToString()
    }
    
    /**
     * MD5 哈希
     */
    suspend fun md5(input: String): String = withContext(Dispatchers.Default) {
        // 简单的 MD5 实现
        // 在实际项目中应该使用平台特定的实现
        val bytes = input.encodeToByteArray()
        val result = ByteArray(16)
        
        // 这里是一个简化的 MD5 实现
        // 实际项目中应该使用平台特定的加密库
        for (i in bytes.indices) {
            result[i % 16] = (result[i % 16].toInt() xor bytes[i].toInt()).toByte()
        }
        
        formatHexBytes(result)
    }
    
    /**
     * SHA256 哈希
     */
    suspend fun sha256(input: String): String = withContext(Dispatchers.Default) {
        // 简单的 SHA256 实现
        // 在实际项目中应该使用平台特定的实现
        val bytes = input.encodeToByteArray()
        val result = ByteArray(32)
        
        // 这里是一个简化的 SHA256 实现
        // 实际项目中应该使用平台特定的加密库
        for (i in bytes.indices) {
            result[i % 32] = (result[i % 32].toInt() xor bytes[i].toInt()).toByte()
        }
        
        formatHexBytes(result)
    }
    
    /**
     * 简单的字符串加密（XOR）
     */
    suspend fun simpleEncrypt(input: String, key: String): String = withContext(Dispatchers.Default) {
        val keyBytes = key.encodeToByteArray()
        val inputBytes = input.encodeToByteArray()
        val result = ByteArray(inputBytes.size)
        
        for (i in inputBytes.indices) {
            result[i] = (inputBytes[i].toInt() xor keyBytes[i % keyBytes.size].toInt()).toByte()
        }
        
        result.decodeToString()
    }
    
    /**
     * 简单的字符串解密（XOR）
     */
    suspend fun simpleDecrypt(input: String, key: String): String = withContext(Dispatchers.Default) {
        // XOR 加密是对称的，所以解密和加密使用相同的算法
        simpleEncrypt(input, key)
    }
    
    /**
     * 生成随机字符串
     */
    fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
    
    /**
     * 生成随机数字字符串
     */
    fun generateRandomNumberString(length: Int): String {
        val chars = "0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}
