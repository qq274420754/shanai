package com.mm.shanai.module.home.ui.activity

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import coil.load
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.mm.shanai.common.constant.MainTabKey
import com.mm.shanai.common.model.PrivacyAgreement
import com.mm.shanai.common.model.SysParamBean
import com.shanai.base.ktx.observeLiveData
import com.shanai.base.utils.SpUtils
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl
import com.shanai.common.constant.SpKey.KEY_ISAGREE_PRIVACYALERT
import com.shanai.common.constant.SpKey.KEY_PRIVACY_URL
import com.shanai.common.constant.SpKey.KEY_PROTOCOL_URL
import com.shanai.common.constant.SpKey.KEY_SPLASH_BG
import com.shanai.common.constant.SpKey.KEY_SYSPARAM_CACHE
import com.shanai.common.ui.activity.BaseActivity
import com.shanai.module.home.R
import com.shanai.module.home.databinding.HomeActivitySplashBinding
import com.mm.shanai.module.home.ui.activity.SplashVM
import com.mm.shanai.common.ui.CommonNavigateManager
import com.mm.shanai.module.home.ui.HomeNavigateManager
import dagger.hilt.android.AndroidEntryPoint

@Route(name = RouteKey.SPLASH_PAGE, path = RouteUrl.SPLASH)
@AndroidEntryPoint
class SplashActivity : BaseActivity<HomeActivitySplashBinding, SplashVM>(){

    // 通过 Hilt 注入 ViewModel
    override val mViewModel: SplashVM by viewModels()

    override fun createVB() = HomeActivitySplashBinding.inflate(layoutInflater)

    // 初始化视图
    override fun HomeActivitySplashBinding.initView() {
        mBinding.lifecycleOwner = this@SplashActivity // 确保 LiveData 可以感知生命周期
        mBinding.splash = mViewModel
        initListeners()
    }

    override fun initObserve() {
        observeLiveData(mViewModel.privacyAgreement, ::processSplashInfo)
        observeLiveData(mViewModel.errorMessage, ::showEnptyCacheDialog)
        observeLiveData(mViewModel.sysParamBean, ::processSystemBean)
    }

    private fun processSystemBean(sysParamBean: SysParamBean?) {
        val sysParamJson = Gson().toJson(sysParamBean)
        SpUtils.put(KEY_SYSPARAM_CACHE, sysParamJson)
        HomeNavigateManager.navigateToMain(MainTabKey.MainTab_FRIEND)
        finish()
    }



    fun processSplashInfo(privacyAgreement: PrivacyAgreement){
        val imageUrl = privacyAgreement.bgSplash
        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.bg_splash)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    SpUtils.put(KEY_SPLASH_BG, imageUrl)
                    return false
                }
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(mBinding.backgroundImage)

        SpUtils.putString(KEY_PROTOCOL_URL,privacyAgreement.protocolUrl)
        SpUtils.putString(KEY_PRIVACY_URL,privacyAgreement.privacyPolicyUrl)

        mBinding.tvWelcome.text = privacyAgreement.title
        mBinding.tvContent.text = getClickableHtml(privacyAgreement.protocolPrivacyAlert)
        mBinding.tvContent.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun initRequestData() {
        if (SpUtils.getBoolean(KEY_ISAGREE_PRIVACYALERT, false)) {
            //已经显示过隐私弹窗了
            SpUtils.getString(KEY_SPLASH_BG,"")?.let { showBgSplash(it) };
            mViewModel.loadSysParam()
        }else{
            //没有显示过隐私弹窗
            mViewModel.loadPrivacyAlertData()
        }
    }

    private fun initListeners() {
        // 设置按钮点击事件
        mBinding.btnAgree.setOnClickListener {
            CheckSysParamCache(true)
        }
        mBinding.btnDisagree.setOnClickListener {
            // 不同意按钮点击逻辑
            CheckSysParamCache(false)
        }
    }


    fun CheckSysParamCache(isAgree:Boolean){
        mBinding.privacyContainer.visibility = View.GONE
        SpUtils.put(KEY_ISAGREE_PRIVACYALERT, isAgree)
        val sysparam = SpUtils.getString(KEY_SYSPARAM_CACHE,"")
        if (sysparam.isNullOrEmpty()){
            //空  请求接口获取系统配置信息
            mViewModel.loadSysParam()
        }else{
            HomeNavigateManager.navigateToMain(MainTabKey.MainTab_FRIEND)
            finish()
        }
    }

    fun showBgSplash(imageUrl:String){
        if (!imageUrl.isNullOrEmpty()) {
            // 如果 URL 不为空，使用 Coil 加载网络图片
            mBinding.backgroundImage.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.bg_splash) // 设置占位图
                error(R.drawable.bg_splash) // 加载错误时显示默认图片
            }
        } else {
            // 如果 URL 为空，使用本地默认背景
            mBinding.backgroundImage.setImageResource(R.drawable.bg_splash)
        }
    }

    /**
     * 格式化超链接文本内容并设置点击处理
     */
    private fun getClickableHtml(htmlContent: String): CharSequence {
        //1. 解析 HTML
        val spannedText = HtmlCompat.fromHtml(htmlContent, HtmlCompat.FROM_HTML_MODE_LEGACY)
        // 2. 替换自定义 URLSpan 来处理 web:// 协议
        val spannable = spannedText as Spannable
        val urlSpans = spannable.getSpans(0, spannedText.length, URLSpan::class.java)
        for (urlSpan in urlSpans) {
            val start = spannable.getSpanStart(urlSpan)
            val end = spannable.getSpanEnd(urlSpan)
            val flags = spannable.getSpanFlags(urlSpan)
            spannable.removeSpan(urlSpan)
            spannable.setSpan(CustomUrlSpan(urlSpan.url), start, end, flags)
        }
        return spannedText;
    }

    class CustomUrlSpan(url: String) : ClickableSpan() {
        private val mUrl = url
        override fun onClick(widget: View) {
            // 处理点击事件
            CommonNavigateManager.navigateToWeb(mUrl)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false // 禁用下划线
        }
    }



    /**
     * 缓存为空的情况下  显示网络错误信息
     */
    private fun showEnptyCacheDialog(mode: String) {

        val sysparam = SpUtils.getString(KEY_SYSPARAM_CACHE,"")
        if (!sysparam.isNullOrEmpty() && mode == "sysparam"){
            HomeNavigateManager.navigateToMain(MainTabKey.MainTab_FRIEND)
            finish()
            return;
        }

        val normalDialog = AlertDialog.Builder(this)
        normalDialog.setMessage("应用访问需连接网络，请检查您的网络设置或切换网络后再次尝试")
        normalDialog.setPositiveButton(
            "重新连接"
        ) { dialog, which ->
            if ("sysparam" == mode) {
                mViewModel.loadSysParam()
            } else if ("sysconfigure" == mode) {
                mViewModel.loadPrivacyAlertData()
            }
        }
        normalDialog.setNegativeButton(
            "取消"
        ) { dialog, which -> finish() }
        normalDialog.setCancelable(false)
        normalDialog.show()
    }


}


