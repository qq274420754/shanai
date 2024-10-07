package com.shanai.account.activity

import android.text.InputType
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.addTextChangedListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.account.R
import com.shanai.module.account.databinding.AccountActivityPhoneloginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: lzm
 * @Date: 2024-10-03 21:11
 * @Description: TODO
 */
@Route(name = RouteKey.PHONELOGIN_PAGE, path = RouteUrl.PHONELOGIN)
@AndroidEntryPoint
class AccountPhoneLoginActivity : BaseActivity<AccountActivityPhoneloginBinding, AccountPhoneLoginVM>() {

    // 通过 Hilt 注入 ViewModel
    override val mViewModel: AccountPhoneLoginVM by viewModels()

    override fun createVB() = AccountActivityPhoneloginBinding.inflate(layoutInflater)

    override fun setStatusBar() {
        super.setStatusBar()
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.common_white)
            navigationBarColor(R.color.common_white) }
    }

    // 初始化视图
    override fun AccountActivityPhoneloginBinding.initView() {

        mBinding.lifecycleOwner = this@AccountPhoneLoginActivity // 确保 LiveData 可以感知生命周期
        mBinding.accountphonedata = mViewModel
        // 监听手机号输入变化
        mBinding.etPhone.addTextChangedListener {
            mViewModel.phonenumber.value = it.toString()
            mViewModel.updateCommitStatus()  // 在手机号变化后更新提交按钮状态
            mBinding.ivClean.visibility = if (it.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        }

        mBinding.etPsdphone.addTextChangedListener {
            mViewModel.phonenumber.value = it.toString()
            mViewModel.updatePsdCommitStatus()  // 在手机号变化后更新提交按钮状态
        }
        mBinding.etPsdpassword.addTextChangedListener {
            mViewModel.psdnumber.value = it.toString()
            mViewModel.updatePsdCommitStatus()  // 在手机号变化后更新提交按钮状态
        }

        // 监听同意协议的变化
        cbHint.setOnCheckedChangeListener { _, isChecked ->
            mViewModel.isAgree.value = isChecked
        }

        mBinding.tvUseagreement.apply {
            spanColor = "#333333"
        }

        // 执行返回操作
        mBinding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        //密文切换
        mBinding.ivPsdvisibility.setOnClickListener{
            // 检查当前的输入类型是否是密码隐藏的类型
            if (mBinding.etPsdpassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // 如果当前是密文密码，切换到明文显示
                mBinding.etPsdpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                // 可以选择更改图标，提示用户现在显示的是明文密码
                mBinding.ivPsdvisibility.setImageResource(R.drawable.common_pwd_visibility) // 假设这是显示明文的图标
            } else {
                // 如果当前是明文密码，切换回密文
                mBinding.etPsdpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                // 更改图标，提示用户现在显示的是密文密码
                mBinding.ivPsdvisibility.setImageResource(R.drawable.common_pwd_invisibility) // 假设这是隐藏密码的图标
            }
            // 为了确保光标保持在文本末尾
            mBinding.etPsdpassword.setSelection(mBinding.etPsdpassword.text.length)
        }
    }

    override fun initObserve() {
        // 观察手机号和是否同意协议的变化，更新提交按钮状态
        mViewModel.isCommitEnabled.observe(this) { isEnabled ->
            updateCodeCommitButtonState(isEnabled)
        }

        // 观察协议内容，更新到 UI
        mViewModel.privacyPolicyText.observe(this) { privacyPolicyText ->
            mBinding.tvUseagreement.setText(privacyPolicyText)
        }

        // 观察按钮状态，更新到 UI
        mViewModel.loginMode.observe(this) { loginMode ->
            if (loginMode){
                switchToPhoneLogin()
                mBinding.tvLogin.text = "验证码登录"
            }else{
                switchToPasswordLogin()
                mBinding.tvLogin.text = "账号密码登录"
            }
        }


    }

    override fun initRequestData() {
        // 加载隐私协议等初始数据
        mViewModel.loadPrivacyPolicy()
    }

    private fun updatePsdCommitButtonState(isEnabled: Boolean) {
        if (isEnabled) {
            mBinding.tvPsdcommit.apply {
                setBackgroundResource(R.drawable.account_getcode_bg)
                setOnClickListener { mViewModel.onPsdCommitClicked() }
            }
        } else {
            mBinding.tvPsdcommit.apply {
                setBackgroundResource(R.drawable.account_getcode_bg_n)
                setOnClickListener(null)
            }
        }
    }

    private fun updateCodeCommitButtonState(isEnabled: Boolean) {
        if (isEnabled) {
            mBinding.tvCodecommit.apply {
                setBackgroundResource(R.drawable.account_getcode_bg)
                setOnClickListener { mViewModel.onGetCodeClicked() }
            }
            mBinding.ivClean.visibility = View.VISIBLE
        } else {
            mBinding.tvCodecommit.apply {
                setBackgroundResource(R.drawable.account_getcode_bg_n)
                setOnClickListener(null)
            }
            mBinding.ivClean.visibility = View.INVISIBLE
        }
    }

    // 切换到密码登录的逻辑
    private fun switchToPasswordLogin() {
        mBinding.verifyloginGroup.visibility = View.GONE // 隐藏验证码登录布局
        mBinding.psdloginGroup.visibility = View.VISIBLE // 显示密码登录布局
        updatePrivacyLayoutPosition() // 更新协议布局位置
    }

    // 切换到手机号验证码登录的逻辑
    private fun switchToPhoneLogin() {
        mBinding.psdloginGroup.visibility = View.GONE // 隐藏密码登录布局
        mBinding.verifyloginGroup.visibility = View.VISIBLE // 显示验证码登录布局
        updatePrivacyLayoutPosition() // 更新协议布局位置
    }
    private fun updatePrivacyLayoutPosition() {
        val isPasswordLoginVisible = mBinding.psdloginGroup.visibility == View.VISIBLE
        val constraintSet = ConstraintSet()
        constraintSet.clone(mBinding.activityLogin)
        if (isPasswordLoginVisible) {
            // 协议在密码登录按钮下
            constraintSet.connect(R.id.layout_useagreement, ConstraintSet.TOP, R.id.tv_psdcommit, ConstraintSet.BOTTOM)
        } else {
            // 协议在验证码提交按钮下
            constraintSet.connect(R.id.layout_useagreement, ConstraintSet.TOP, R.id.tv_codecommit, ConstraintSet.BOTTOM)
        }
        constraintSet.applyTo(mBinding.activityLogin)
    }

}
