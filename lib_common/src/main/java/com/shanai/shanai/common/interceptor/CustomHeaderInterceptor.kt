package com.shanai.shanai.common.interceptor

import android.os.Build
import com.shanai.base.BaseApplication
import com.shanai.base.utils.AppUtils
import com.shanai.base.utils.DimenUtil
import com.shanai.shanai.common.provider.UserSessionProvider
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * @Author: lzm
 * @Date: 2024-09-17 17:16
 * @Description: TODO
 */
class CustomHeaderInterceptor  @Inject constructor(
    private val userSessionProvider: UserSessionProvider
):Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        // 获取当前登录用户的 userId
        val userId = userSessionProvider.getCurrentUserId()
        // 在请求头中添加 UA 和 userid 信息
        request = request.newBuilder()
            .addHeader("User-Agent", getUserAgent())  // 添加 User-Agent 信息
//            .addHeader("tenant-id", "1")  // 添加租户 信息
            .apply {
                // 如果用户 ID 存在，则添加到请求头
                if (!userId.isNullOrEmpty()) {
                    addHeader("HTTP_X_API_USERID", userId)
                }
            }
            .build()
        return chain.proceed(request)
    }


    /**
     * 取得网络请求的UA
     * Sys/Android OS/16 Model/W818 Ver/2.2.7 PKG/yueai SCR/480x854 Ca/0
     * Sys/Android OS/24 Model/1.0.0 PKG/com.mm.michat SCR/1080x1920 Ca/0
     * "User-Agent" -> "Sys/Android OS/24 Model/MI MAXVer/1.0.0 PKG/com.mm.michat SCR/1080x1920 Ca/0"
     */
    fun getUserAgent(): String {
        return ((((("Sys/" +
                "android OS/" + Build.VERSION.SDK_INT +
                " Model/" + AppUtils().getSystemInfo()).toString() +
                " Ver/" + AppUtils().getAppVersionName()).toString() +
                " PKG/" + AppUtils().getPackageName()).toString() +
                " SCR/" + DimenUtil.getScreenWidth(BaseApplication.context)).toString() + "x" + DimenUtil.getScreenHeight(BaseApplication.context)).toString() +
                " Ca/" + AppUtils().getChannelName()
    }
}
