package com.mm.shanai.common.helper

/**
 * @Author: lzm
 * @Date: 2024-09-27 14:29
 * @Description: TODO
 */
enum class ResponseExceptionEnum : ResponseExceptionEnumCode {

    NOT_LOGIN_ERROR {
        override fun getCode() = -1001
        override fun getMessage() = "未登录"
    },
    ERROR {
        override fun getCode() = -1
        override fun getMessage() = ""
    },
    SUCCESS {
        override fun getCode() = 0
        override fun getMessage() = ""
    }
}