package com.shanai.shanai.module.home.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import com.shanai.shanai.module.home.entity.User
import kotlinx.coroutines.flow.Flow


/**
 * @Author: lzm
 * @Date: 2024-09-17 21:36
 * @Description: TODO
 */
@Dao
interface UserDao {

    // 插入一个用户，onConflictStrategy.REPLACE 表示如果用户已经存在就替换
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    // 获取所有用户信息
    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>  // 使用 Flow 实时返回用户列表

    // 通过userId 获取用户信息
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserById(userId: String): User?

    // 删除用户
    @Delete
    suspend fun deleteUser(user: User)

    // 删除所有用户
    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}
