package com.example.kmpuniversalapp.viewmodel

// 暂时注释不存在的引用，避免编译错误
// import com.example.kmpuniversalapp.data.User
// import com.example.kmpuniversalapp.network.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// 暂时注释整个类，避免编译错误
/*
class UserViewModel {
    private val apiService = ApiService()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    suspend fun loadUsers() {
        _isLoading.value = true
        _error.value = null
        
        try {
            val userList = apiService.getUsers()
            _users.value = userList
        } catch (e: Exception) {
            _error.value = e.message ?: "加载用户失败"
        } finally {
            _isLoading.value = false
        }
    }
    
    suspend fun loadUserById(id: Int): User? {
        return try {
            apiService.getUserById(id)
        } catch (e: Exception) {
            _error.value = e.message ?: "加载用户失败"
            null
        }
    }
}
*/
