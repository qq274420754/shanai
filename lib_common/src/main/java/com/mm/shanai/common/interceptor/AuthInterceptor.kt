package com.mm.shanai.common.interceptor

import com.shanai.common.constant.NetBaseUrlConstant
import com.mm.shanai.common.provider.UserSessionProvider
import com.mm.shanai.common.provider.UserTokenProvider
import com.mm.shanai.common.net.UserAuthApiService
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * @Author: lzm
 * @Date: 2024-09-15 22:31
 * @Description: TODO
 */
class AuthInterceptor @Inject constructor(
    private val userSessionProvider: UserSessionProvider,
    private val userTokenProvider: UserTokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 获取当前登录用户的 userId
        val userId = userSessionProvider.getCurrentUserId()

        if (userId.isNullOrEmpty()) {
            return chain.proceed(request)
        }
        // 检查请求头中是否有 "No-Auth" 标志
        val noAuth = request.header("No-Auth")
        if (noAuth == null || noAuth != "true") {
            // 没有 No-Auth 标志，说明需要 Token
            val accessToken = userTokenProvider.getUserAccessToken(userId)
            if (accessToken != null) {
                // 在请求头中加入 Authorization: Bearer <AccessToken>
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $accessToken")
                    .build()
            }
        }

        val response = chain.proceed(request)

        // 如果返回 401 Unauthorized，尝试刷新 Token
        if (response.code == 401 && (noAuth == null || noAuth != "true")) {
            synchronized(this) {
                // 尝试刷新 Access Token
                val newAccessToken = refreshToken()
                if (newAccessToken != null) {
                    // 重新构建请求
                    val newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer $newAccessToken")
                        .build()

                    return chain.proceed(newRequest)
                }
            }
        }

        return response
    }

    // 使用 Refresh Token 获取新的 Access Token
    private fun refreshToken(): String? {
        // 获取当前登录用户的 userId
        val userId = userSessionProvider.getCurrentUserId()
        val refreshToken = userId?.let { userTokenProvider.getUserRefreshToken(it) }
        if (refreshToken != null) {
            // 发起网络请求以获取新的 Access Token
            val retrofit = Retrofit.Builder()
                .baseUrl(NetBaseUrlConstant.MAIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(UserAuthApiService::class.java)
            val refreshTokenResponse = service.refreshToken("Bearer $refreshToken").execute()

            if (refreshTokenResponse.isSuccessful) {
                val newAccessToken = refreshTokenResponse.body()?.accessToken
                newAccessToken?.let {
                    userTokenProvider.saveUserToken(userId,newAccessToken, refreshToken)
                }
                return newAccessToken
            }
        }
        return null
    }
}

