package com.shanai.shanai.common.net

import com.shanai.shanai.common.model.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Author: lzm
 * @Date: 2024-09-15 22:53
 * @Description: TODO
 */
interface SystemApiService {

    // 定义基础路径前缀
    companion object {
        const val BASE_PATH = "/system/auth"
    }

    // 使用账号密码登录（不需要 Token）
    @POST("$BASE_PATH/login")
    fun login(@Header("No-Auth") noAuth: Boolean = true): Call<String>

    // 退出登录
    @POST("$BASE_PATH/logout")
    fun logout(): Call<String>

    // 发送手机验证码
    @POST("$BASE_PATH/send-sms-code")
    fun sendLoginSmsCode(): Call<String>

    // 使用短信验证码登录
    @POST("$BASE_PATH/sms-login")
    fun smsLogin(): Call<String>

    // 社交快捷登录，使用 code 授权码
    @POST("$BASE_PATH/social-login")
    fun socialQuickLogin(): Call<String>

    // 刷新 Token
    @POST("$BASE_PATH/refresh-token")
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<RefreshTokenResponse>

}
