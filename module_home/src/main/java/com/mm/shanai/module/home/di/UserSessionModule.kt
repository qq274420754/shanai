package com.mm.shanai.module.home.di

/**
 * @Author: lzm
 * @Date: 2024-09-17 23:05
 * @Description: TODO
 */
import com.mm.shanai.module.home.provider.UserSessionProviderImpl
import com.mm.shanai.common.provider.UserSessionProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserSessionModule {

    @Binds
    @Singleton
    abstract fun bindUserSessionProvider(
        impl: UserSessionProviderImpl
    ): UserSessionProvider
}
