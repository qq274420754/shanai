package com.shanai.shanai.module.home.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.shanai.module.home.R
import com.shanai.shanai.module.home.entity.FriendTopBannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @Author: lzm
 * @Date: 2024-09-28 15:56
 * @Description: TODO
 */
class FriendTopBannerAdapter : BaseBannerAdapter<FriendTopBannerBean>() {
    override fun bindData(holder: BaseViewHolder<FriendTopBannerBean>, data: FriendTopBannerBean?, position: Int, pageSize: Int) {
        val iv = holder.findViewById<ImageView>(R.id.iv_topbanner_groupphoto)
        Glide.with(iv.context).load(data?.groupPhoto).into(iv)
    }
    override fun getLayoutId(viewType: Int) = R.layout.home_item_banner_photo
}