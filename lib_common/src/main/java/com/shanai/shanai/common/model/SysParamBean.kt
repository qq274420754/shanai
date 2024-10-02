package com.shanai.shanai.common.model

data class SysParamBean(

    //登录提示slogn
    val loginSlogon:String,

    //QQ 登录 appid 和 secret
    val qqAppid: String,
    val qqAppsecret: String,

    //微信登录appid和key
    val wxAppid: String,
    val wxAppsecret: String,

    //底部菜单列表
    val bottomMenuList: ArrayList<BottomMenuBean>,

    //首页分页tab
    val friendList: ArrayList<FriendListBean>,

    )
