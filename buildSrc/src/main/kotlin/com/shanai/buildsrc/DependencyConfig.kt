package com.shanai.buildsrc

/**
 * 项目依赖版本统一管理
 *
 * @author LZM
 * @since 4/24/21 4:00 PM
 */
object DependencyConfig {

    /**
     * 依赖版本号
     *
     * @author LZM
     * @since 4/24/21 4:01 PM
     */
    object Version {

        // AndroidX--------------------------------------------------------------
        const val AppCompat = "1.3.1"
        const val CoreKtx = "1.7.0"
        const val ConstraintLayout = "2.1.3"                // 约束布局
        const val TestExtJunit = "1.1.2"
        const val TestEspresso = "3.3.0"
        const val ActivityKtx = "1.5.1"
        const val FragmentKtx = "1.5.2"
        const val MultiDex = "2.0.1"

        // Android---------------------------------------------------------------
        const val Junit = "4.13"
        const val Coordinatorlayout = "1.2.0"                        // 材料设计UI套件
        const val Material = "1.12.0"                        // 材料设计UI套件
//        const val Material = "1.6.0"                        // 材料设计UI套件

        // Kotlin----------------------------------------------------------------
        const val Kotlin = "1.6.21"
        const val Coroutines = "1.6.1"                      // 协程

        // JetPack---------------------------------------------------------------
        const val Lifecycle = "2.4.1"                       // Lifecycle
        const val Hilt = "2.52"                             // DI框架-Hilt
        const val Room = "2.6.1"                             // 数据库

        // GitHub----------------------------------------------------------------
        const val OkHttp = "4.9.0"                          // OkHttp
        const val OkHttpInterceptorLogging = "4.9.1"        // OkHttp 请求Log拦截器
        const val Retrofit = "2.9.0"                        // Retrofit
        const val RetrofitConverterGson = "2.9.0"           // Retrofit Gson 转换器
        const val Gson = "2.8.7"                            // Gson
        const val xLog = "1.11.1"                            // xLog
        const val MMKV = "1.2.9"                            // 腾讯 MMKV 替代SP
        const val AutoSize = "v1.2.1"                        // 屏幕适配
        const val ARoute = "1.5.2"                          // 阿里路由
        const val ARouteCompiler = "1.5.2"                  // 阿里路由 APT
        const val SmartRefreshLayout = "2.1.0"              // SmartRefreshLayout
        const val classics = "2.0.6"                        // 经典刷新头
        const val immersionbar = "3.2.2"                    // 沉浸式状态栏
        const val Bannerviewpager = "3.5.12"                 //ViewPager轮播图
        const val viewpagerindicator = "1.2.3"                 //ViewPager轮播图
        const val adapterHelper = "4.1.4"                  //BaseRecyclerViewAdapterHelper
        const val EventBus = "3.2.0"                        // 事件总线
        const val PermissionX = "1.8.1"                     // 权限申请
        const val LeakCanary = "2.7"                        // 检测内存泄漏
        const val AutoService = "1.0"                       // 自动生成SPI暴露服务文件
        const val Coil = "1.3.0"                            // Kotlin图片加载框架
        const val Glide = "4.16.0"                            // Glide图片加载框架
        const val Magic = "1.7.0"                            // Magic
        const val Lottie = "6.5.2"                            // Lottie

        // 第三方SDK--------------------------------------------------------------
        const val TencentBugly = "3.3.9"                    // 腾讯Bugly 异常上报
        const val TencentBuglyNative = "3.8.0"              // Bugly native异常上报
        const val TencentTBSX5 = "44286"                    // 腾讯X5WebView
    }

    /**
     * AndroidX相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:01 PM
     */
    object AndroidX {
        const val AndroidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
        const val AppCompat = "androidx.appcompat:appcompat:${Version.AppCompat}"
        const val CoreKtx = "androidx.core:core-ktx:${Version.CoreKtx}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.ConstraintLayout}"
        const val TestExtJunit = "androidx.test.ext:junit:${Version.TestExtJunit}"
        const val TestEspresso = "androidx.test.espresso:espresso-core:${Version.TestEspresso}"
        const val ActivityKtx = "androidx.activity:activity-ktx:${Version.ActivityKtx}"
        const val FragmentKtx = "androidx.fragment:fragment-ktx:${Version.FragmentKtx}"
        const val MultiDex = "androidx.multidex:multidex:${Version.MultiDex}"
    }

    /**
     * Android相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:02 PM
     */
    object Android {
        const val Junit = "junit:junit:${Version.Junit}"
        const val Material = "com.google.android.material:material:${Version.Material}"
        const val Coordinatorlayout = "androidx.coordinatorlayout:coordinatorlayout:${Version.Coordinatorlayout}"
    }

