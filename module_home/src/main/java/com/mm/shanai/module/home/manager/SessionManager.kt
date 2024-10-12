package com.mm.shanai.module.home.manager

import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.SpKey

/**
 * @Author: lzm
 * @Date: 2024-09-17 17:59
 * @Description: TODO
 */
object SessionManager {

    // 保存所有已登录用户的 userId 集合
    fun saveLoggedInUser(userId: String) {
        val userIds = SpUtils.getStringSet(SpKey.ALL_USERS, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        userIds.add(userId)
        SpUtils.putStringSet(SpKey.ALL_USERS, userIds)
    }

    // 获取所有已登录的用户
    fun getLoggedInUsers(): Set<String> {
        return SpUtils.getStringSet(SpKey.ALL_USERS, mutableSetOf()) ?: mutableSetOf()
    }

    // 保存当前登录用户的 userId
    fun setCurrentUserId(userId: String) {
        SpUtils.putString(SpKey.CURRENT_USER_ID, userId)
    }

    // 获取当前登录用户的 userId
    fun getCurrentUserId(): String? {
        return SpUtils.getString(SpKey.CURRENT_USER_ID, "")
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
        SpUtils.putStringSet(SpKey.ALL_USERS, userIds)

        // 如果删除的是当前用户，清空当前用户信息
        if (getCurrentUserId() == userId) {
            SpUtils.cleanValue(SpKey.CURRENT_USER_ID)
        }
    }

    // 判断是否有用户已登录
    fun isUserLoggedIn(): Boolean {
        return getCurrentUserId().isNullOrEmpty().not() // 只要当前有登录的用户，返回true
    }

    // 登出用户
    fun logoutCurrentUser() {
        val currentUserId = getCurrentUserId()
        if (currentUserId != null) {
            removeUser(currentUserId)
        }
    }
}


