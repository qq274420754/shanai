package com.shanai.module.home.ui.fragment

import com.quyunshuo.wanandroid.common.helper.responseCodeExceptionHandler
import com.shanai.base.mvvm.m.BaseRepository
import com.shanai.module.home.net.FriendApiService
import com.shanai.shanai.module.home.entity.FriendInfoListBean
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * @author DBoy 2021/7/6 <p>
 * - 文件描述 :
 */
class MeRepo @Inject constructor() : BaseRepository() {

    @Inject
    lateinit var friendApiService: FriendApiService

    /**
     * 获取列表数据
     */
    suspend fun getUserList(itemtpe:String,currpager:Int) = request<FriendInfoListBean> {
        friendApiService.getUserList(itemtpe,currpager).run {
            responseCodeExceptionHandler(code,msg)
            emit(data)
        }
    }
}