    /**
     * JetPack相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:02 PM
     */
    object JetPack {
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.Lifecycle}"
        const val ViewModelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version.Lifecycle}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.Lifecycle}"
        const val Lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.Lifecycle}"
        const val LifecycleCompilerAPT =
            "androidx.lifecycle:lifecycle-compiler:${Version.Lifecycle}"
        const val HiltCore = "com.google.dagger:hilt-android:${Version.Hilt}"
        const val HiltApt = "com.google.dagger:hilt-compiler:${Version.Hilt}"
        const val Room = "androidx.room:room-runtime:${Version.Room}"
        const val RoomApt = "androidx.room:room-compiler:${Version.Room}"
        const val RoomKtx = "androidx.room:room-ktx:${Version.Room}"
    }

    /**
     * Kotlin相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:02 PM
     */
    object Kotlin {
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.Kotlin}"
        const val CoroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.Coroutines}"
        const val CoroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.Coroutines}"
    }

    /**
     * GitHub及其他相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:02 PM
     */
    object GitHub {
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Version.OkHttp}"
        const val OkHttpInterceptorLogging =
            "com.squareup.okhttp3:logging-interceptor:${Version.OkHttpInterceptorLogging}"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Version.Retrofit}"
        const val RetrofitConverterGson =
            "com.squareup.retrofit2:converter-gson:${Version.RetrofitConverterGson}"
        const val Gson = "com.google.code.gson:gson:${Version.Gson}"
        const val xLog = "com.elvishew:xlog:${Version.xLog}"
        const val MMKV = "com.tencent:mmkv-static:${Version.MMKV}"
        const val AutoSize = "com.github.JessYanCoding:AndroidAutoSize:${Version.AutoSize}"
        const val ARoute = "com.alibaba:arouter-api:${Version.ARoute}"
        const val ARouteCompiler = "com.alibaba:arouter-compiler:${Version.ARouteCompiler}"
        const val SmartRefreshLayout = "io.github.scwang90:refresh-layout-kernel:${Version.SmartRefreshLayout}"
        const val SmartRefreshLayout_classics = "io.github.scwang90:refresh-header-classics:${Version.classics}"
        const val IMmersionBar = "com.geyifeng.immersionbar:immersionbar:${Version.immersionbar}"
        const val IMmersionBar_ktx = "com.geyifeng.immersionbar:immersionbar-ktx:${Version.immersionbar}"
        const val Bannerviewpager = "com.github.zhpanvip:bannerviewpager:${Version.Bannerviewpager}"
        const val viewpagerindicator = "com.github.zhpanvip:viewpagerindicator:${Version.viewpagerindicator}"
        const val adapterHelper = "io.github.cymchad:BaseRecyclerViewAdapterHelper4:${Version.adapterHelper}"
        const val Magic = "com.github.hackware1993:MagicIndicator:${Version.Magic}"
        const val Lottie = "com.airbnb.android:lottie:${Version.Lottie}"


        const val EventBus = "org.greenrobot:eventbus:${Version.EventBus}"
        const val EventBusAPT = "org.greenrobot:eventbus-annotation-processor:${Version.EventBus}"
        const val PermissionX = "com.guolindev.permissionx:permissionx:${Version.PermissionX}"
        const val LeakCanary = "com.squareup.leakcanary:leakcanary-android:${Version.LeakCanary}"
        const val AutoService = "com.google.auto.service:auto-service:${Version.AutoService}"
        const val AutoServiceApt =
            "com.google.auto.service:auto-service-annotations:${Version.AutoService}"
        const val Glide = "com.github.bumptech.glide:glide:${Version.Glide}"
        const val Coil = "io.coil-kt:coil:${Version.Coil}"
        const val CoilGIF = "io.coil-kt:coil-gif:${Version.Coil}"
        const val CoilSVG = "io.coil-kt:coil-svg:${Version.Coil}"
        const val CoilVideo = "io.coil-kt:coil-video:${Version.Coil}"

    }

    /**
     * SDK相关依赖
     *
     * @author LZM
     * @since 4/24/21 4:02 PM
     */
    object SDK {
        const val TencentBugly = "com.tencent.bugly:crashreport:${Version.TencentBugly}"
        const val TencentBuglyNative =
            "com.tencent.bugly:nativecrashreport:${Version.TencentBuglyNative}"
        const val TencentTBSX5 = "com.tencent.tbs:tbssdk:${Version.TencentTBSX5}"
    }
}