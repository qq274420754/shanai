package com.shanai.shanai.common.provider

/**
 * @Author: lzm
 * @Date: 2024-09-17 22:51
 * @Description: TODO
 */

import com.shanai.base.utils.SpUtils

object UserTokenProvider {
    private const val ACCESS_TOKEN = "access_token_"
    private const val REFRESH_TOKEN = "refresh_token_"

    /**
     * 保存用户Token
     *
     * @param userId String 用户ID
     * @param accessToken String AccessToken
     * @param refreshToken String RefreshToken
     */
    fun saveUserToken(userId: String, accessToken: String, refreshToken: String) {
        SpUtils.putString(ACCESS_TOKEN + userId, accessToken)
        SpUtils.putString(REFRESH_TOKEN + userId, refreshToken)
    }

    /**
     * 获取用户的AccessToken
     *
     * @param userId String 用户ID
     * @return String? AccessToken
     */
    fun getUserAccessToken(userId: String): String? {
        return SpUtils.getString(ACCESS_TOKEN + userId, "")
    }

    /**
     * 获取用户的RefreshToken
     *
     * @param userId String 用户ID
     * @return String? RefreshToken
     */
    fun getUserRefreshToken(userId: String): String? {
        return SpUtils.getString(REFRESH_TOKEN + userId, "")
    }

    /**
     * 清除用户的Token
     *
     * @param userId String 用户ID
     */
    fun clearTokens(userId: String) {
        SpUtils.cleanValue(ACCESS_TOKEN + userId)
        SpUtils.cleanValue(REFRESH_TOKEN + userId)
    }
}
