package com.shanai.account.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.RouteKey.Params.KEY_LOGIN_PHONENUMBER
import com.shanai.common.constant.RouteUrl
import com.shanai.common.constant.SpKey.KEY_PRIVACY_URL
import com.shanai.common.constant.SpKey.KEY_PROTOCOL_URL
import com.shanai.shanai.common.model.PrivacyAgreement
import com.shanai.shanai.common.model.SysParamBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountPhoneLoginVM @Inject constructor(
    private val accoutphoneloginrepo: AccountPhoneLoginRepo
) : BaseViewModel() {

    val phonenumber = MutableLiveData<String>() // 手机号
    val psdnumber = MutableLiveData<String>() // 密码
    val isAgree = MutableLiveData<Boolean>() // 是否同意协议
    val loginMode = MutableLiveData<Boolean>() // 登录页面展示（密码登录false/验证码登录true）
    val isCommitEnabled = MutableLiveData<Boolean>() // 提交按钮状态
    val isPsdCommitEnabled = MutableLiveData<Boolean>() // 密码提交按钮状态
    val privacyPolicyText = MutableLiveData<String>() // 协议文本内容

    init {
        // 初始化状态
        isAgree.value = false
        loginMode.value = true
        phonenumber.value = ""
        psdnumber.value = ""
        updateCommitStatus()
        updatePsdCommitStatus()
    }

    // 每当手机号或是否同意协议变化时更新提交按钮状态
    fun updateCommitStatus() {
        isCommitEnabled.value = !phonenumber.value.isNullOrEmpty()
    }

    fun updatePsdCommitStatus(){
        isPsdCommitEnabled.value = (!phonenumber.value.isNullOrEmpty() && !psdnumber.value.isNullOrEmpty())
    }

    // 获取验证码点击事件
    fun onGetCodeClicked() {
        if (!phonenumber.value.isNullOrEmpty() && isAgree.value == true) {
            // 执行获取验证码逻辑
            ARouter.getInstance().build(RouteUrl.PHONEVERIFYCODE).withString(KEY_LOGIN_PHONENUMBER,phonenumber.value).navigation()
        }
    }

    //密码登录
    fun onPsdCommitClicked() {

    }
    fun onCleanPsdClicked() {

    }
    // 清除手机号点击事件
    fun onCleanPhoneClicked() {
        phonenumber.value = "";
    }

    fun onSwitchModeClicked() {
        // 处理密码登录以及验证码切换逻辑
        if (loginMode.value == true){
            loginMode.value = false
        }else{
            loginMode.value = true
        }
    }

    // 加载隐私协议和使用协议的文本
    fun loadPrivacyPolicy() {
        val privacyUrl = SpUtils.getString(KEY_PRIVACY_URL, "")
        val protocolUrl = SpUtils.getString(KEY_PROTOCOL_URL, "")
        val policyText = """阅读并同意<a href="$protocolUrl">《使用协议》</a><a href="$privacyUrl"> 《隐私政策》</a>，除青少年模式外，未满18岁不得使用本APP""".trimIndent()
        privacyPolicyText.postValue(policyText)
    }
}
