package com.mm.shanai.module.home.entity

import com.google.gson.annotations.SerializedName

/**
 * @Author: lzm
 * @Date: 2024-09-28 17:42
 * @Description: TODO
 */
data class FriendInfoListBean (

    val curPage: Int,

   val friendList: List<FriendInfoBean>


)