package com.mm.shanai.module.account.ui.activity

import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.account.R
import com.shanai.module.account.databinding.AccountActivityVerifycodeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: lzm
 * @Date: 2024-10-03 21:11
 * @Description: TODO
 */
@Route(name = RouteKey.PHONE_VERIFYCODE_PAGE, path = RouteUrl.PHONEVERIFYCODE)
@AndroidEntryPoint
class AccountVerifyCodeActivity : BaseActivity<AccountActivityVerifycodeBinding, AccountVerifyCodeVM>() {

    @Autowired(name = RouteKey.Params.KEY_LOGIN_PHONENUMBER)
    lateinit var phonenumber:String;

    // 通过 Hilt 注入 ViewModel
    override val mViewModel: AccountVerifyCodeVM by viewModels()

    override fun createVB() = AccountActivityVerifycodeBinding.inflate(layoutInflater)

    override fun setStatusBar() {
        super.setStatusBar()
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.common_white)
            navigationBarColor(R.color.common_white) }
    }

    // 初始化视图
    override fun AccountActivityVerifycodeBinding.initView() {

    }

    override fun initObserve() {



    }

    override fun initRequestData() {
    }



}
