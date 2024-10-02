package com.shanai.base.utils

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 * MMKV使用封装
 *
 * @author Qu Yunshuo
 * @since 8/28/20
 */
object SpUtils {

    /**
     * 初始化 MMKV
     */
    fun initMMKV(context: Context): String? = MMKV.initialize(context)

    /**
     * 保存数据（简化）
     * 根据value类型自动匹配需要执行的方法
     */
    fun put(key: String, value: Any) =
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Double -> putDouble(key, value)
            is String -> putString(key, value)
            is Boolean -> putBoolean(key, value)
            is Set<*> -> putStringSet(key, value as Set<String>)
            else -> false
        }

    // 保存String类型数据
    fun putString(key: String, value: String): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getString(key: String, defValue: String): String? =
        MMKV.defaultMMKV()?.decodeString(key, defValue)

    // 保存Set<String>数据
    fun putStringSet(key: String, value: Set<String>): Boolean? =
        MMKV.defaultMMKV()?.encode(key, value)

    // 获取Set<String>数据，支持返回可变的MutableSet
    fun getStringSet(key: String, defValue: Set<String>): Set<String>? =
        MMKV.defaultMMKV()?.decodeStringSet(key, defValue)?.toMutableSet()

    // 保存int类型数据
    fun putInt(key: String, value: Int): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getInt(key: String, defValue: Int): Int? = MMKV.defaultMMKV()?.decodeInt(key, defValue)

    // 保存long类型数据
    fun putLong(key: String, value: Long): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getLong(key: String, defValue: Long): Long? = MMKV.defaultMMKV()?.decodeLong(key, defValue)

    // 保存double类型数据
    fun putDouble(key: String, value: Double): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getDouble(key: String, defValue: Double): Double? =
        MMKV.defaultMMKV()?.decodeDouble(key, defValue)

    // 保存float类型数据
    fun putFloat(key: String, value: Float): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

    fun getFloat(key: String, defValue: Float): Float? =
        MMKV.defaultMMKV()?.decodeFloat(key, defValue)

    // 保存boolean类型数据
    fun putBoolean(key: String, value: Boolean): Boolean? = MMKV.defaultMMKV()?.encode(key, value)

//    fun getBoolean(key: String, defValue: Boolean): Boolean? =
//        MMKV.defaultMMKV()?.decodeBool(key, defValue)
    fun getBoolean(key: String, defValue: Boolean): Boolean =
        MMKV.defaultMMKV()?.decodeBool(key, defValue) ?: defValue



    // 检查是否包含某个key
    fun contains(key: String): Boolean? = MMKV.defaultMMKV()?.contains(key)

    // 清除指定key的值
    fun cleanValue(key: String) = MMKV.defaultMMKV()?.removeValueForKey(key)
}
