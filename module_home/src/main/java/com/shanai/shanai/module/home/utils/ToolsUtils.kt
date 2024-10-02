package com.shanai.shanai.module.home.utils

import androidx.annotation.Nullable
import com.shanai.module.home.R
import com.shanai.shanai.module.home.constant.AppConstants

object ToolsUtils {

    //加载默认图片 区分 男女
    fun defaultHead(sex: String?): Int {
        var res = 0
        res = if (sex.isNullOrEmpty()) {
            if (sex == AppConstants.LADY) {
                R.drawable.common_default_woman
            } else {
                R.drawable.common_default_man
            }
        } else {
            R.drawable.common_default_man
        }
        return res
    }

}