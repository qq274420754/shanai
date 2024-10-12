package com.shanai.module.home.di

import com.shanai.module.home.net.FriendApiService
import com.shanai.module.home.net.HomeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 全局作用域的Home组件网络接口代理依赖注入模块
 *
 * @author LZM
 * @since 6/4/21 5:51 PM
 */
@Module
@InstallIn(SingletonComponent::class)
class DIHomeNetServiceModule {

    /**
     * Home模块的[HomeApiService]依赖提供方法
     *
     * @param retrofit Retrofit
     * @return HomeApiService
     */
    @Singleton
    @Provides
    fun provideHomeApiService(retrofit: Retrofit): HomeApiService {
        return retrofit.create(HomeApiService::class.java)
    }

    /**
     * Home模块的[FriendApiService]依赖提供方法
     *
     * @param retrofit Retrofit
     * @return HomeApiService
     */
    @Singleton
    @Provides
    fun provideFriendApiService(retrofit: Retrofit): FriendApiService {
        return retrofit.create(FriendApiService::class.java)
    }
}