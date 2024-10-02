package com.shanai.shanai.module.home.db

/**
 * @Author: lzm
 * @Date: 2024-09-17 21:41
 * @Description: TODO
 */

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shanai.shanai.module.home.dao.UserDao
import com.shanai.shanai.module.home.entity.User

@Database(entities = [User::class], version = 1 )
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
