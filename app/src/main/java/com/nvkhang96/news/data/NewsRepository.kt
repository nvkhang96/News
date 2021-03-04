package com.nvkhang96.news.data

import com.nvkhang96.news.api.VnexpressService
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val vnexpressService: VnexpressService
) {

    suspend fun fetchNews() = vnexpressService.fetchLatestRssNews()
}