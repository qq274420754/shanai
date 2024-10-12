package com.mm.shanai.module.account.ui.activity

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
class AccountVerifyCodeRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var settingApiService : SettingApiService

   
}