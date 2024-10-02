package com.shanai.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CoilUtils {

    // 根据 URL 加载 Drawable，可选择是否进行圆形转换
    suspend fun loadDrawableFromUrl(context: Context, imageUrl: String, isCircular: Boolean = false): Drawable? {
        // 创建 ImageLoader 实例
        val imageLoader = ImageLoader(context)

        // 构建 ImageRequest 请求，加入是否需要圆形转换的判断
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false) // 确保返回 Drawable
            .apply {
                if (isCircular) {
                    transformations(CircleCropTransformation()) // 圆形转换
                }
            }
            .build()

        // 执行请求并获取结果
        return withContext(Dispatchers.IO) {
            val result = imageLoader.execute(request)
            if (result is SuccessResult) {
                result.drawable
            } else {
                null
            }
        }
    }

    // 根据 URL 异步加载图片到 ImageView
    fun loadImageIntoImageView(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_report_image) // 设置占位符
            error(android.R.drawable.ic_dialog_alert)           // 设置错误图像
        }
    }

    // 加载圆形图片到 ImageView
    fun loadCircularImage(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            transformations(CircleCropTransformation())
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_report_image)
            error(android.R.drawable.ic_dialog_alert)
        }
    }

    // 加载图片并监听加载状态
    fun loadImageWithListener(
        context: Context,
        imageView: ImageView,
        imageUrl: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        imageView.load(imageUrl) {
            crossfade(true)
            listener(
                onSuccess = { _, _ -> onSuccess() },
                onError = { _, _ -> onError() }
            )
        }
    }

    // 在协程中使用 Coil 加载 Drawable 并返回
    fun loadDrawableInCoroutine(
        lifecycleOwner: LifecycleOwner,
        context: Context,
        imageUrl: String,
        isCircular: Boolean = false,
        onResult: (Drawable?) -> Unit
    ) {
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            val drawable = loadDrawableFromUrl(context, imageUrl, isCircular)
            onResult(drawable)
        }
    }
}

