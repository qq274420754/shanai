package com.shanai.module.home.ui.fragment

import com.shanai.base.mvvm.m.BaseRepository
import javax.inject.Inject

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 :
 */
class FriendRepo @Inject constructor() : BaseRepository() {

    suspend fun getData() = request<String> {
        emit("数据加载成功")
    }
}