package com.example.kmpuniversalapp.search

import com.example.kmpuniversalapp.search.SearchResultModel
import com.example.kmpuniversalapp.search.SearchApiService
import com.example.kmpuniversalapp.libs.utils.log.AppLogger
import com.example.kmpuniversalapp.core.BaseViewModel
import com.arkivanov.essenty.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
    private val searchApiService: SearchApiService,
    private val logger: AppLogger,
    lifecycle: Lifecycle
) : BaseViewModel(lifecycle) {
    
    // 搜索关键词
    private val _searchKeyword = MutableStateFlow("")
    val searchKeyword: StateFlow<String> = _searchKeyword.asStateFlow()
    
    // 搜索结果
    private val _searchResults = MutableStateFlow<List<SearchResultModel>>(emptyList())
    val searchResults: StateFlow<List<SearchResultModel>> = _searchResults.asStateFlow()
    
    // 搜索建议
    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions.asStateFlow()
    
    // 搜索历史
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()
    
    // 热门搜索
    private val _hotSearches = MutableStateFlow<List<String>>(emptyList())
    val hotSearches: StateFlow<List<String>> = _hotSearches.asStateFlow()
    
    // 加载状态
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()
    
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()
    
    // 分页信息
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()
    
    private val _totalResults = MutableStateFlow(0)
    val totalResults: StateFlow<Int> = _totalResults.asStateFlow()
    
    private val _hasMore = MutableStateFlow(false)
    val hasMore: StateFlow<Boolean> = _hasMore.asStateFlow()
    
    // 是否显示搜索结果
    private val _showSearchResults = MutableStateFlow(false)
    val showSearchResults: StateFlow<Boolean> = _showSearchResults.asStateFlow()
    
    fun loadSearchHistory() {
        submit {
            try {
                val history = searchApiService.getSearchHistory()
                _searchHistory.value = history
                setSuccess("搜索历史加载成功")
            } catch (e: Exception) {
                handleException(e, "加载搜索历史失败")
            }
        }
    }
    
    fun loadHotSearches() {
        submit {
            try {
                val hotSearches = searchApiService.getHotSearches()
                _hotSearches.value = hotSearches
                setSuccess("热门搜索加载成功")
            } catch (e: Exception) {
                handleException(e, "加载热门搜索失败")
            }
        }
    }
    
    fun setSearchKeyword(keyword: String) {
        _searchKeyword.value = keyword
        if (keyword.isNotEmpty()) {
            performSearch()
        } else {
            clearSearchResults()
        }
    }
    
    fun clearSearchKeyword() {
        _searchKeyword.value = ""
        clearSearchResults()
    }
    
    private fun performSearch() {
        if (_searchKeyword.value.trim().isEmpty()) return
        
        submit {
            try {
                _isSearching.value = true
                _currentPage.value = 1
                
                // 并行获取搜索建议和执行搜索
                val suggestionsDeferred = execute { 
                    searchApiService.getSearchSuggestions(_searchKeyword.value.trim()) 
                }
                val searchDeferred = execute { 
                    searchApiService.search(
                        keyword = _searchKeyword.value.trim(),
                        page = _currentPage.value,
                        pageSize = 10
                    )
                }
                
                // 等待结果
                val suggestions = suggestionsDeferred.await()
                val result = searchDeferred.await()
                
                _suggestions.value = suggestions
                _totalResults.value = result["total"] as Int
                _hasMore.value = result["hasMore"] as Boolean
                _searchResults.value = result["results"] as List<SearchResultModel>
                
                // 显示搜索结果
                _showSearchResults.value = true
                
                updateStats()
                setSuccess("搜索完成")
                
            } catch (e: Exception) {
                handleException(e, "搜索失败")
            } finally {
                _isSearching.value = false
            }
        }
    }
    
    
    fun loadMoreResults() {
        if (!_hasMore.value || _isLoadingMore.value) return
        
        submit {
            try {
                _isLoadingMore.value = true
            _currentPage.value = _currentPage.value + 1
            
                val result = searchApiService.search(
                    keyword = _searchKeyword.value.trim(),
                    page = _currentPage.value,
                    pageSize = 10
                )
                
                val results = result["results"] as List<SearchResultModel>
                _searchResults.value = _searchResults.value + results
                _hasMore.value = result["hasMore"] as Boolean
                
                updateStats()
                setSuccess("加载更多成功")
                
            } catch (e: Exception) {
                handleException(e, "加载更多失败")
                _currentPage.value = _currentPage.value - 1
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
    
    fun refreshSearchResults() {
        if (_searchKeyword.value.trim().isEmpty()) return
        
        _currentPage.value = 1
        performSearch()
    }
    
    fun clearSearchResults() {
        _searchResults.value = emptyList()
        _currentPage.value = 1
        _totalResults.value = 0
        _hasMore.value = false
        _showSearchResults.value = false
    }
    
    suspend fun selectSuggestion(suggestion: String) {
        setSearchKeyword(suggestion)
    }
    
    suspend fun selectHistory(history: String) {
        setSearchKeyword(history)
    }
    
    suspend fun selectHotSearch(hotSearch: String) {
        setSearchKeyword(hotSearch)
    }
    
    fun onSearchResultTap(result: SearchResultModel) {
        // 处理搜索结果点击
        println("点击了搜索结果: ${result.title}")
    }
    
    // 搜索统计信息
    private val _searchStats = MutableStateFlow("")
    val searchStats: StateFlow<String> = _searchStats.asStateFlow()
    
    // 分页信息
    private val _paginationInfo = MutableStateFlow("")
    val paginationInfo: StateFlow<String> = _paginationInfo.asStateFlow()
    
    private fun updateStats() {
        _searchStats.value = if (_totalResults.value == 0) "" else "共找到 ${_totalResults.value} 条结果"
        _paginationInfo.value = if (_totalResults.value == 0) "" else "第 ${_currentPage.value} 页"
    }
}
