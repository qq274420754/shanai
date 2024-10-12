package com.mm.shanai.module.account.ui.activity

import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.addTextChangedListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.mm.shanai.module.account.ui.activity.AccountPhoneLoginVM
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

        setupListeners()

        // 返回操作
        mBinding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        // 密码可见/隐藏切换
        mBinding.ivPsdvisibility.setOnClickListener { togglePasswordVisibility() }

    }

    private fun AccountActivityPhoneloginBinding.setupListeners() {
        etPhone.addTextChangedListener {
            mViewModel.phonenumber.value = it.toString()
            updateCommitButtonState()
        }
        etPsdphone.addTextChangedListener {
            mViewModel.phonenumber.value = it.toString()
            mViewModel.updatePsdCommitStatus()
        }
        etPsdpassword.addTextChangedListener {
            updatePasswordView(it.isNullOrEmpty())
            mViewModel.psdnumber.value = it.toString()
            mViewModel.updatePsdCommitStatus()
        }
        ivClean.setOnClickListener { etPhone.text.clear() }
        ivPsdclean.setOnClickListener { etPsdpassword.text.clear() }
        cbHint.setOnCheckedChangeListener { _, isChecked -> mViewModel.isAgree.value = isChecked }
        tvContent.apply {
            spanColor = "#000000"
            movementMethod = ScrollingMovementMethod()
        }
        tvUseagreement.apply { spanColor = "#000000" }
    }

    override fun initObserve() {
        mViewModel.isCommitEnabled.observe(this) { updateCodeCommitButtonState(it) }
        mViewModel.isPsdCommitEnabled.observe(this) { updatePsdCommitButtonState(it) }
        mViewModel.showAgree.observe(this) {
            if (it) {
                hideSoftKeyboard()
                immersionBar {
                    fitsSystemWindows(false)
                    transparentBar()
                    navigationBarColor(R.color.common_white)
                }
            }else{
                setStatusBar()
            }
        }

        // 观察登录模式切换
        mViewModel.loginMode.observe(this) { isPhoneLogin ->
            switchLoginMode(isPhoneLogin)
            mBinding.tvLogin.text = if (isPhoneLogin) "验证码登录" else "账号密码登录"
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

    private fun switchLoginMode(isPhoneLogin: Boolean) {
        if (isPhoneLogin) {
            switchToPhoneLogin()
        } else {
            switchToPasswordLogin()
        }
        updatePrivacyLayoutPosition()
    }

    // 切换到密码登录的逻辑
    private fun switchToPasswordLogin() {
        mBinding.verifyloginGroup.visibility = View.GONE // 隐藏验证码登录布局
        mBinding.psdloginGroup.visibility = View.VISIBLE // 显示密码登录布局
        updatePrivacyLayoutPosition() // 更新协议布局位置
    }

    // 切换到手机号验证码登录的逻辑
    private fun switchToPhoneLogin() {
        mBinding.verifyloginGroup.visibility = View.VISIBLE // 显示验证码登录布局
        mBinding.psdloginGroup.visibility = View.GONE // 隐藏密码登录布局
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


    private fun updatePasswordView(isEmpty: Boolean) {
        mBinding.ivPsdclean.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
        mBinding.ivPsdvisibility.visibility = if (isEmpty) View.INVISIBLE else View.VISIBLE
    }

    private fun updateCommitButtonState() {
        // 检查手机号是否为空，并且是否同意协议
        val isPhoneNotEmpty = !mViewModel.phonenumber.value.isNullOrEmpty()
//        val isAgreeChecked = mViewModel.isAgree.value == true

        // 如果手机号非空且同意协议，则启用提交按钮
        mBinding.tvCodecommit.apply {
            if (isPhoneNotEmpty ) {//&& isAgreeChecked
                setBackgroundResource(R.drawable.account_getcode_bg)  // 可点击状态背景
                isEnabled = true  // 启用提交按钮
                setOnClickListener { mViewModel.onGetCodeClicked() }  // 绑定点击事件
            } else {
                setBackgroundResource(R.drawable.account_getcode_bg_n)  // 不可点击状态背景
                isEnabled = false  // 禁用提交按钮
                setOnClickListener(null)  // 移除点击事件
            }
        }

        // 同时更新清除按钮的可见性
        mBinding.ivClean.visibility = if (isPhoneNotEmpty) View.VISIBLE else View.INVISIBLE
    }


    private fun togglePasswordVisibility() {
        val inputType = if (mBinding.etPsdpassword.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        mBinding.etPsdpassword.inputType = inputType
        mBinding.etPsdpassword.setSelection(mBinding.etPsdpassword.text.length)
        mBinding.ivPsdvisibility.setImageResource(if (inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) R.drawable.common_pwd_visibility else R.drawable.common_pwd_invisibility)
    }


}
