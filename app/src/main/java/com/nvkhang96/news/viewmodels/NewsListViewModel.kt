package com.nvkhang96.news.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvkhang96.news.data.NewsRepository
import com.nvkhang96.news.data.VnexpressRssResponseWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val _newsList = MutableLiveData<VnexpressRssResponseWrapper>()
    val newsList = _newsList

    init {
        fetchLatestNews()
    }

    fun fetchLatestNews() {
        viewModelScope.launch {
            val latestNews = newsRepository.fetchNews()
            newsList.postValue(latestNews)
        }
    }
}