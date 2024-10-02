package com.shanai.base.utils

/**
 * @Author: lzm
 * @Date: 2024-09-22 21:17
 * @Description: TODO
 */

import android.os.Bundle

open class ViewRecreateHelper(savedInstanceState: Bundle?=null) {
    /**
     * 重建标记key
     */
    private val KEY_RECREATE = "recreate"

    /**
     * 是否重建
     */
    var isRecreate = false
        private set

    init {
        if (savedInstanceState!=null) {
            this.onSaveInstanceState(savedInstanceState)
        }
    }

    /**
     * 恢复状态
     */
    open fun onRestoreInstanceStatus(savedInstanceState: Bundle?) {
        isRecreate = savedInstanceState?.getBoolean(KEY_RECREATE) ?: false
    }

    /**
     * 保存状态
     */
    open fun onSaveInstanceState(bundle: Bundle) {
        bundle.putBoolean(KEY_RECREATE, true)
    }

}