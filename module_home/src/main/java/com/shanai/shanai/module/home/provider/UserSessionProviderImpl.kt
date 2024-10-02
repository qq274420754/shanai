package com.shanai.shanai.module.home.provider


import com.shanai.shanai.common.provider.UserSessionProvider
import com.shanai.shanai.module.home.manager.SessionManager
import javax.inject.Inject

/**
 * @Author: lzm
 * @Date: 2024-09-17 23:05
 * @Description: TODO
 */
class UserSessionProviderImpl @Inject constructor() : UserSessionProvider {
    override fun getCurrentUserId(): String? {
        return SessionManager.getCurrentUserId()
    }
}
