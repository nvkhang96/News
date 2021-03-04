package com.nvkhang96.news.api

import com.nvkhang96.news.data.VnexpressRssResponseWrapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET

interface VnexpressService {

    @GET("rss/tin-moi-nhat.rss")
    suspend fun fetchLatestRssNews(): VnexpressRssResponseWrapper

    companion object {

        private const val BASE_URL = "https://vnexpress.net/"

        fun create(): VnexpressService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(VnexpressService::class.java)
        }
    }

}