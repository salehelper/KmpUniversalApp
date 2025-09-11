package com.example.kmpuniversalapp.presentation.navigation

/**
 * 应用屏幕路由定义
 * 参考 Translation-KMP 的设计，使用密封类定义所有屏幕
 */
sealed class AppScreen(val title: String, val route: String) {
    // 主页面
    data object Home : AppScreen("首页", "nav_home")
    data object Search : AppScreen("搜索", "nav_search")
    data object Message : AppScreen("消息", "nav_message")
    data object Profile : AppScreen("个人", "nav_profile")
    
    // 详情页面 - 支持参数传递
    data object SearchDetail : AppScreen("搜索详情", "nav_search_detail?keyword={keyword}&page={page}&category={category}")
    data object MessageDetail : AppScreen("消息详情", "nav_message_detail?messageId={messageId}&conversationId={conversationId}")
    data object ProfileDetail : AppScreen("个人详情", "nav_profile_detail?userId={userId}&tab={tab}")
    data object BannerDetail : AppScreen("横幅详情", "nav_banner_detail?bannerId={bannerId}&title={title}&linkUrl={linkUrl}")
    
    // 设置页面
    data object Settings : AppScreen("设置", "nav_settings")
    data object SettingsDetail : AppScreen("设置详情", "nav_settings_detail?section={section}&item={item}")
    
    // 认证页面
    data object Login : AppScreen("登录", "nav_login")
    data object Register : AppScreen("注册", "nav_register?inviteCode={inviteCode}")
    
    // 其他页面
    data object About : AppScreen("关于", "nav_about")
    data object Help : AppScreen("帮助", "nav_help")
    data object Feedback : AppScreen("反馈", "nav_feedback")
    
    companion object {
        /**
         * 用于状态保存的序列化器
         */
        val Saver = { screen: AppScreen ->
            screen.route
        }
        
        /**
         * 用于状态恢复的反序列化器
         */
        val Restorer = { str: String ->
            when (str) {
                Home.route -> Home
                Search.route -> Search
                Message.route -> Message
                Profile.route -> Profile
                Settings.route -> Settings
                Login.route -> Login
                Register.route -> Register
                About.route -> About
                Help.route -> Help
                Feedback.route -> Feedback
                else -> Home // 默认返回首页
            }
        }
    }
}

/**
 * 路由常量
 */
object AppRoutes {
    // 主页面
    const val HOME = "nav_home"
    const val SEARCH = "nav_search"
    const val MESSAGE = "nav_message"
    const val PROFILE = "nav_profile"
    
    // 详情页面
    const val SEARCH_DETAIL = "nav_search_detail"
    const val MESSAGE_DETAIL = "nav_message_detail"
    const val PROFILE_DETAIL = "nav_profile_detail"
    const val BANNER_DETAIL = "nav_banner_detail"
    
    // 设置页面
    const val SETTINGS = "nav_settings"
    const val SETTINGS_DETAIL = "nav_settings_detail"
    
    // 认证页面
    const val LOGIN = "nav_login"
    const val REGISTER = "nav_register"
    
    // 其他页面
    const val ABOUT = "nav_about"
    const val HELP = "nav_help"
    const val FEEDBACK = "nav_feedback"
}

/**
 * 路由参数解析工具
 */
object RouteParser {
    /**
     * 解析查询参数
     */
    fun parseQueryString(queryString: String?): Map<String, String> {
        if (queryString.isNullOrBlank()) return emptyMap()
        
        return queryString.split("&").associate { param ->
            val (key, value) = param.split("=", limit = 2)
            key to (value ?: "")
        }
    }
    
    /**
     * 移除查询参数，只保留路径
     */
    fun String.removeQuery(): String {
        return this.substringBefore("?")
    }
    
    /**
     * 构建带参数的URL
     */
    fun buildUrl(baseRoute: String, params: Map<String, Any> = emptyMap()): String {
        if (params.isEmpty()) return baseRoute
        
        val queryString = params.entries.joinToString("&") { (key, value) ->
            "$key=$value"
        }
        return "$baseRoute?$queryString"
    }
}

