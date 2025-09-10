package com.example.kmpuniversalapp.di

import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.kmpuniversalapp.account.AccountViewModel
import com.example.kmpuniversalapp.home.HomeViewModel
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import com.example.kmpuniversalapp.message.MessageViewModel
import com.example.kmpuniversalapp.profile.ProfileViewModel
import com.example.kmpuniversalapp.search.SearchViewModel
import com.example.kmpuniversalapp.home.HomeApiService
import com.example.kmpuniversalapp.search.SearchApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Web平台特定的ViewModel Factory
 * 解决Web平台上ViewModel需要Lifecycle参数的问题
 */
object WebViewModelFactory : ViewModelFactory, KoinComponent {

    private val homeApiService: HomeApiService by inject()
    private val searchApiService: SearchApiService by inject()
    private val logger: AppLogger by inject()

    /**
     * 创建Web平台特定的Lifecycle实例
     */
    private fun createWebLifecycle(): Lifecycle {
        return LifecycleRegistry()
    }

    /**
     * 创建HomeViewModel
     */
    override fun createHomeViewModel(): HomeViewModel {
        return HomeViewModel(
            homeApiService = homeApiService,
            logger = logger,
            lifecycle = createWebLifecycle()
        )
    }

    /**
     * 创建SearchViewModel
     */
    override fun createSearchViewModel(): SearchViewModel {
        return SearchViewModel(
            searchApiService = searchApiService,
            logger = logger,
            lifecycle = createWebLifecycle()
        )
    }

    /**
     * 创建AccountViewModel
     */
    override fun createAccountViewModel(): AccountViewModel {
        return AccountViewModel(
            logger = logger,
            lifecycle = createWebLifecycle()
        )
    }

    /**
     * 创建MessageViewModel
     */
    override fun createMessageViewModel(): MessageViewModel {
        return MessageViewModel(
            logger = logger,
            lifecycle = createWebLifecycle()
        )
    }

    /**
     * 创建ProfileViewModel
     */
    override fun createProfileViewModel(): ProfileViewModel {
        return ProfileViewModel(
            logger = logger,
            lifecycle = createWebLifecycle()
        )
    }
}





