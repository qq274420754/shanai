package com.mm.shanai.module.home.ui.activity

import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import com.mm.shanai.common.model.PrivacyAgreement
import com.mm.shanai.common.model.SysParamBean
import com.mm.shanai.common.net.SettingApiService
import javax.inject.Inject

/**
 *
 *
 * @author LZM
 * @since 5/25/21 5:42 PM
 */
class SplashRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var settingApiService : SettingApiService

    /**
     * 获取隐私协议内容
     */
    suspend fun fetchPrivacyAgreement() = request<PrivacyAgreement> {
        settingApiService.getPrivacyAgreement().run {
            responseCodeExceptionHandler(code, msg)
            emit(data)
        }
    }

    /**
     * 获取系统配置信息
     */
    suspend fun fetchSysParam() = request<SysParamBean> {
        settingApiService.getSysParam().run {
            responseCodeExceptionHandler(code, msg)
            emit(data)
        }
    }
}