package com.shanai.shanai.module.home.Interceptor

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.shanai.common.constant.RouteUrl
import com.shanai.shanai.module.home.di.UserSessionModule
import com.shanai.shanai.module.home.manager.SessionManager

/**
 * @Author: lzm
 * @Date: 2024-10-03 10:38
 * @Description: TODO
 */
@Interceptor(priority = 8, name = "LoginInterceptor")
class LoginInterceptor : IInterceptor {

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val path = postcard.path  // 获取跳转的路径

        // 定义不需要登录的页面路径，可以通过路径前缀或全路径名进行判断
        val noLoginRequiredPaths = listOf(
            RouteUrl.SPLASH,        //
            RouteUrl.MAIN,          // 首页
            RouteUrl.WEB,           // web页
            RouteUrl.PHONELOGIN,    // 手机登录
            RouteUrl.PHONEVERIFYCODE,    // 手机号码验证
        )

        // 判断是否登录
        val isLoggedIn = SessionManager.isUserLoggedIn()

        if (isLoggedIn || noLoginRequiredPaths.contains(path)) {
            // 已登录，或者该路径不需要登录，直接放行
            callback.onContinue(postcard)
        } else {
            // 未登录，跳转到登录页面
            ARouter.getInstance().build("/user/login").navigation()
            // 阻止继续执行，拦截此次跳转
            callback.onInterrupt(Throwable("请先登录"))
        }
    }

    override fun init(context: Context) {
        // 初始化操作，一般留空即可
    }
}