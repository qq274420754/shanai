package com.shanai.shanai.common.model

/**
 * @Author: lzm
 * @Date: 2024-09-15 22:35
 * @Description: TODO
 */
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String // 通常 Refresh Token 也会一起更新
)
