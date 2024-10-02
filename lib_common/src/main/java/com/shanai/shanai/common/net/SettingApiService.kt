package com.shanai.shanai.common.net

import com.quyunshuo.wanandroid.common.bean.BaseResponse
import com.shanai.shanai.common.model.PrivacyAgreement
import com.shanai.shanai.common.model.RefreshTokenResponse
import com.shanai.shanai.common.model.SysParamBean
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @Author: lzm
 * @Date: 2024-09-14 15:41
 * @Description: TODO
 */
interface SettingApiService {

    // 定义基础路径前缀
    companion object {
        const val BASE_PATH = "/app-api/setting"
    }
    /**
     * 获取隐私协议等参数
     */
    @GET("$BASE_PATH/privacy-agreement")
    suspend fun getPrivacyAgreement(): BaseResponse<PrivacyAgreement>


    /**
     * 获取客户端需要的基本配置信息
     */
    @GET("$BASE_PATH/sys-param")
    suspend fun getSysParam(): BaseResponse<SysParamBean>




}