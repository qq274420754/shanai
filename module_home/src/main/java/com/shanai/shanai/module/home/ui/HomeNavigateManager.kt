package com.shanai.shanai.module.home.ui

import com.alibaba.android.arouter.launcher.ARouter
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl

/**
 * Home模块 页面跳转管理类
 */
class HomeNavigateManager {

    companion object {
        fun navigateToMain(defaulttab:String) {
            ARouter.getInstance()
                .build(RouteUrl.MAIN)
                .withString(RouteKey.Params.DEFAULT_TAB, defaulttab)
                .navigation()
        }

        fun navigateToWeb(url:String) {
            ARouter.getInstance()
                .build(RouteUrl.WEB)
                .withString(RouteKey.Params.WEB_URL, url)
                .navigation()
        }
    }


}