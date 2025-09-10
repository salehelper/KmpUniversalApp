package com.example.kmpuniversalapp.navigation

// 暂时注释掉 NavigationManager，因为 Decompose Parcelable 类型问题
// TODO: 修复 Decompose 2.2.0 的 Parcelable 类型问题

/*

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.decompose.statekeeper.Parcelable
import com.arkivanov.decompose.statekeeper.Parcelize
import com.example.kmpuniversalapp.libs.utils.log.AppLogger

/**
 * 导航管理器
 * 基于URL路由的页面导航系统，支持参数传递
 */
interface NavigationManager {
    val stack: Value<ChildStack<*, Child>>
    
    /**
     * 导航到指定路由
     */
    fun navigateTo(route: Route)
    
    /**
     * 导航到指定URL
     */
    fun navigateToUrl(url: String)
    
    /**
     * 替换当前路由
     */
    fun replaceWith(route: Route)
    
    /**
     * 替换当前URL
     */
    fun replaceWithUrl(url: String)
    
    /**
     * 返回上一页
     */
    fun goBack()
    
    /**
     * 返回根页面
     */
    fun goToRoot()
    
    /**
     * 检查是否可以返回
     */
    fun canGoBack(): Boolean
    
    /**
     * 获取当前路由
     */
    fun getCurrentRoute(): Route?
    
    /**
     * 获取当前URL
     */
    fun getCurrentUrl(): String?
}

/**
 * 导航子组件
 */
sealed interface Child : Parcelable {
    @Parcelize
    data object Home : Child
    
    @Parcelize
    data object Search : Child
    
    @Parcelize
    data object Message : Child
    
    @Parcelize
    data object Profile : Child
    
    @Parcelize
    data class SearchDetail(
        val keyword: String,
        val page: Int = 1,
        val category: String? = null
    ) : Child
    
    @Parcelize
    data class MessageDetail(
        val messageId: String,
        val conversationId: String? = null
    ) : Child
    
    @Parcelize
    data class ProfileDetail(
        val userId: String,
        val tab: String? = null
    ) : Child
    
    @Parcelize
    data class BannerDetail(
        val bannerId: String,
        val title: String,
        val linkUrl: String? = null
    ) : Child
    
    @Parcelize
    data object Settings : Child
    
    @Parcelize
    data class SettingsDetail(
        val section: String,
        val item: String? = null
    ) : Child
    
    @Parcelize
    data object Login : Child
    
    @Parcelize
    data class Register(
        val inviteCode: String? = null
    ) : Child
}

/**
 * 默认导航管理器实现
 */
