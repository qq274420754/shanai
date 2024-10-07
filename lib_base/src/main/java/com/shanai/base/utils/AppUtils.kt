package com.shanai.base.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import com.elvishew.xlog.XLog
import com.shanai.base.BaseApplication


/**
 * App 相关工具类
 *
 * @author LZM
 * @sine 2023/2/13 23:15
 */
class AppUtils {

    /**
     * 获取当前 App 版本号
     *
     * @return Long
     */
    fun getAppVersionCode(): Long {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getAppPackageInfo().longVersionCode
        } else {
            getAppPackageInfo().versionCode.toLong()
        }
    }

    /**
     * 获取当前 App 版本名
     *
     * @return String
     */
    fun getAppVersionName(): String = getAppPackageInfo().versionName


    /**
     * 获取当前 App 的 [PackageInfo]
     *
     * @return PackageInfo
     */
    fun getPackageName(): String = getAppPackageInfo().packageName

    /**
     * 获取当前 App 的 [PackageInfo]
     *
     * @return PackageInfo
     */
    fun getAppPackageInfo(): PackageInfo {
        return BaseApplication.context
            .packageManager
            .getPackageInfo(BaseApplication.context.packageName, 0)
    }

    /**
     * 根据 CHANNEL获取渠道号
     */
    fun getChannelName():String{
        var channel = "defaultChanne2l";
        try {
            val context:Context = BaseApplication.context
            val appinfo = context.packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)
            val metadata = appinfo.metaData;
            if (metadata != null) {
                var data = metadata.getString("CHANNEL")
                XLog.d("CHANNEL= "+data)
                channel = if (data.isNullOrEmpty()) "defaultChannel" else data
            }
        }catch (e : Exception){
            XLog.e("err= "+e.message)
        }finally {
            return channel;
        }

    }

    /**
     * 返回设置相关信息项
     *
     * @return
     */
    fun getSystemInfo(): String {
        val sb = StringBuilder()
        val manufString = Build.MANUFACTURER // 制造商
        sb.append(manufString)
        var model = Build.MODEL // 型号
        if (model == null || "" == model) {
            model = Build.BOARD
        }
        sb.append(":").append(model!!.replace("-", ""))
        return StringUtils.inCodeUA(sb.toString())
    }
}
























