package com.shanai.shanai.module.home.vm

import androidx.lifecycle.ViewModel
import com.shanai.shanai.module.home.reppo.UserRepository

/**
 * @Author: lzm
 * @Date: 2024-09-17 22:23
 * @Description: TODO
 */

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    // 登录用户
    fun loginUser(userId: String) {
        userRepository.loginUser(userId)
    }

    // 获取所有已登录用户
    fun getAllUsers(): Set<String> {
        return userRepository.getAllUsers()
    }

    // 获取当前登录用户
    fun getCurrentUserId(): String? {
        return userRepository.getCurrentUserId()
    }

    // 切换用户
    fun switchUser(userId: String, onResult: (Boolean) -> Unit) {
        val success = userRepository.switchUser(userId)
        onResult(success)
    }

    // 删除用户
    fun deleteUser(userId: String) {
        userRepository.deleteUser(userId)
    }
}
