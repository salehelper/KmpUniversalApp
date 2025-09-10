package com.example.kmpuniversalapp.navigation

// 暂时注释掉 Decompose 导航相关代码，因为 Parcelable 类型问题
// TODO: 修复 Decompose 2.2.0 的 Parcelable 类型问题

/*
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.serialization.Serializable

/**
 * 使用Decompose的导航组件
 * Decompose是专为KMP设计的导航库，比自定义导航更稳定、功能更完善
 */
interface AppComponent {
    val stack: Value<ChildStack<*, Child>>
    
    fun onTabSelected(index: Int)
    
    sealed interface Child {
        @Serializable
        data object Home : Child
        
        @Serializable
        data object Search : Child
        
        @Serializable
        data object Message : Child
        
        @Serializable
        data object Profile : Child
    }
}

class DefaultAppComponent(
    componentContext: ComponentContext
) : AppComponent, ComponentContext by componentContext {
    
    private val navigation = StackNavigation<AppComponent.Child>()
    
    override val stack: Value<ChildStack<*, AppComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = AppComponent.Child.Home,
            handleBackButton = true,
            childFactory = ::child
        )
    
    private fun child(config: AppComponent.Child, componentContext: ComponentContext): AppComponent.Child {
        return when (config) {
            is AppComponent.Child.Home -> AppComponent.Child.Home
            is AppComponent.Child.Search -> AppComponent.Child.Search
            is AppComponent.Child.Message -> AppComponent.Child.Message
            is AppComponent.Child.Profile -> AppComponent.Child.Profile
        }
    }
    
    override fun onTabSelected(index: Int) {
        val destination = when (index) {
            0 -> AppComponent.Child.Home
            1 -> AppComponent.Child.Search
            2 -> AppComponent.Child.Message
            3 -> AppComponent.Child.Profile
            else -> return
        }
        
        navigation.push(destination)
    }
    
    init {
        lifecycle.doOnCreate {
            // 初始化逻辑
        }
        
        lifecycle.doOnDestroy {
            // 清理逻辑
        }
    }
}
*/
