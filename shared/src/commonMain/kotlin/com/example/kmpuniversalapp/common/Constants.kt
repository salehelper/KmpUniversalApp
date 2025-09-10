package com.example.kmpuniversalapp.common

/**
 * 通用常量
 */
object Constants {
    
    // 网络相关
    object Network {
        const val CONNECT_TIMEOUT = 30_000L
        const val READ_TIMEOUT = 30_000L
        const val WRITE_TIMEOUT = 30_000L
        const val MAX_RETRY_COUNT = 3
    }
    
    // 分页相关
    object Pagination {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 100
        const val INITIAL_PAGE = 1
    }
    
    // 缓存相关
    object Cache {
        const val DEFAULT_CACHE_SIZE = 50 * 1024 * 1024L // 50MB
        const val CACHE_EXPIRE_TIME = 24 * 60 * 60 * 1000L // 24小时
        const val MAX_CACHE_AGE = 7 * 24 * 60 * 60 * 1000L // 7天
    }
    
    // 存储键名
    object StorageKeys {
        const val USER_TOKEN = "user_token"
        const val USER_INFO = "user_info"
        const val APP_SETTINGS = "app_settings"
        const val THEME_MODE = "theme_mode"
        const val LANGUAGE = "language"
        const val FIRST_LAUNCH = "first_launch"
    }
    
    // 正则表达式
    object Regex {
        const val EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        const val PHONE = "^1[3-9]\\d{9}$"
        const val PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{8,}$"
    }
    
    // 日期格式
    object DateFormat {
        const val DEFAULT = "yyyy-MM-dd HH:mm:ss"
        const val DATE_ONLY = "yyyy-MM-dd"
        const val TIME_ONLY = "HH:mm:ss"
        const val ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
    
    // 文件相关
    object File {
        const val MAX_FILE_SIZE = 10 * 1024 * 1024L // 10MB
        const val ALLOWED_IMAGE_TYPES = "jpg,jpeg,png,gif,webp"
        const val ALLOWED_DOCUMENT_TYPES = "pdf,doc,docx,txt"
    }
    
    // 动画时长
    object Animation {
        const val SHORT_DURATION = 200L
        const val MEDIUM_DURATION = 300L
        const val LONG_DURATION = 500L
    }
    
    // 错误码
    object ErrorCode {
        const val NETWORK_ERROR = 1001
        const val PARSE_ERROR = 1002
        const val UNKNOWN_ERROR = 1003
        const val AUTH_ERROR = 1004
        const val PERMISSION_ERROR = 1005
    }
}
