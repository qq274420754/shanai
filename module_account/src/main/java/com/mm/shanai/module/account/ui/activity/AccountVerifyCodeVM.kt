package com.mm.shanai.module.account.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.SpKey.KEY_PRIVACY_URL
import com.shanai.common.constant.SpKey.KEY_PROTOCOL_URL
import com.mm.shanai.common.model.PrivacyAgreement
import com.mm.shanai.common.model.SysParamBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountVerifyCodeVM @Inject constructor(
    private val accoutphoneloginrepo: AccountVerifyCodeRepo
) : BaseViewModel() {

    val phonenumber = MutableLiveData<String>() // 手机号
    val isAgree = MutableLiveData<Boolean>() // 是否同意协议
    val isCommitEnabled = MutableLiveData<Boolean>() // 提交按钮状态
    val privacyPolicyText = MutableLiveData<String>() // 协议文本内容

    init {
        // 初始化状态
        isAgree.value = false
        phonenumber.value = ""
        updateCommitStatus()
    }

    // 每当手机号或是否同意协议变化时更新提交按钮状态
    fun updateCommitStatus() {
        isCommitEnabled.value = !phonenumber.value.isNullOrEmpty()
    }

    // 获取验证码点击事件
    fun onGetCodeClicked() {
        if (!phonenumber.value.isNullOrEmpty() && isAgree.value == true) {
            // 执行获取验证码逻辑

        }
    }

    fun onCleanPhoneClicked() {
        phonenumber.value = "";
    }


    fun onLoginClicked() {
        // 处理密码登录逻辑

    }

    // 加载隐私协议和使用协议的文本
    fun loadPrivacyPolicy() {
        val privacyUrl = SpUtils.getString(KEY_PRIVACY_URL, "")
        val protocolUrl = SpUtils.getString(KEY_PROTOCOL_URL, "")
        val policyText = """阅读并同意<a href="$protocolUrl">《使用协议》</a><a href="$privacyUrl"> 《隐私政策》</a>，除青少年模式外，未满18岁不得使用本APP""".trimIndent()
        privacyPolicyText.postValue(policyText)
    }
}
