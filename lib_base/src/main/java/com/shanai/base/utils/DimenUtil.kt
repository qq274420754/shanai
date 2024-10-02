package com.shanai.base.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * @Author: lzm
 * @Date: 2024-09-18 10:14
 * @Description: TODO
 */


object DimenUtil {

    /**
     * 将 dp 转换为 px
     * @param context
     * @param dp
     * @return 转换后的像素值
     */
    fun dpToPx(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 将 px 转换为 dp
     * @param context
     * @param px
     * @return 转换后的 dp 值
     */
    fun pxToDp(context: Context, px: Float): Int {
        return (px / context.resources.displayMetrics.density).toInt()
    }

    /**
     * 获取屏幕宽度（以 px 为单位）
     * @param context
     * @return 屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    /**
     * 获取屏幕高度（以 px 为单位）
     * @param context
     * @return 屏幕高度
     */
    fun getScreenHeight(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.heightPixels
    }

    /**
     * 将 sp 转换为 px
     * @param context
     * @param sp
     * @return 转换后的像素值
     */
    fun spToPx(context: Context, sp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics
        ).toInt()
    }

    /**
     * 将 px 转换为 sp
     * @param context
     * @param px
     * @return 转换后的 sp 值
     */
    fun pxToSp(context: Context, px: Float): Int {
        return (px / context.resources.displayMetrics.scaledDensity).toInt()
    }
}
