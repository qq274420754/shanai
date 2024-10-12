package com.quyunshuo.wanandroid.common.bean

import com.google.gson.annotations.SerializedName

/**
 * 公共Response
 *
 * @author LZM
 * @since 2021/8/5 11:33 下午
 */
data class BaseResponse<D>(
    val `data`: D,
    @SerializedName("code") val code: Int,
    @SerializedName("msg") val msg: String
)