class DefaultNavigationManager(
    componentContext: ComponentContext
) : NavigationManager, ComponentContext by componentContext {
    
    private val navigation = StackNavigation<Child>()
    
    override val stack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Child.Home,
            handleBackButton = true,
            childFactory = ::child
        )
    
    private fun child(config: Child, componentContext: ComponentContext): Child {
        return when (config) {
            is Child.Home -> Child.Home
            is Child.Search -> Child.Search
            is Child.Message -> Child.Message
            is Child.Profile -> Child.Profile
            is Child.SearchDetail -> Child.SearchDetail(
                keyword = config.keyword,
                page = config.page,
                category = config.category
            )
            is Child.MessageDetail -> Child.MessageDetail(
                messageId = config.messageId,
                conversationId = config.conversationId
            )
            is Child.ProfileDetail -> Child.ProfileDetail(
                userId = config.userId,
                tab = config.tab
            )
            is Child.BannerDetail -> Child.BannerDetail(
                bannerId = config.bannerId,
                title = config.title,
                linkUrl = config.linkUrl
            )
            is Child.Settings -> Child.Settings
            is Child.SettingsDetail -> Child.SettingsDetail(
                section = config.section,
                item = config.item
            )
            is Child.Login -> Child.Login
            is Child.Register -> Child.Register(
                inviteCode = config.inviteCode
            )
        }
    }
    
    override fun navigateTo(route: Route) {
        AppLogger.d("NavigationManager", "Navigating to route: $route")
        
        val child = routeToChild(route)
        navigation.push(child)
    }
    
    override fun navigateToUrl(url: String) {
        AppLogger.d("NavigationManager", "Navigating to URL: $url")
        
        val route = RouteParser.parseRoute(url)
        if (route != null) {
            navigateTo(route)
        } else {
            AppLogger.e("NavigationManager", "Failed to parse URL: $url")
        }
    }
    
    override fun replaceWith(route: Route) {
        AppLogger.d("NavigationManager", "Replacing with route: $route")
        
        val child = routeToChild(route)
        navigation.replaceAll(child)
    }
    
    override fun replaceWithUrl(url: String) {
        AppLogger.d("NavigationManager", "Replacing with URL: $url")
        
        val route = RouteParser.parseRoute(url)
        if (route != null) {
            replaceWith(route)
        } else {
            AppLogger.e("NavigationManager", "Failed to parse URL: $url")
        }
    }
    
    override fun goBack() {
        AppLogger.d("NavigationManager", "Going back")
        navigation.pop()
    }
    
    override fun goToRoot() {
        AppLogger.d("NavigationManager", "Going to root")
        while (canGoBack()) {
            navigation.pop()
        }
    }
    
    override fun canGoBack(): Boolean {
        return stack.value.backStack.isNotEmpty()
    }
    
    override fun getCurrentRoute(): Route? {
        val currentChild = stack.value.active.instance
        return childToRoute(currentChild)
    }
    
    override fun getCurrentUrl(): String? {
        val route = getCurrentRoute()
        return route?.let { RouteParser.routeToUrl(it) }
    }
    
    /**
     * 将Route转换为Child
     */
    private fun routeToChild(route: Route): Child {
        return when (route) {
            is Route.Home -> Child.Home
            is Route.Search -> Child.Search
            is Route.Message -> Child.Message
            is Route.Profile -> Child.Profile
            is Route.SearchDetail -> Child.SearchDetail(
                keyword = route.keyword,
                page = route.page,
                category = route.category
            )
            is Route.MessageDetail -> Child.MessageDetail(
                messageId = route.messageId,
                conversationId = route.conversationId
            )
            is Route.ProfileDetail -> Child.ProfileDetail(
                userId = route.userId,
                tab = route.tab
            )
            is Route.BannerDetail -> Child.BannerDetail(
                bannerId = route.bannerId,
                title = route.title,
                linkUrl = route.linkUrl
            )
            is Route.Settings -> Child.Settings
            is Route.SettingsDetail -> Child.SettingsDetail(
                section = route.section,
                item = route.item
            )
            is Route.Login -> Child.Login
            is Route.Register -> Child.Register(
                inviteCode = route.inviteCode
            )
            is Route.Generic -> Child.Home // 默认跳转到首页
        }
    }
    
    /**
     * 将Child转换为Route
     */
    private fun childToRoute(child: Child): Route {
        return when (child) {
            is Child.Home -> Route.Home
            is Child.Search -> Route.Search
            is Child.Message -> Route.Message
            is Child.Profile -> Route.Profile
            is Child.SearchDetail -> Route.SearchDetail(
                keyword = child.keyword,
                page = child.page,
                category = child.category
            )
            is Child.MessageDetail -> Route.MessageDetail(
                messageId = child.messageId,
                conversationId = child.conversationId
            )
            is Child.ProfileDetail -> Route.ProfileDetail(
                userId = child.userId,
                tab = child.tab
            )
            is Child.BannerDetail -> Route.BannerDetail(
                bannerId = child.bannerId,
                title = child.title,
                linkUrl = child.linkUrl
            )
            is Child.Settings -> Route.Settings
            is Child.SettingsDetail -> Route.SettingsDetail(
                section = child.section,
                item = child.item
            )
            is Child.Login -> Route.Login
            is Child.Register -> Route.Register(
                inviteCode = child.inviteCode
            )
        }
    }
}
*/
