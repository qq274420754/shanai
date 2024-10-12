package com.mm.shanai.common.ui

import com.alibaba.android.arouter.launcher.ARouter
import com.shanai.common.constant.RouteKey
import com.shanai.common.constant.RouteUrl

/**
 * @Author: lzm
 * @Date: 2024-10-03 22:27
 * @Description: TODO
 */
class CommonNavigateManager {

    companion object {

        fun navigateToWeb(url:String) {
            ARouter.getInstance()
                .build(RouteUrl.WEB)
                .withString(RouteKey.Params.WEB_URL, url)
                .navigation()
        }
    }
}