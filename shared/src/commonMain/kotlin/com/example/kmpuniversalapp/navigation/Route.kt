package com.example.kmpuniversalapp.navigation

import kotlinx.serialization.Serializable

/**
 * 路由定义
 * 支持URL参数传递，实现页面间解耦
 */
@Serializable
sealed class Route {
    
    // 主页面路由
    @Serializable
    data object Home : Route()
    
    @Serializable
    data object Search : Route()
    
    @Serializable
    data object Message : Route()
    
    @Serializable
    data object Profile : Route()
    
    // 详情页面路由 - 支持参数传递
    @Serializable
    data class SearchDetail(
        val keyword: String,
        val page: Int = 1,
        val category: String? = null
    ) : Route()
    
    @Serializable
    data class MessageDetail(
        val messageId: String,
        val conversationId: String? = null
    ) : Route()
    
    @Serializable
    data class ProfileDetail(
        val userId: String,
        val tab: String? = null
    ) : Route()
    
    @Serializable
    data class BannerDetail(
        val bannerId: String,
        val title: String,
        val linkUrl: String? = null
    ) : Route()
    
    // 设置页面路由
    @Serializable
    data object Settings : Route()
    
    @Serializable
    data class SettingsDetail(
        val section: String,
        val item: String? = null
    ) : Route()
    
    // 登录相关路由
    @Serializable
    data object Login : Route()
    
    @Serializable
    data class Register(
        val inviteCode: String? = null
    ) : Route()
    
    // 通用路由 - 支持任意参数
    @Serializable
    data class Generic(
        val path: String,
        val params: Map<String, String> = emptyMap()
    ) : Route()
}

/**
 * 路由参数解析器
 * 解析URL参数并转换为Route对象
 */
object RouteParserOld {
    
    /**
     * 解析URL路径和参数
     * 支持格式: /path?param1=value1&param2=value2
     */
    fun parseRoute(url: String): Route? {
        return try {
            val (path, queryString) = url.split("?", limit = 2)
            val params = parseQueryString(queryString)
            
            when (path) {
                "/", "/home" -> Route.Home
                "/search" -> Route.Search
                "/message" -> Route.Message
                "/profile" -> Route.Profile
                "/settings" -> Route.Settings
                "/login" -> Route.Login
                "/register" -> Route.Register(params["inviteCode"])
                
                "/search/detail" -> Route.SearchDetail(
                    keyword = params["keyword"] ?: "",
                    page = params["page"]?.toIntOrNull() ?: 1,
                    category = params["category"]
                )
                
                "/message/detail" -> Route.MessageDetail(
                    messageId = params["messageId"] ?: "",
                    conversationId = params["conversationId"]
                )
                
                "/profile/detail" -> Route.ProfileDetail(
                    userId = params["userId"] ?: "",
                    tab = params["tab"]
                )
                
                "/banner/detail" -> Route.BannerDetail(
                    bannerId = params["bannerId"] ?: "",
                    title = params["title"] ?: "",
                    linkUrl = params["linkUrl"]
                )
                
                "/settings/detail" -> Route.SettingsDetail(
                    section = params["section"] ?: "",
                    item = params["item"]
                )
                
                else -> Route.Generic(path, params)
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * 将Route转换为URL字符串
     */
    fun routeToUrl(route: Route): String {
        return when (route) {
            is Route.Home -> "/home"
            is Route.Search -> "/search"
            is Route.Message -> "/message"
            is Route.Profile -> "/profile"
            is Route.Settings -> "/settings"
            is Route.Login -> "/login"
            is Route.Register -> "/register${if (route.inviteCode != null) "?inviteCode=${route.inviteCode}" else ""}"
            
            is Route.SearchDetail -> buildString {
                append("/search/detail")
                append("?keyword=${route.keyword}")
                append("&page=${route.page}")
                route.category?.let { append("&category=$it") }
            }
            
            is Route.MessageDetail -> buildString {
                append("/message/detail")
                append("?messageId=${route.messageId}")
                route.conversationId?.let { append("&conversationId=$it") }
            }
            
            is Route.ProfileDetail -> buildString {
                append("/profile/detail")
                append("?userId=${route.userId}")
                route.tab?.let { append("&tab=$it") }
            }
            
            is Route.BannerDetail -> buildString {
                append("/banner/detail")
                append("?bannerId=${route.bannerId}")
                append("&title=${route.title}")
                route.linkUrl?.let { append("&linkUrl=$it") }
            }
            
            is Route.SettingsDetail -> buildString {
                append("/settings/detail")
                append("?section=${route.section}")
                route.item?.let { append("&item=$it") }
            }
            
            is Route.Generic -> buildString {
                append(route.path)
                if (route.params.isNotEmpty()) {
                    append("?")
                    append(route.params.entries.joinToString("&") { "${it.key}=${it.value}" })
                }
            }
        }
    }
    
    /**
     * 解析查询字符串
     */
    private fun parseQueryString(queryString: String?): Map<String, String> {
        if (queryString.isNullOrBlank()) return emptyMap()
        
        return queryString.split("&").associate { param ->
            val (key, value) = param.split("=", limit = 2)
            key to (value ?: "")
        }
    }
}

/**
 * 路由常量
 */
object Routes {
    const val HOME = "/home"
    const val SEARCH = "/search"
    const val MESSAGE = "/message"
    const val PROFILE = "/profile"
    const val SETTINGS = "/settings"
    const val LOGIN = "/login"
    const val REGISTER = "/register"
    
    // 详情页面路由
    const val SEARCH_DETAIL = "/search/detail"
    const val MESSAGE_DETAIL = "/message/detail"
    const val PROFILE_DETAIL = "/profile/detail"
    const val BANNER_DETAIL = "/banner/detail"
    const val SETTINGS_DETAIL = "/settings/detail"
}
