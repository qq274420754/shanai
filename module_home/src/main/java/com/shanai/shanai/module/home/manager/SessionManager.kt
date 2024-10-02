package com.shanai.shanai.module.home.manager

import com.shanai.base.utils.SpUtils
import com.shanai.shanai.module.home.constant.MMKVKeys

/**
 * @Author: lzm
 * @Date: 2024-09-17 17:59
 * @Description: TODO
 */
object SessionManager {

    // 保存所有已登录用户的 userId 集合
    fun saveLoggedInUser(userId: String) {
        val userIds = SpUtils.getStringSet(MMKVKeys.ALL_USERS, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        userIds.add(userId)
        SpUtils.putStringSet(MMKVKeys.ALL_USERS, userIds)
    }

    // 获取所有已登录的用户
    fun getLoggedInUsers(): Set<String> {
        return SpUtils.getStringSet(MMKVKeys.ALL_USERS, mutableSetOf()) ?: mutableSetOf()
    }

    // 保存当前登录用户的 userId
    fun setCurrentUserId(userId: String) {
        SpUtils.putString(MMKVKeys.CURRENT_USER_ID, userId)
    }

    // 获取当前登录用户的 userId
    fun getCurrentUserId(): String? {
        return SpUtils.getString(MMKVKeys.CURRENT_USER_ID, "")
    }

    // 切换当前用户
    fun switchUser(userId: String): Boolean {
        val userIds = getLoggedInUsers()
        return if (userIds.contains(userId)) {
            setCurrentUserId(userId)
            true
        } else {
            false
        }
    }

    // 删除用户
    fun removeUser(userId: String) {
        val userIds = getLoggedInUsers().toMutableSet()
        userIds.remove(userId)
        SpUtils.putStringSet(MMKVKeys.ALL_USERS, userIds)

        // 如果删除的是当前用户，清空当前用户信息
        if (getCurrentUserId() == userId) {
            SpUtils.cleanValue(MMKVKeys.CURRENT_USER_ID)
        }
    }
}


