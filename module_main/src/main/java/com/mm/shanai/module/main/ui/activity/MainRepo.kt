package com.mm.shanai.module.main.ui.activity

import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import com.mm.shanai.common.model.SysParamBean
import com.mm.shanai.common.net.SettingApiService
import javax.inject.Inject

/**
 * 首页M层
 *
 * @author LZM
 * @since 5/25/21 5:42 PM
 */
class MainRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var settingApiService : SettingApiService

    /**
     * 获取系统配置信息
     */
    suspend fun fetchSysParam()= request<SysParamBean> {
        settingApiService.getSysParam().run {
            responseCodeExceptionHandler(code, msg)
            emit(data)
        }
    }

}