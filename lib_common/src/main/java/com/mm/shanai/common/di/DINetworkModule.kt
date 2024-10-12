package com.shanai.common.di

import android.content.Context
import com.shanai.base.constant.VersionStatus
import com.shanai.common.BuildConfig
import com.shanai.common.constant.NetBaseUrlConstant
import com.mm.shanai.common.interceptor.AuthInterceptor
import com.mm.shanai.common.interceptor.CustomHeaderInterceptor
import com.mm.shanai.common.net.SettingApiService
import com.mm.shanai.common.net.UserAuthApiService
import com.mm.shanai.common.provider.UserTokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * 全局作用域的网络层的依赖注入模块
 *
 * @author LZM
 * @since 6/4/21 8:58 AM
 */
@Module
@InstallIn(SingletonComponent::class)
class DINetworkModule {

    @Singleton
    @Provides
    fun provideUserTokenProvider(): UserTokenProvider {
        return UserTokenProvider
    }
    /**
     * [OkHttpClient]依赖提供方法
     *
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor,customHeaderInterceptor: CustomHeaderInterceptor): OkHttpClient {
        // 日志拦截器部分
        val level = if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) BODY else NONE
        val logInterceptor = HttpLoggingInterceptor().setLevel(level)

        return OkHttpClient.Builder()
            .connectTimeout(15L * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(20L * 1000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15L * 1000L, TimeUnit.MILLISECONDS)
            .addInterceptor(logInterceptor)  //  日志打印拦截器
            .addInterceptor(authInterceptor)  // 添加 Token 拦截器
            .addInterceptor(customHeaderInterceptor)  // 添加 自定义 拦截器
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * 项目主要服务器地址的[Retrofit]依赖提供方法
     *
     * @param okHttpClient OkHttpClient OkHttp客户端
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun provideMainRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetBaseUrlConstant.MAIN_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /**
     * 提供 `SettingApiService` 实例
     */
    @Singleton
    @Provides
    fun provideSettingApiService(retrofit: Retrofit): SettingApiService {
        return retrofit.create(SettingApiService::class.java)
    }

    /**
     * 提供 `UserAuthApiService` 实例
     */
    @Singleton
    @Provides
    fun provideUserAuthApiService(retrofit: Retrofit): UserAuthApiService {
        return retrofit.create(UserAuthApiService::class.java)
    }

}