package com.shanai.common.constant

/**
 * @Author: QuYunShuo
 * @Time: 2020/8/29
 * @Class: SpKey
 * @Remark: 本地存储的键 放在此类中
 */
object SpKey{

    const val ALL_USERS = "all_users"            // 存储所有已登录用户的 userId 集合
    const val CURRENT_USER_ID = "current_user_id" // 当前登录用户的 userId
    const val KEY_ISAGREE_PRIVACYALERT  = "isagree_privacyalert"//是否同意了隐私权限
    const val KEY_ISSHOW_PRIVACYALERT  = "isshow_privacyalert"//是否展示了隐私权限
    const val KEY_SPLASH_BG  = "bg_splash"//是否展示了隐私权限


    const val KEY_PRIVACY_CACHE  = "cache_privacy"//隐私接口缓存信息
    const val KEY_SYSPARAM_CACHE  = "cache_sysparam"//systemparam接口缓存信息

    const val KEY_PROTOCOL_URL  = "protocol_url"//协议地址
    const val KEY_PRIVACY_URL  = "privacy_url"//隐私地址
}

