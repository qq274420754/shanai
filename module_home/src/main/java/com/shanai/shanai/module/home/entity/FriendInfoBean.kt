package com.shanai.shanai.module.home.entity

data class FriendInfoBean(
    //头像
    val headpho: String,
    //用户id
    val userid: String,
    //性别
    val sex: String,
    //昵称
    val nickname: String,
    //用户账号
    val usernum: String,
    //用户状态 /0普通用户  /1直播中 /2相亲中 /3在线
    val online: Int,
    //用户签名信息
    val memotext: String,
    //用户资料展示 "24岁 | 在线 | 单身"
    val userIntroduce: String,
    // 实名1  真人2  手机3
    val verify: Int,
    //  默认为用户item0   广告1  滑动广告2  为我推荐3
    var itemtype: Int = 0
)

