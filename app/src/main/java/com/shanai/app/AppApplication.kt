package com.shanai.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.elvishew.xlog.XLog
import com.shanai.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus

/**
 * App壳
 *
 * @author LZM
 * @since 4/23/21 6:08 PM
 */
@HiltAndroidApp
class AppApplication : BaseApplication() {

    companion object {
        // 全局Context
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var application: AppApplication
    }

    override fun onCreate() {
        // 初始化全局Context
        context = this
        application = this
        // 开启EventBusAPT,优化反射效率 当组件作为App运行时需要将添加的Index注释掉 因为找不到对应的类了
        EventBus
            .builder()
//            .addIndex(MainEventIndex())
//            .throwSubscriberException(BuildConfig.VERSION_TYPE != VersionStatus.RELEASE)
            .installDefaultEventBus()
        super.onCreate()
    }



}