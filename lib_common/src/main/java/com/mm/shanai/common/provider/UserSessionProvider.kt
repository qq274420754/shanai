package com.mm.shanai.common.provider

/**
 * @Author: lzm
 * @Date: 2024-09-17 23:04
 * @Description: TODO
 */
interface UserSessionProvider {
    fun getCurrentUserId(): String?
}
