package com.mm.shanai.module.account.ui.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.shanai.base.mvvm.vm.BaseViewModel
import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.RouteKey.Params.KEY_LOGIN_PHONENUMBER
import com.shanai.common.constant.RouteUrl
import com.shanai.common.constant.SpKey.KEY_PRIVACY_URL
import com.shanai.common.constant.SpKey.KEY_PROTOCOL_URL
import com.shanai.base.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountPhoneLoginVM @Inject constructor(
    private val accoutphoneloginrepo: AccountPhoneLoginRepo
) : BaseViewModel() {

    val phonenumber = MutableLiveData<String>("") // 手机号
    val psdnumber = MutableLiveData<String>() // 密码
    val isAgree = MutableLiveData<Boolean>(false) // 是否同意协议
    val showAgree = MutableLiveData<Boolean>(false) // 是否同意协议
    val loginMode = MutableLiveData<Boolean>(true) // 登录页面展示（密码登录false/验证码登录true）
    val isCommitEnabled = MutableLiveData<Boolean>() // 获取验证码提交按钮状态
    val isPsdCommitEnabled = MutableLiveData<Boolean>() // 密码登录提交按钮状态
    val privacyPolicyText = MutableLiveData<String>() // 同意协议文本内容
    val dialogprivacyPolicyText = MutableLiveData<String>() // 弹窗的协议文本内容

    init {
        // 初始化状态
        updateCommitStatus()
        updatePsdCommitStatus()
    }

    // 每当手机号或是否同意协议变化时更新提交按钮状态
    fun updateCommitStatus() {
        isCommitEnabled.value = (!phonenumber.value.isNullOrEmpty())
    }

    fun updatePsdCommitStatus(){
        isPsdCommitEnabled.value = ( (!phonenumber.value.isNullOrEmpty() && !psdnumber.value.isNullOrEmpty()))
    }

    // 获取验证码点击事件
    fun onGetCodeClicked() {
        if (isAgree.value == false){
            showAgree.value = (true)
            return
        }
        if (!phonenumber.value.isNullOrEmpty()) {// && isAgree.value == true
            // 执行获取验证码逻辑
            viewModelScope.launch(Dispatchers.IO){
                accoutphoneloginrepo.fetchRegisterSmsCode(phonenumber.value!!)
                    .catch { e ->
                        e.message?.let { toast(it) }
                    }
                    .collect{
                        ARouter.getInstance().build(RouteUrl.PHONEVERIFYCODE).withString(KEY_LOGIN_PHONENUMBER,phonenumber.value).navigation()
                    }
            }
        }
    }
    //是否同意弹窗协议内容
    fun onPolicyDialogResult(Agree:Boolean) {
        showAgree.value = false
        isAgree.value = Agree
        if (Agree){
            onGetCodeClicked()
        }else{
            toast("请先查看并同意用户协议")
        }
    }

    //密码登录
    fun onPsdCommitClicked() {

    }
    fun onCleanPsdClicked() {
        psdnumber.value = ""
    }
    // 清除手机号点击事件
    fun onCleanPhoneClicked() {
        phonenumber.value = ""
    }

    fun onSwitchModeClicked() {
        // 处理密码登录以及验证码切换逻辑
        loginMode.value = loginMode.value != true

    }

    // 加载隐私协议和使用协议的文本
    fun loadPrivacyPolicy() {
        val privacyUrl = SpUtils.getString(KEY_PRIVACY_URL, "")
        val protocolUrl = SpUtils.getString(KEY_PROTOCOL_URL, "")
        val policyText = """阅读并同意<a href="$protocolUrl">《使用协议》</a><a href="$privacyUrl"> 《隐私政策》</a>，除青少年模式外，未满18岁不得使用本APP""".trimIndent()
        privacyPolicyText.value = (policyText)
        val doalogpolicyText = """闪爱重视并致力于保障您的个人隐私，我们根据监管要求更新了 <a href="$protocolUrl">《使用协议》</a>和<a href="$privacyUrl"> 《隐私政策》 </a>，特说明如下:

1.为更好的帮您找到心仪的朋友，会根据您设置的择偶条件来向您做推荐:
2.为了查看附近的用户，我们需要使用您的位置信息，您可以随时开启或关闭此项授权:
3.您可以随时访问、更正、删除您的个人信息，我们也提供了注销和反馈的渠道:
4.未经您同意，我们不会从第三方获取、共享或向其提供您的信息:
5.点击同意即表示您已阅读并同意全部条款。

我们非常重视您的个人信息保护。关于个人信息收集和使用的详细信息，您可以点击:<a href="$protocolUrl">《使用协议》</a> 和<a href="$privacyUrl"> 《隐私政策》 </a> 进行了解。""".trimIndent()
        dialogprivacyPolicyText.value = (doalogpolicyText)

    }
}
