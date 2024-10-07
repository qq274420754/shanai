package com.shanai.module.home.ui.activity

import android.view.View
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ktx.immersionBar
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.home.R
import com.shanai.module.home.databinding.HomeActivityWebBinding
import com.shanai.shanai.module.home.ui.activity.WebVM
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import dagger.hilt.android.AndroidEntryPoint

@Route(name = RouteKey.WEB_PAGE, path = RouteUrl.WEB)
@AndroidEntryPoint
class WebActivity : BaseActivity<HomeActivityWebBinding, WebVM>(){

    @Autowired(name = RouteKey.Params.WEB_URL)
    lateinit var webUrl:String;

    // 通过 Hilt 注入 ViewModel
    override val mViewModel: WebVM by viewModels()

    override fun createVB() = HomeActivityWebBinding.inflate(layoutInflater)

    // 初始化视图
    override fun HomeActivityWebBinding.initView() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarColor(R.color.common_white)
            navigationBarColor(R.color.common_white) }

        ivTitleGoback.setOnClickListener {
            // WebView返回
            if (mBinding.webview.canGoBack()) {
                mBinding.webview.goBack()
            } else {
                finish()
            }
        }

        ivReload.setOnClickListener {
            // 刷新WebView
            mBinding.webview.reload()
        }

        ivTitleClose.setOnClickListener {
            // 关闭WebView
            finish()
        }
        // 初始化WebView
        initWebView()
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    private fun initWebView() {

        // 设置 WebViewClient 来拦截加载的页面
        mBinding.webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // 页面开始加载时显示进度条
                mBinding.webviewProgress.visibility = View.VISIBLE

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // 页面加载完成时隐藏进度条
                mBinding.webviewProgress.visibility = View.GONE
                // 设置标题为网页标题
                mBinding.tvTitle.text = mBinding.webview.title
                mBinding.webview.canGoBack()

            }

            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {

                return super.shouldOverrideUrlLoading(p0, p1)
            }

            override fun onPageCommitVisible(p0: WebView?, p1: String?) {
                super.onPageCommitVisible(p0, p1)
                mBinding.ivTitleClose.visibility = if (mBinding.webview.canGoBack()) View.VISIBLE else View.GONE
            }


        }

        if (!webUrl.isEmpty()) {
            if (webUrl.contains("web://")) {
                webUrl = webUrl.replace("web://", "")
            }
        } else {
            finish()
        }
        // 加载网页
        mBinding.webview.loadUrl(webUrl)
    }


    override fun onBackPressed() {
        // 如果 WebView 可以返回上一级网页，则返回
        if (mBinding.webview.canGoBack()) {
            mBinding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}