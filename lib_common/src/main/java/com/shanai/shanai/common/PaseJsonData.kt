package com.shanai.shanai.common

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils
import com.shanai.shanai.common.ui.CommonNavigateManager

/**
 * @Author: lzm
 * @Date: 2024-10-03 22:20
 * @Description: TODO
 */
object PaseJsonData {


    fun parseWebViewTag(urlContent: String, context:Context): Boolean {
        if (TextUtils.isEmpty(urlContent)) {
            return false;
        }
        if (urlContent.startsWith("in://")) {//内部跳转

        } else if (urlContent.startsWith("goto://")) {//跳转外部浏览器

        }else if (urlContent.startsWith("web://") || urlContent.startsWith("http://") || urlContent.startsWith("https://")) {//跳到内部网页浏览器
            CommonNavigateManager.navigateToWeb(urlContent)
        }

        return false
    }

}