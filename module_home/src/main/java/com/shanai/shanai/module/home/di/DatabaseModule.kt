package com.shanai.shanai.module.home.di


import android.content.Context
import androidx.room.Room
import com.shanai.shanai.module.home.dao.UserDao
import com.shanai.shanai.module.home.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
/**
 * @Author: lzm
 * @Date: 2024-09-17 21:43
 * @Description: TODO
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "shanai_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }
}
