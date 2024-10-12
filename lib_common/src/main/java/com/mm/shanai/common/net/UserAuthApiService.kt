package com.mm.shanai.common.net

import com.mm.shanai.common.model.RefreshTokenResponse
import com.quyunshuo.wanandroid.common.bean.BaseResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Author: lzm
 * @Date: 2024-09-15 22:53
 * @Description: TODO
 */
interface UserAuthApiService {

    companion object {
        const val BASE_PATH = "/app-api/user/auth"
    }

    /**
     * 发送手机验证码
     */
    @POST("$BASE_PATH/send-sms-code")
    suspend fun sendLoginSmsCode(@Body body: RequestBody): BaseResponse<Boolean>

    /**
     * 使用账号密码登录（不需要 Token）
     */
    @POST("$BASE_PATH/login")
    suspend fun login(@Header("No-Auth") noAuth: Boolean = true): BaseResponse<String>

    /**
     * 退出登录
     */
    @POST("$BASE_PATH/logout")
    suspend fun logout(): BaseResponse<String>

    /**
     * 使用短信验证码登录
     */
    @POST("$BASE_PATH/sms-login")
    suspend fun smsLogin(): BaseResponse<String>

    /**
     * 社交快捷登录，使用 code 授权码
     */
    @POST("$BASE_PATH/social-login")
    suspend fun socialQuickLogin(): BaseResponse<String>

    /**
     * 刷新 Token
     */
    @POST("$BASE_PATH/refresh-token")
    fun refreshToken(@Header("Authorization") refreshToken: String): Call<RefreshTokenResponse>

}
