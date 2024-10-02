package com.shanai.shanai.module.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val userId: String,        // 用户ID (PrimaryKey 是主键)
    val nickname: String?,                 // 用户昵称
    val avatarUrl: String?,                // 用户头像URL
    val gender: String?,                   // 性别
    val level: Int = 1                     // 等级，默认为1
)


