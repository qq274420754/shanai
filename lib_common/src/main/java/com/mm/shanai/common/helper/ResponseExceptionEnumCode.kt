package com.mm.shanai.common.helper

/**
 * 请求响应异常枚举的抽象
 *
 * @author LZM
 * @since 2021/7/9 2:56 下午
 */
interface ResponseExceptionEnumCode {

    /**
     * 获取该异常枚举的code码
     * @return Int
     */
    fun getCode(): Int

    /**
     * 获取该异常枚举的描述
     * @return String
     */
    fun getMessage(): String
}