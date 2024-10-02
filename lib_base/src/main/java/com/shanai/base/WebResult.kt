package com.shanai.base

/**
 * @Author: lzm
 * @Date: 2024-09-19 12:00
 * @Description: TODO
 */
sealed class WebResult<out T> {
    data class Success<out T>(val data: T) : WebResult<T>()
    data class Error(val message: String) : WebResult<Nothing>()
    object Loading : WebResult<Nothing>()
}
