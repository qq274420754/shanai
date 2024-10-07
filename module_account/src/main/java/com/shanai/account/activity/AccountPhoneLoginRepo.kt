package com.shanai.account.activity

import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import com.shanai.shanai.common.model.PrivacyAgreement
import com.shanai.shanai.common.model.SysParamBean
import com.shanai.shanai.common.net.SettingApiService
import javax.inject.Inject

/**
 *
 *
 * @author LZM
 * @since 5/25/21 5:42 PM
 */
class AccountPhoneLoginRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var settingApiService : SettingApiService

   
}