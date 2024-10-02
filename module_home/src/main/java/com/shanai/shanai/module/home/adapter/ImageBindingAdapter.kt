package com.shanai.shanai.module.home.adapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import coil.load
import com.shanai.module.home.R

/**
 * @Author: lzm
 * @Date: 2024-09-19 15:46
 * @Description: TODO
 */
object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            view.load(url) {
                crossfade(true)
                placeholder(R.drawable.bg_splash) // 设置占位图
                error(R.drawable.bg_splash) // 设置加载错误时的默认图片
            }
        } else {
            view.setImageResource(R.drawable.bg_splash) // 没有 URL 时使用默认图片
        }
    }
}
