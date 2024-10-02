package com.shanai.shanai.common


import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.elvishew.xlog.XLog
import okhttp3.OkHttpClient
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * @Author: lzm
 * @Date: 2024-09-26 12:41
 * @Description: TODO
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {

    // 自定义 Glide 的配置
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // 设置 Glide 的内存缓存大小
        val memoryCacheSizeBytes = 1024 * 1024 * 40L // 20 MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes))

        // 设置磁盘缓存的大小和位置
        val diskCacheSizeBytes = 1024 * 1024 * 100L // 100 MB
        builder.setDiskCache(
            ExternalPreferredCacheDiskCacheFactory(context, "glide_cache", diskCacheSizeBytes)
        )

        // 设置图片的默认解码格式为 RGB_565 以减少内存消耗
        builder.setDefaultRequestOptions(
            RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig()
        )

        // 启用日志级别为调试模式
        builder.setLogLevel(android.util.Log.DEBUG)

        // 自定义线程池以优化性能
        builder.setSourceExecutor(GlideExecutor.newSourceExecutor())
    }

    // 禁用 Glide 生成的解析模式
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }



}
