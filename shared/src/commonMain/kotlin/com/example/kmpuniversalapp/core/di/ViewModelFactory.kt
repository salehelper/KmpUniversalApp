package com.example.kmpuniversalapp.core.di

import com.example.kmpuniversalapp.features.home.HomeViewModel
import com.example.kmpuniversalapp.features.search.SearchViewModel
import com.example.kmpuniversalapp.features.account.AccountViewModel
import com.example.kmpuniversalapp.features.message.MessageViewModel
import com.example.kmpuniversalapp.features.profile.ProfileViewModel

/**
 * ViewModel Factory接口
 * 定义跨平台的ViewModel创建方法
 */
interface ViewModelFactory {
    fun createHomeViewModel(): HomeViewModel
    fun createSearchViewModel(): SearchViewModel
    fun createAccountViewModel(): AccountViewModel
    fun createMessageViewModel(): MessageViewModel
    fun createProfileViewModel(): ProfileViewModel
}
