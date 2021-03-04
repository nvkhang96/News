package com.nvkhang96.news.di

import com.nvkhang96.news.api.VnexpressService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideVnexpressService(): VnexpressService {
        return VnexpressService.create()
    }
}