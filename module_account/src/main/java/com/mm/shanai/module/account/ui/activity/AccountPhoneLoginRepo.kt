package com.mm.shanai.module.account.ui.activity

import com.mm.shanai.common.net.UserAuthApiService
import com.mm.shanai.common.utils.RetrofitUtil
import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import okhttp3.RequestBody
import javax.inject.Inject

/**
 *
 *
 * @author LZM
 * @since 5/25/21 5:42 PM
 */
class AccountPhoneLoginRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var userAuthApiService: UserAuthApiService

    /**
     * 获取登录注册验证码
     */
    suspend fun fetchRegisterSmsCode(phoneNumber:String) = request<Boolean> {
        val paramsMap = HashMap<String, Any>()
        paramsMap["region"] = 86
        paramsMap["mobile"] = phoneNumber //电话
        paramsMap["scene"] = 31//发送场景
        val body: RequestBody = RetrofitUtil.createJsonRequest(paramsMap)
        userAuthApiService.sendLoginSmsCode(body).run {
            responseCodeExceptionHandler(code, msg)
            emit(data)
        }
    }


}