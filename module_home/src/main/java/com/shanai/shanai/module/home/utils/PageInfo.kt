package com.shanai.shanai.module.home.utils

/**
 * @Author: lzm
 * @Date: 2024-09-28 23:38
 * @Description: TODO
 */
class PageInfo {

    var page = 0

    fun nextPage() {
        page++
    }

    fun reset() {
        page = 0
    }

    val isFirstPage: Boolean
        get() = page == 0
}