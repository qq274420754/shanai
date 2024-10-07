package com.shanai.base.utils

import android.os.Build.VERSION.SDK_INT
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.shanai.base.BaseApplication

/**
 * 用于加载 Gif 的 Coil ImageLoader
 *
 * @author LZM
 * @since 2021/9/6 4:26 下午
 */
object CoilGIFImageLoader {

    val imageLoader = ImageLoader.Builder(BaseApplication.context)
        .componentRegistry {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder(BaseApplication.context))
            } else {
                add(GifDecoder())
            }
        }
        .build()
}