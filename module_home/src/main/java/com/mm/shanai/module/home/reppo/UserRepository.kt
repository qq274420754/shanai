package com.mm.shanai.module.home.reppo


import com.mm.shanai.module.home.manager.SessionManager

/**
 * @Author: lzm
 * @Date: 2024-09-17 21:42
 * @Description: TODO
 */
class UserRepository {

    // 保存登录用户，并设置为当前用户
    fun loginUser(userId: String) {
        SessionManager.saveLoggedInUser(userId)
        SessionManager.setCurrentUserId(userId)
    }

    // 获取所有已登录用户
    fun getAllUsers(): Set<String> {
        return SessionManager.getLoggedInUsers()
    }

    // 获取当前登录用户
    fun getCurrentUserId(): String? {
        return SessionManager.getCurrentUserId()
    }

    // 切换用户
    fun switchUser(userId: String): Boolean {
        return SessionManager.switchUser(userId)
    }

    // 删除用户
    fun deleteUser(userId: String) {
        SessionManager.removeUser(userId)
    }
}

